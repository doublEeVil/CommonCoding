package excel2db


import (
	"fmt"
	"net/http"
	//"os"
	//"io"
	"html/template"
	"log"
	//"time"
	"strconv"
	"io/ioutil"
	"strings"
)

func upload(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()
	if r.Method == "GET" {
		t, err := template.ParseFiles("./app/excel2db/www/input_sql.html")
		checkErr(err)
		t.Execute(w, nil)
	} else {
		r.ParseMultipartForm(32 << 20)
		mp := r.MultipartForm
		if mp == nil {
			log.Fatal("not MultipartForm.")
			w.Write(([]byte)("不是MultipartForm格式"))
			return
		}
		fhs := mp.File["file"]
		num := len(fhs)
		log.Printf("总文件数：%d\n", num)

		for n, fheader := range fhs {
			fmt.Printf("%d : %s\n", n, fheader.Filename )
			if !strings.HasSuffix(fheader.Filename, ".xlsx") {
				continue
			}
			uploadFile, err := fheader.Open()
			checkErr(err)
			data, err := ioutil.ReadAll(uploadFile)
			checkErr(err)

			defer func() {
				if e := recover(); e != nil {
					fmt.Printf("Panicing %s\r\n", e)
				}
			}()
			readExcelFile(data)
		}

		w.Write(([]byte)("成功"))
		log.Println("upload success")
	}
}

func checkErr(err error) {
	if err != nil {
		// err.Error()
		log.Fatal(err)
	}
	fmt.Println("----")

}

func Start() {
	fmt.Println("----Excel to Sql multiple工具启动中----")

	http.Handle("/js/", http.FileServer(http.Dir("app/excel2db/www/")))
	http.Handle("/css/", http.FileServer(http.Dir("app/excel2db/www/")))
	http.HandleFunc("/upload", upload)

	port := 8888

	log.Println("欢迎使用 http://127.0.0.1:" + strconv.Itoa(port) + "/upload")
	err := http.ListenAndServe(":" + strconv.Itoa(port), nil)
	if err != nil {
		log.Fatal("listenAndServe: ", err)
	}
}