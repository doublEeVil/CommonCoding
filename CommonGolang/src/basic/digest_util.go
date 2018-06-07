package basic

//常用编码解码(加密解密相关)

import (
	"crypto/md5"
	"encoding/base64"
	"fmt"
)

/**
 * Md5摘要
 */
func Md5Encode(src string) string {
	dat := []byte(src)
	resultDat := md5.Sum(dat)
	resultStr := fmt.Sprintf("%x", resultDat) // 16进制
	return resultStr
}

/**
 * Base64编码
 */
func Base64Encode(src string) string {
	ret := base64.StdEncoding.EncodeToString([]byte(src))
	return ret
}

/**
 * Base64解码
 */
func Base64Decode(src string) string {
	ret, err := base64.StdEncoding.DecodeString(src)
	if err != nil {
		return "解码错误，请检查输入"
	}
	return string(ret)
}
