package excel2json

import (
	"archive/zip"
	"bytes"
	"encoding/json"
	"fmt"
	// "io"
	"io/ioutil"
	"log"
	"os"
	"path/filepath"
	"regexp"
	"strings"
	"time"

	"../../basic"
	"github.com/tealeg/xlsx"
)

var pat = "[a-zA-Z_][0-9a-zA-Z_]*"

func convertAll(excelPath string, outputPath string) {
	// 先创建文件夹，不然该文件夹不存在，后续文件创建不成功
	// os.Mkdir("_json", os.ModePerm)
	fmt.Println(excelPath)

	fileList := basic.GetFilePathListOnlyRoot(excelPath)
	for _, fileName := range fileList {
		fmt.Printf("===%s\n", fileName)
		convert(fileName, outputPath)
	}
}

func convert(fileName string, outputPath string) {
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

		f, err := os.OpenFile(outputPath+"\\"+tbName+".json", os.O_WRONLY|os.O_CREATE|os.O_TRUNC, 0777)
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

func createJsonFile() {
	conf := Conf{
		Excel_path:  ".",
		Json_path:   "_json",
		Config_path: "conf",
	}
	data, err := json.Marshal(conf)
	fp, err := os.OpenFile("conf.json", os.O_RDWR|os.O_CREATE, 0755)
	if err != nil {
		log.Fatal(err)
	}
	defer fp.Close()
	_, err = fp.Write(data)
	if err != nil {
		log.Fatal(err)
	}
}

func zipFiles(inputDirs string, outputPath string) {

}

// 参数frm可以是文件或目录，不会给dst添加.zip扩展名
func compress(frm, dst string) error {
	buf := bytes.NewBuffer(make([]byte, 0, 10*1024*1024)) // 创建一个读写缓冲
	myzip := zip.NewWriter(buf)                           // 用压缩器包装该缓冲
	// 用Walk方法来将所有目录下的文件写入zip
	err := filepath.Walk(frm, func(path string, info os.FileInfo, err error) error {
		var file []byte
		if err != nil {
			return filepath.SkipDir
		}
		header, err := zip.FileInfoHeader(info) // 转换为zip格式的文件信息
		if err != nil {
			return filepath.SkipDir
		}
		header.Name, _ = filepath.Rel(filepath.Dir(frm), path)
		if !info.IsDir() {
			// 确定采用的压缩算法（这个是内建注册的deflate）
			header.Method = 8
			file, err = ioutil.ReadFile(path) // 获取文件内容
			if err != nil {
				return filepath.SkipDir
			}
		} else {
			file = nil
		}
		// 上面的部分如果出错都返回filepath.SkipDir
		// 下面的部分如果出错都直接返回该错误
		// 目的是尽可能的压缩目录下的文件，同时保证zip文件格式正确
		w, err := myzip.CreateHeader(header) // 创建一条记录并写入文件信息
		if err != nil {
			return err
		}
		_, err = w.Write(file) // 非目录文件会写入数据，目录不会写入数据
		if err != nil {        // 因为目录的内容可能会修改
			return err // 最关键的是我不知道咋获得目录文件的内容
		}
		return nil
	})
	if err != nil {
		return err
	}
	myzip.Close()               // 关闭压缩器，让压缩器缓冲中的数据写入buf
	file, err := os.Create(dst) // 建立zip文件
	if err != nil {
		return err
	}
	defer file.Close()
	_, err = buf.WriteTo(file) // 将buf中的数据写入文件
	if err != nil {
		return err
	}
	return nil
}

type Conf struct {
	Excel_path  string
	Json_path   string
	Config_path string
}

// 简要步骤
// 1. 得到配置文件，如果没有，创建配置文件，获取excel目录
// 2. 获得输出json目录，如果没有默认是_json目录
// 3. 获得输出config目录，如果没有，默认是config目录
func Start() {
	fmt.Println("--------欢迎使用excel转json工具-----------")
	fmt.Println("--------请先配置conf.json文件")
	fmt.Println("--------生成的文件位于_json文件夹下")
	fmt.Println("-----------------------------------------")

	file, err := os.Open("conf.json")
	if err != nil {
		fmt.Errorf("conf.json文件不存在, 已创建")
		createJsonFile()
	}
	defer file.Close()

	var conf Conf
	fp, err := os.OpenFile("conf.json", os.O_RDONLY, 0755)
	defer fp.Close()
	if err != nil {
		log.Fatal(err)
	}
	data := make([]byte, 1024)
	n, err := fp.Read(data)
	if err != nil {
		log.Fatal(err)
	}
	json.Unmarshal(data[:n], &conf)
	fmt.Println(conf)

	t1 := time.Now()
	// 1. 将excel转json
	//convertAll(conf.Excel_path, conf.Json_path)
	// 2. 将json zip压缩
	// zipFiles(conf.Json_path, conf.Config_path+"\\config")
	err = compress(conf.Json_path+"\\", conf.Config_path+"\\config")
	if err != nil {
		fmt.Println(err)
	}
	t2 := time.Now()
	fmt.Printf("--------转换完成----用时 %d s", t2.Unix()-t1.Unix())
}
