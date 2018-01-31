package main

import (
	"fmt"
	"strings"
	"time"
	"net"
	"net/http"
	"./basic"
	"./app/webtools"
)

type Stu struct {
	name string
	age int
}

func main() {
	//httpGet("www.baidu.com", httpCallback)
	ppp("---test---")
	//data := basic.ReadFromFile("/Users/shangguyi/Downloads/abc/one.txt")
	//ppp(data)
	//basic.AppendFile("/Users/shangguyi/Downloads/abc/a.c", "hhh")
	dat := basic.HttpGet("http://baidu.com")
	ppp(dat)
	webtools.Start(8081);
	ppp("---end---")
}

func Hello(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "hello")
}

func server(conn net.Conn) {
	for  {
		buf := make([]byte, 512)
		n, _ := conn.Read(buf)
		ppp(string(buf[:n]))
	}
}

func sendData(ch chan string)  {
	ch <- "111"
	ch <- "222"
	ch <- "333"
}

func getData(ch chan string)  {
	for  {
		input := <- ch
		ppp(input)
	}
}

func wait1()  {
	ppp("wait 1")
	time.Sleep(10 * 1e9)
	ppp("wait 1 end")
}

func wait2()  {
	ppp("wait 2")
	time.Sleep(10 * 1e9)
	ppp("wait 2 end")
}

func httpGet(url string, f func(string))  {
	if strings.HasSuffix(url, ".com") {
		f("success")
	} else {
		f("fail")
	}
}

func httpCallback(msg string)  {
	fmt.Println(msg)
}

func ppp(abc interface{})  {
	fmt.Println(abc)
}