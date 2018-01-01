package basic

import (
	"net/http"
	"log"
	"io/ioutil"
	"bytes"
)

// 普通Http Get请求
func HttpGet(url string) string {
	resp, err := http.Get(url)
	if err != nil {
		log.Println(err)
		return ""
	}
	data, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		log.Println(err)
		return ""
	}
	return string(data)
}

// 普通Http Post请求
func HttpPost(url string, data []byte) string {
	body_type := "application/json;charset=utf-8"
	req := bytes.NewBuffer(data)
	resp, err := http.Post(url, body_type, req)
	if err != nil {
		log.Println(err)
		return ""
	}
	ret, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		log.Println(err)
		return ""
	}
	return string(ret)
}