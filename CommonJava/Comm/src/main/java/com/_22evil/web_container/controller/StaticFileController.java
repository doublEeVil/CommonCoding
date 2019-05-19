package com._22evil.web_container.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 处理静态资源
 */
@Controller
public class StaticFileController {

    @RequestMapping(value = {"/static/*/*", "/static/*"})
    public void solveStatic(HttpServletRequest request, HttpServletResponse response) throws Exception{
        // 备注 如果文件是文本类型而且有中文，依旧是乱码
        response.setCharacterEncoding("UTF-8");
        String url = request.getRequestURI();
        
        File file = new File("template/static");
        if (file.exists()) {

        } else {
            file = new File("");
            String filePath = file.getCanonicalPath();
            file = new File (filePath + "\\src\\main\\resources\\template\\" + url.toString());
            if (file.exists()) {
                FileInputStream in = new FileInputStream(file);
                byte[] data = new byte[4096];
                int c;
                while ( (c = in.read(data)) > 0) {
                    response.getOutputStream().write(data, 0, c);
                }
                in.close();
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }   
}