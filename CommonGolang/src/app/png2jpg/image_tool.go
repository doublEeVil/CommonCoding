package png2jpg

import (
	"fmt"
	"image/jpeg"
	"image/png"
	"mime/multipart"
	"os"
)

/**
 * png转jpg
 */
func PngToJpg(pathFrom string, pathTo string) error {
	fileFrom, err := os.Open(pathFrom)
	if err != nil {
		fmt.Println("打开文件失败：", pathFrom, err)
		return err
	}
	defer fileFrom.Close()
	img, err := png.Decode(fileFrom)

	saveFile, err := os.Create(pathTo)
	if err != nil {
		fmt.Println("创建文件失败:", pathTo, err)
		return err
	}
	defer saveFile.Close()
	return jpeg.Encode(saveFile, img, nil)
}

/**
 * png转jpg
 */
func PngToJpgByFile(fileFrom multipart.File, pathTo string) error {
	img, err := png.Decode(fileFrom)

	saveFile, err := os.Create(pathTo)
	if err != nil {
		fmt.Println("创建文件失败:", pathTo, err)
		return err
	}
	defer saveFile.Close()
	return jpeg.Encode(saveFile, img, nil) // 最后一个参数是图片质量 0-100
}

/**
 * jpg转png
 */
func JpgToPng(pathFrom string, pathTo string) error {
	fileFrom, err := os.Open(pathFrom)
	if err != nil {
		fmt.Println("打开文件失败：", pathFrom, err)
		return err
	}
	defer fileFrom.Close()
	img, err := jpeg.Decode(fileFrom)

	saveFile, err := os.Create(pathTo)
	if err != nil {
		fmt.Println("创建文件失败:", pathTo, err)
		return err
	}
	defer saveFile.Close()
	return png.Encode(saveFile, img)
}

/**
 * jpg转png
 */
func JpgToPngByFile(fileFrom multipart.File, pathTo string) error {
	img, err := jpeg.Decode(fileFrom)

	saveFile, err := os.Create(pathTo)
	if err != nil {
		fmt.Println("创建文件失败:", pathTo, err)
		return err
	}
	defer saveFile.Close()
	return png.Encode(saveFile, img)
}
