package v1

import (
	"bytes"
	"database/sql"
	"fmt"
	"log"
	_ "strconv"
	"strings"
	_ "strings"

	_ "github.com/go-sql-driver/mysql"
	"github.com/tealeg/xlsx"
)

const user_name = "zjs"
const password = "123456"
const db_url = "127.0.0.1:3306"
const db_name = "ddd2h5_dev"

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

func readExcelFile(data []byte) {
	xlFile, err := xlsx.OpenBinary(data)
	checkErr(err)

	tabMap := getAllTables()

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
		err := getColumnName(tbName, colMap1)
		if err != nil {
			log.Println("查找表报错-----", tbName)
			log.Println(err)
			continue
		}

		var buffer bytes.Buffer
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

		// 先清空表数据
		clearTable(tbName)
		// 插入新数据
		insertToDb(sql)
	}
}

var MY_DB *sql.DB = nil

func getDb() *sql.DB {
	if MY_DB == nil {
		//db, err := sql.Open("mysql", "zjs:123456@tcp(127.0.0.1:3306)/ddd2h5_dev?charset=utf8")
		db, err := sql.Open("mysql", user_name+":"+password+"@tcp("+db_url+")/"+db_name+"?charset=utf8")
		log.Fatal("数据库链接出错： ", err)
		//checkErr(err)
		MY_DB = db
	}
	return MY_DB
}

func getAllTables() map[string]int {
	tabMap := make(map[string]int)
	rows, err := getDb().Query("show tables")
	if err != nil {
		log.Println("得到全部表名报错：", err)
	}
	for rows.Next() {
		var tabName string
		rows.Scan(&tabName)
		tabMap[tabName] = 0
	}
	return tabMap
}

func getColumnName(tbName string, colMap map[string]int) error {
	rows, err := getDb().Query("select * from " + tbName + " limit 1")
	if err != nil {
		return err
	}
	//返回所有列
	cols, _ := rows.Columns()

	for _, col := range cols {
		colMap[col] = 0
	}
	return nil
}

func clearTable(tbName string) {
	_, err := getDb().Exec(" DELETE FROM " + tbName)
	if err != nil {
		log.Println("清空表报错： ", err)
	}
}

func insertToDb(sql string) {
	_, err := getDb().Exec(sql)
	if err != nil {
		log.Println("插入数据报错：", err)
	}
}
