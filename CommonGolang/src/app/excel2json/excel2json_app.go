package excel2json

import (
	"bytes"
	"fmt"
	"log"
	"os"
	"regexp"
	"strings"
	"time"

	"../../basic"
	"github.com/tealeg/xlsx"
)

var pat = "[a-zA-Z_][0-9a-zA-Z_]*"

func convertAll() {
	// 先创建文件夹，不然该文件夹不存在，后续文件创建不成功
	os.Mkdir("_json", os.ModePerm)

	fileList := basic.GetFilePathListOnlyRoot(".")
	for _, fileName := range fileList {
		convert(fileName)
	}
}

func convert(fileName string) {
	if !strings.HasSuffix(fileName, ".xlsx") {
		return
	}
	xlFile, err := xlsx.OpenFile(fileName)
	if err != nil {
		log.Println(fileName + " 文件不存在！")
		return
	}
	for _, sheet := range xlFile.Sheets {
		tbName := sheet.Name
		// fmt.Println("表名：", tbName)

		// 只考虑以tab开头的表
		if !strings.HasPrefix(tbName, "tab") {
			continue
		}

		f, err := os.OpenFile("_json\\"+tbName+".json", os.O_WRONLY|os.O_CREATE|os.O_TRUNC, 0777)
		if err != nil {
			log.Println(err)
			log.Println("打开文件错误" + "_json\\" + tbName + ".json")
		}

		var buffer bytes.Buffer
		buffer.WriteString("{\n")

		colMap := make(map[int]string)

		for _, row := range sheet.Rows {
			if len(colMap) == 0 {
				for cellIndex, cell := range row.Cells {
					// 构建头部
					text := cell.String()
					if ok, _ := regexp.Match(pat, []byte(text)); ok {
						colMap[cellIndex] = text
					} else {
						// 清空
						for k := range colMap {
							delete(colMap, k)
						}
						break
					}
				}
			} else {
				// 下面是正式数据
				for cellIndex, cell := range row.Cells {
					text := cell.String()
					if cellIndex == 0 && strings.Compare(text, "") == 0 {
						break
					}

					// 替换带有特殊的字符例如 ""
					text = strings.Replace(text, "\\", "\\\\", -1)
					text = strings.Replace(text, "\"", "\\\"", -1)

					if cellIndex == 0 {
						buffer.WriteString("\t\"" + text + "\": {\n")
					}

					buffer.WriteString("\t\t\"" + colMap[cellIndex] + "\": \"" + text + "\",\n")

					if cellIndex == len(row.Cells)-1 {
						buffer.WriteString("\t},\n")
					}
				}
			}
		}
		buffer.WriteString("}")
		f.Write(buffer.Bytes())
		buffer.Reset()
	}
}

func Start() {
	fmt.Println("--------欢迎使用excel转json工具-----------")
	fmt.Println("--------将本exe放入含有excel的文件夹中")
	fmt.Println("--------生成的文件位于_json文件夹下")
	fmt.Println("-----------------------------------------")

	t1 := time.Now()
	convertAll()
	t2 := time.Now()
	fmt.Printf("--------转换完成----用时 %d s", t2.Unix()-t1.Unix())
}
