package com._22evil.test.module_httpserver;

import com._22evil.module_httpserver.controller.BaseHttpController;
import com._22evil.module_httpserver.controller.HandleHttp;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@HandleHttp(url = "/test")
public class TestController extends BaseHttpController{

    public void service(MockHttpServletRequest request, MockHttpServletResponse response) throws Exception {
        System.out.println("this is test");
        request.getSession(true).setAttribute("name", "zhjs");
        String sid = request.getSession().toString();

        response.addHeader("ContentType", "application-msdownload");
        response.addHeader("Content-Disposition", "attachment;filename=11.xls");

        // 新建xls文件
        HSSFWorkbook wb=new HSSFWorkbook();
        // 新建sheet
        HSSFSheet sheet=wb.createSheet("第一个sheet");
        sheet.createRow(1).createCell(1).setCellValue("ccc");
        wb.write(response.getOutputStream());

        //response.getWriter().write("<html><body>thi is  test " + sid + "<a href='/abc'>go to abc</a></body></html>");
    }

    //
    private void testOutputFile() {


    }
}