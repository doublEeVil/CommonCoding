package basic

import (
	"bufio"
	"io/ioutil"
	"log"
	"os"
	"path/filepath"
)

//将文件读取成一个字符串并返回
func ReadFromFile(path string) string {
	//方法1
	data, err := ioutil.ReadFile(path)
	if err != nil {
		log.Fatal(err)
		return ""
	}
	return string(data)
	////方法2
	//f, err := os.Open(path)
	//if err != nil{
	//	log.Fatal(err)
	//}
	//data, _ := ioutil.ReadAll(f)
	//return string(data)
}

//一行行得到文本内容
func ReadFromFileAsLines(path string) []string {
	ret := make([]string, 12)
	f, err := os.Open(path)
	if err != nil {
		log.Fatal(err)
	}
	defer f.Close()
	reader := bufio.NewReader(f)
	for {
		data, _, err := reader.ReadLine()
		if err != nil {
			break
		}
		ret = append(ret, string(data))
	}
	return ret
}

// 将内容写入文件, 会直接覆盖
func WriteToFile(path string, data string) {
	//方法1
	ioutil.WriteFile(path, []byte(data), 0644)
}

// 将内容添加到文件尾
func AppendFile(path string, data string) {
	f, err := os.OpenFile(path, os.O_WRONLY|os.O_APPEND, 0666)
	if err != nil {
		log.Fatal(err)
	}
	f.Write([]byte(data))
}

// 得到路径下所有文件路径, 包括子目录
func GetFilePathList(root string) []string {
	ret := make([]string, 8)
	err := filepath.Walk(root, func(path string, info os.FileInfo, err error) error {
		if info == nil {
			return err
		}
		if info.IsDir() {
			return nil
		}
		ret = append(ret, path)
		return nil
	})
	if err != nil {
		log.Fatal(err)
	}
	return ret
}

func GetFilePathListOnlyRoot(root string) []string {
	// files, _ := ioutil.ReadDir(root)
	// return files
	files, _ := filepath.Glob("*")
	return files
}
