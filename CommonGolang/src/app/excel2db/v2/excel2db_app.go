package v2

import (
	"fmt"
	"mime/multipart"
	"net/http"
	//"os"
	//"io"
	"html/template"
	"log"
	//"time"
	"io/ioutil"
	"strconv"
	"strings"
)

type PageInfo struct {
	Title  string
	Detail []DBShowDetail
}

type DBShowDetail struct {
	Id   string
	Url  string
	Name string
	Mark string
}

var pageInfo *PageInfo = nil

func getPageInfo() *PageInfo {
	if pageInfo == nil {
		dbDetails := make([]DBShowDetail, 0)

		pageInfo = &PageInfo{"导表工具", dbDetails}
		dbMap := initDBInfos()
		for key, val := range dbMap {
			detail := DBShowDetail{key, val.DB_URL, val.DB_Name, val.DB_Mark}
			dbDetails = append(dbDetails, detail)
		}
		pageInfo.Detail = dbDetails
	}
	return pageInfo
}

func upload(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()
	if r.Method == "GET" {
		t, err := template.ParseFiles("./app/excel2db/v2/www/input_sql1.html")
		checkErr(err)
		t.Execute(w, getPageInfo())
	} else {
		r.ParseMultipartForm(32 << 20)
		mp := r.MultipartForm
		if mp == nil {
			log.Println("not MultipartForm.")
			w.Write(([]byte)("导入失败:不是MultipartForm格式"))
			return
		}
		action(w, mp)
	}
}

func action(w http.ResponseWriter, mp *multipart.Form) {
	dbids := mp.Value["dbids"]
	fmt.Println(dbids)
	if len(dbids) == 0 {
		//w.Write(([]byte)("失败"))
		fmt.Fprintf(w, string("导入失败:没有选择数据库"))
		return
	}
	fhs := mp.File["file"]
	num := len(fhs)
	log.Printf("总文件数：%d\n", num)

	var insertErr error = nil

	for n, fheader := range fhs {
		fmt.Printf("%d : %s\n", n, fheader.Filename)
		if !strings.HasSuffix(fheader.Filename, ".xlsx") {
			continue
		}
		uploadFile, err := fheader.Open()
		checkErr(err)
		data, err := ioutil.ReadAll(uploadFile)
		checkErr(err)

		// defer func() {
		// 	if e := recover(); e != nil {
		// 		fmt.Printf("Panicing %s\r\n", e)
		// 	}
		// }()
		insertErr = readExcelFile(data, dbids)
		if insertErr != nil {
			w.Write(([]byte)("导入失败..." + insertErr.Error()))
			break
		}
	}
	if insertErr == nil {
		w.Write(([]byte)("导入成功..."))
		log.Println("upload success")
	} else {
		w.Write(([]byte)("导入失败..." + insertErr.Error()))
		log.Println("upload fail" + insertErr.Error())
	}
}

func checkErr(err error) {
	if err != nil {
		// err.Error()
		log.Println(err)
	}
}

func Start() {
	fmt.Println("----Excel to Sql multiple工具启动中----")

	http.Handle("/js/", http.FileServer(http.Dir("app/excel2db/v2/www/")))
	http.Handle("/css/", http.FileServer(http.Dir("app/excel2db/v2/www/")))
	http.HandleFunc("/upload", upload)

	port := 8888

	log.Println("欢迎使用 http://127.0.0.1:" + strconv.Itoa(port) + "/upload")
	err := http.ListenAndServe(":"+strconv.Itoa(port), nil)
	if err != nil {
		log.Fatal("listenAndServe: ", err)
	}
}
