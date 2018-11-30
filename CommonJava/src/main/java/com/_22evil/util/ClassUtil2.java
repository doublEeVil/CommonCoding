package com._22evil.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
/**
 * 不借助任何第三方库的工具类
 */
public class ClassUtil2 {
    private static final String ClASS_FILE_SUFFIX = ".class";
    private static final String JAR_FILE_SUFFIX = ".jar";

    /**
     * 只考虑当前classpath下的类
     * 如果想获取所有classpath下路径，可以用下面代码
     * <code>String path = System.getProperty("java.class.path");</code>
     * @param packageName
     * @return
     */
    public static Set<String> getAllClassName(String packageName) {
        Set<String> set = new HashSet<>();
        try {
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(".");
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String subPath = url.toString().replace("file:/", "").replace("/", ".");
                List<File> fileList = FileUtil.getFileList(url.getPath());
                for (File file : fileList) {
                    if (file.getName().endsWith(ClASS_FILE_SUFFIX)) {
                        // 判断是否包名下
                        String path = file.getPath().replace(File.separator, ".").replace(subPath, "").replace(ClASS_FILE_SUFFIX, "");
                        if (path.contains(packageName)) {
                            set.add(path);
                        }
                    } else if (file.getName().endsWith(JAR_FILE_SUFFIX)) {
                        // 处理jar文件
                        JarInputStream jarIn = new JarInputStream(new FileInputStream(file));
                        JarEntry entry = jarIn.getNextJarEntry();
                        while (entry != null) {
                            if (entry.getName().endsWith(ClASS_FILE_SUFFIX)) {
                                String path = entry.getName().replace("/", ".").replace(ClASS_FILE_SUFFIX, "");
                                if (path.startsWith(packageName)) {
                                    set.add(path);
                                }
                            }
                            entry = jarIn.getNextJarEntry();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    /**
     * 根据注解获取包名下的所有class
     * @param packageName
     * @return
     */
    public static <A extends Annotation> Set<Class> getClassSetWithAnnotation(String packageName, Class<A> annotation) {
        Set<String> classNameSet = getAllClassName(packageName);
        Set<Class> classSet = new HashSet<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            for (String className : classNameSet) {
                // 使用loadClass不会触发static方法
                Class<?> clazz = classLoader.loadClass(className);
                if (clazz.getAnnotation(annotation) != null) {
                    classSet.add(clazz);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classSet;
    }
}
