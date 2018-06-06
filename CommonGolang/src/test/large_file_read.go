package test

import (
	"bufio"
	"log"
	"os"
	"strconv"
)

/**
 * 写一个大型json文件，大概占用4G 空间
 */
func WriteLargeJSONFile(filePath string) {
	f, err := os.OpenFile(filePath, os.O_WRONLY|os.O_APPEND, 0666)
	if err != nil {
		log.Fatal(err)
	}
	f.WriteString("{")

	// 以下内容写入，大概占据8.27G空间
	for i := 1; i <= 1e9/4; i++ {
		data := "'" + strconv.Itoa(i) + "'"
		data += ":'浔阳江头夜送客',"
		f.WriteString(data)
	}
	f.WriteString("}")
}

/**
 * 读一个超级大型的文件 start表示最开始的大小 单位KB
 */
func ReadLargeJSONFile(filePath string, start int) {
	f, err := os.Open(filePath)
	if err != nil {
		log.Fatal(err)
	}
	defer f.Close()
	stat, _ := f.Stat()
	log.Printf("文件大小是 %d GB", stat.Size()/1024/1024/1024)
	reader := bufio.NewReader(f)
	//reader.Discard(start * 1024)
	f.Seek((int64)(start*1024), 0) //这种方法比上面的快了很多很多
	dat := make([]byte, 48)
	reader.Read(dat)
	log.Println("---" + string(dat))
}
