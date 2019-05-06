package com._22evil.blog.controller.api;

import com._22evil.blog.ServiceManager;
import com._22evil.blog.common.GlobalData;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Collection;

import static com._22evil.blog.BlogApp.BLOG_CONFIG;

public class ApiPicAddRouter implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("data"));

        // 格式 2019_11_02_11_22_12_filename
        try {
            Part part = request.raw().getPart("file");
            String fileName = part.getSubmittedFileName();
//                if (!fileName.toLowerCase().endsWith(".png") && !fileName.toLowerCase().endsWith(".jpg")) {
//                    return "上传的文件必须是 config 配置文件";
//                }
            // 格式 2019_11_02_11_22_12_filename
            Calendar calendar = Calendar.getInstance();
            String prefix = BLOG_CONFIG.getUploadPicPath() + File.separator;
            StringBuilder sb = new StringBuilder();
            sb.append(calendar.get(Calendar.YEAR)).append("_");
            sb.append(calendar.get(Calendar.MONTH)).append("_");
            sb.append(calendar.get(Calendar.DAY_OF_MONTH)).append("_");
            sb.append(calendar.get(Calendar.HOUR_OF_DAY)).append("_");
            sb.append(calendar.get(Calendar.MINUTE)).append("_");
            sb.append(calendar.get(Calendar.SECOND)).append("_");
            sb.append(fileName);
            String newFileName = prefix + sb.toString();
            System.out.println("fileName is: " + newFileName);
            File file = new File(newFileName);
            if (file.exists()) {
                Files.delete(Paths.get(file.getPath()));
            }
            InputStream input = part.getInputStream();
            Files.copy(input, Paths.get(file.getPath()));
            ServiceManager.getInstance().getPicInfoService().addPicInfo(sb.toString());
            GlobalData.getInstance().addRecentPic(sb.toString());

//            Collection<Part> partList = request.raw().getParts();
//            for (Part part : partList) {
//                String fileName = part.getSubmittedFileName();
//                System.out.println("---" + fileName);
//                if (!fileName.toLowerCase().endsWith(".png") && !fileName.toLowerCase().endsWith(".jpg")) {
//                    return "上传的文件必须是 config 配置文件";
//                }
//
//                Calendar calendar = Calendar.getInstance();
//                StringBuilder sb = new StringBuilder("pic/");
//                sb.append(calendar.get(Calendar.YEAR)).append("_");
//                sb.append(calendar.get(Calendar.MONTH)).append("_");
//                sb.append(calendar.get(Calendar.DAY_OF_MONTH)).append("_");
//                sb.append(calendar.get(Calendar.HOUR_OF_DAY)).append("_");
//                sb.append(calendar.get(Calendar.MINUTE)).append("_");
//                sb.append(calendar.get(Calendar.SECOND)).append("_");
//                sb.append(fileName);
//                String newFileName = sb.toString();
//                System.out.println("fileName is: " + newFileName);
//                File file = new File(newFileName);
//                if (file.exists()) {
//                    Files.delete(Paths.get(file.getPath()));
//                }
//                InputStream input = part.getInputStream();
//                Files.copy(input, Paths.get(file.getPath()));
//                ServiceManager.getInstance().getPicInfoService().addPicInfo(fileName);
//                GlobalData.getInstance().addRecentPic(fileName);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败: " + e.getMessage();
        }
        return "success";
    }
}
