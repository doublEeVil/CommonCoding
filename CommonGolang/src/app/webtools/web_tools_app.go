package webtools

import (
	"fmt"
	"html/template"
	"io/ioutil"
	"log"
	"net/http"
	"os"
	"path"
	"regexp"
	"strconv"
	"strings"

	basic "../../basic"

	img_tool "../png2jpg"
)

type IndexPageInfo struct {
	Title string
}

func Start(port int) {
	fmt.Println("server start to run on port:", port)
	http.HandleFunc("/webtools/", indexPage)                  // 首页
	http.HandleFunc("/webtools/img_exchange", imageExchagnge) // 图片转换工具
	http.HandleFunc("/webtools/encode", commonEncode)         // 常用在线编解码

	basePath, _ := os.Getwd()
	// 静态文件
	http.Handle("/webtools/static/", http.StripPrefix("/webtools/static/", http.FileServer(http.Dir(basePath+"/app/webtools/www/static")))) //通过浏览器直接查看
	http.Handle("/js/", http.FileServer(http.Dir("app/webtools/www/")))
	http.Handle("/css/", http.FileServer(http.Dir("app/webtools/www/")))

	http.HandleFunc("/download/", download) // 通过浏览器直接下载而不是查看

	http.HandleFunc("/abc", helloworld)

	http.ListenAndServe(":"+strconv.Itoa(port), nil)
}

/**
 * 统一处理下载文件
 */
func download(w http.ResponseWriter, r *http.Request) {
	log.Println(r.URL.String())
	path := strings.Replace(r.URL.String(), "/download", "app/webtools/www/static", 1)
	log.Println(path)
	//http.ServeFile(w, r, path)

	w.Header().Set("Content-Type", "application/octet-stream") //通过设置头文件可以达到浏览器对图像，pdf,txt是直接下载，而不是浏览打开
	data, err := ioutil.ReadFile(path)
	if err != nil {
		w.Write(([]byte)("文件不存在"))
		return
	}
	w.Write(data)
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

type ImageExchangeInfo struct {
	Title  string
	Detail []ImageDetail
	ZipUrl string
}

type ImageDetail struct {
	Id   int
	Name string
	Size int64
	Url  string
}

/**
 * 图片转换 png jpeg互转
 */
func imageExchagnge(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()
	if r.Method == "GET" {
		t, _ := template.ParseFiles("app/webtools/www/img_exchange.html")
		imageExchangeInfo := &ImageExchangeInfo{"图片格式转换工具", nil, ""}
		t.Execute(w, imageExchangeInfo)
	} else {
		r.ParseMultipartForm(32 << 20)
		mp := r.MultipartForm
		if mp == nil {
			log.Println("not MultipartForm.")
			w.Write(([]byte)("导入失败:不是MultipartForm格式"))
			return
		}

		fhs := mp.File["file"]
		num := len(fhs)
		log.Printf("总文件数：%d\n", num)

		pathList := make([]string, 0)     //下载url路径
		fileNameList := make([]string, 0) //文件名
		sizeList := make([]int64, 0)      // 文件大小

		for n, fheader := range fhs {
			fmt.Printf("%d : %s\n", n, fheader.Filename)

			uploadFile, err := fheader.Open()
			if err != nil {
				log.Println(err)
				return
			}

			//png转jpg
			pat := ".*(png|PNG)"
			if ok, _ := regexp.Match(pat, []byte(fheader.Filename)); ok {
				fileName := path.Base(fheader.Filename)
				fileSuffix := path.Ext(fileName)
				fileName = strings.TrimSuffix(fileName, fileSuffix)
				fileName = fileName + ".jpg"
				pathTo := "app/webtools/www/static/" + fileName
				err := img_tool.PngToJpgByFile(uploadFile, pathTo)
				if err != nil {
					log.Println("转换文件失败 " + fheader.Filename)
					continue
				}
				pathList = append(pathList, "/download/"+fileName)
				fileNameList = append(fileNameList, fileName)
				f, err := os.Open(pathTo)
				fInfo, _ := f.Stat()
				sizeList = append(sizeList, fInfo.Size())
			}
			//jpg转png
			pat = ".*(jpg|jpeg|JPG|JPEG)"
			if ok, _ := regexp.Match(pat, []byte(fheader.Filename)); ok {
				fileName := path.Base(fheader.Filename)
				fileSuffix := path.Ext(fileName)
				fileName = strings.TrimSuffix(fileName, fileSuffix)
				fileName = fileName + ".png"
				pathTo := "app/webtools/www/static/" + fileName
				err := img_tool.JpgToPngByFile(uploadFile, pathTo)
				if err != nil {
					log.Println("转换文件失败 " + fheader.Filename)
					continue
				}
				pathList = append(pathList, "/download/"+fileName)
				fileNameList = append(fileNameList, fileName)
				f, err := os.Open(pathTo)
				fInfo, _ := f.Stat()
				sizeList = append(sizeList, fInfo.Size())

			}
		}
		t, _ := template.ParseFiles("app/webtools/www/img_exchange.html")
		infoList := make([]ImageDetail, 0)
		for index, path := range pathList {
			info := ImageDetail{
				index,
				fileNameList[index],
				sizeList[index] / 1024,
				path,
			}
			infoList = append(infoList, info)
		}
		imgExchangeInfo := &ImageExchangeInfo{"图片格式转换工具", infoList, ""}
		t.Execute(w, imgExchangeInfo)
	}

}

func commonEncode(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()
	if r.Method == "GET" {
		t, _ := template.ParseFiles("app/webtools/www/encode.html")
		t.Execute(w, nil)
	} else {
		// dat, _ := ioutil.ReadAll(r.Body)
		// log.Println(string(dat)) // 使用ajax方式发过来的数据，只能通过这种方式读取

		param1 := r.PostFormValue("encodeType")
		param2 := r.PostFormValue("srcStr")

		var ret string

		intVal, _ := strconv.Atoi(param1)
		if intVal == 0 {
			// md5
			ret = basic.Md5Encode(param2)
		} else if intVal == 1 {
			// base64 编码
			ret = basic.Base64Encode(param2)
		} else if intVal == 2 {
			// base64解码
			ret = basic.Base64Decode(param2)
		}
		w.Write([]byte(ret))
	}
}
