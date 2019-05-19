package com._22evil.web_container.global;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
public class Global {
    private static volatile Configuration free_marker_cfg;


     public static Configuration getFreeMarkerConfig() {
        if (null == free_marker_cfg ) {
            synchronized(Configuration.class) {
                if (null == free_marker_cfg) {
                    try {
                        free_marker_cfg = new Configuration(Configuration.VERSION_2_3_28);
                        File file = new File("template");
                        if (file.exists()) {
                            
                        } else {
                            file = new File("");
                            String path = file.getCanonicalPath();
                            file = new File(path + "/CommonJava/" + "/src/main/resources/template");
                        }
                        free_marker_cfg.setDirectoryForTemplateLoading(file);
                        free_marker_cfg.setDefaultEncoding("UTF-8");
                        free_marker_cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return free_marker_cfg;
     }
}