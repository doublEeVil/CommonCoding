package v2

import (
	"bytes"
	"fmt"
	"log"
	_ "strconv"
	"strings"
	_ "strings"

	"github.com/tealeg/xlsx"
)

func excelToDb(fileNames []string) {
	// 先将Excel读取
	for _, fname := range fileNames {
		fmt.Println("====", fname)
		xlFile, err := xlsx.OpenFile(fname)
		checkErr(err)
		for _, sheet := range xlFile.Sheets {
			tbName := sheet.Name
			fmt.Println("表名：", tbName)

			var buffer bytes.Buffer
			buffer.WriteString("insert into ")
			buffer.WriteString(tbName)

			for rowIndex, row := range sheet.Rows {
				if rowIndex == 1 {
					buffer.WriteString(" (")
					for _, cell := range row.Cells {
						// 0行是中文解释， 1行是字段名
						text := cell.String()
						fmt.Printf("%s\n", text)
						buffer.WriteString(text)
						buffer.WriteString(",")
					}
					buffer.WriteString(")")
				}

			}
		}
	}
}

func readExcelFile(data []byte, dbids []string) error {
	xlFile, err := xlsx.OpenBinary(data)
	checkErr(err)

	idstring := dbids[0]
	dbids = strings.Split(idstring, ",")

	tabMap := getAllTables(dbids[0])

	for _, sheet := range xlFile.Sheets {
		tbName := sheet.Name
		_, isExist := tabMap[tbName]
		if !isExist {
			continue
		}
		fmt.Println("表名：", tbName)
		colMap1 := make(map[string]int) // 当成一个set， 判断是否存在column
		colMap2 := make(map[int]string) // 真正的map，得到位置与column的关系

		// 根据表名得到栏目名，一般情况下excel会比db表多出一些字段
		err := getColumnName(tbName, dbids[0], colMap1)
		if err != nil {
			log.Println("查找表报错-----", tbName)
			log.Println(err)
			continue
		}

		var buffer bytes.Buffer
		buffer.Reset()
		buffer.WriteString("insert into ")
		buffer.WriteString(tbName)
		buffer.WriteString(" (")

		var findColumnOver = false
		var lastColNumber = -12

		for _, row := range sheet.Rows {
			// 直接循环，直到碰到与栏目名对应的那一行
			if !findColumnOver {
				for index, cell := range row.Cells {
					text := cell.String()
					_, exist := colMap1[text]
					if exist {
						colMap2[index] = text
						buffer.WriteString("`")
						buffer.WriteString(text)
						buffer.WriteString("`")

						if len(colMap1) == len(colMap2) {
							findColumnOver = true
							lastColNumber = index
							buffer.WriteString(") values ")
						} else {
							buffer.WriteString(",")
						}
					}
				}
				if len(colMap2) != len(colMap1) {
					//数据不相等，栏目名找错了,直接清空
					colMap2 = make(map[int]string)
					buffer.Reset()
					buffer.WriteString("insert into ")
					buffer.WriteString(tbName)
					buffer.WriteString(" (")
				}
			} else {
				// 说明这时可以导数据了
				isBlankColumn := false

				for ind, cell := range row.Cells {
					_, exist := colMap2[ind]
					if !exist {
						continue
					}

					text := cell.String()

					if ind == 0 && strings.Compare(text, "") == 0 {
						// 空白数据
						isBlankColumn = true
						break
					}

					if ind == 0 {
						buffer.WriteString("(")
					}

					buffer.WriteString("\"")
					//以下是做特殊字符的转义处理
					text = strings.Replace(text, "\\", "\\\\", -1)
					text = strings.Replace(text, "\"", "\\\"", -1)
					buffer.WriteString(text)
					buffer.WriteString("\"")
					if ind == lastColNumber {
						buffer.WriteString(")")
						break
					} else {
						buffer.WriteString(",")
					}
				}

				// 判断是否还有其他数据
				if len(row.Cells) != 0 && !isBlankColumn {
					buffer.WriteString(",")
				}
			}
		}
		str := buffer.String()
		sql := str[0 : len(str)-1] // 把','去掉
		//log.Println("jieguo: ", sql)

		for _, dbid := range dbids {
			// 先清空表数据
			err := clearTable(tbName, dbid)
			if err != nil {
				return err
			}
			// 插入新数据
			err = insertToDb(sql, dbid)
			if err != nil {
				return err
			}
		}
	}
	return nil
}
