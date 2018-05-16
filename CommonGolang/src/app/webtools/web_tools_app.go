package webtools

import (
	"fmt"
	"html/template"
	"net/http"
	"os"
	"strconv"
)

type IndexPageInfo struct {
	Title string
}

func Start(port int) {
	fmt.Println("server start to run on port:", port)
	http.HandleFunc("/webtools/", indexPage) // 首页
	basePath, _ := os.Getwd()
	// 静态文件
	http.Handle("/webtools/static/", http.StripPrefix("/webtools/static/", http.FileServer(http.Dir(basePath+"/app/webtools/www/static"))))

	http.HandleFunc("/abc", helloworld)

	http.ListenAndServe(":"+strconv.Itoa(port), nil)
}

func indexPage(w http.ResponseWriter, req *http.Request) {
	basePath, _ := os.Getwd()
	info := IndexPageInfo{"welcome"}
	tmpl, _ := template.ParseFiles(basePath + "/app/webtools/www/index.html")
	tmpl.Execute(w, info)
}

func helloworld(w http.ResponseWriter, req *http.Request) {
	fmt.Fprintf(w, string("导入失败"))
}
