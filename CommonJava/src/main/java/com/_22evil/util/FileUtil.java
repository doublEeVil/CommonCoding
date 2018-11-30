package com._22evil.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileUtil {
    private static Logger logger = LogManager.getLogger(FileUtil.class);

    /**
     * 得到目标路径下的所有文件，包括所属路径下子目录文件
     * @param path 路径
     * @return 文件列表
     */
    public static List<File> getFileList(String path) {
        return getFileList(path, false);
    }

    /**
     * 得到目标路径下的所有文件，包括所属路径下子目录文件
     * @param path 路径
     * @param show 是否打印输出控制台
     * @return 文件列表
     */
    public static List<File> getFileList(String path, boolean show) {
        List<File> fileList = new ArrayList<File>();
        find(fileList, path, 1, show);
        return fileList;
    }

    private static void find(List<File> fileList, String path, int depth, boolean show) {
        File file = new File(path);
        if (!file.exists())
            return;
        if (file.isFile()) {
            if (show) {
                showConsole(depth - 1, "", file.getName());
            }
            fileList.add(file);
            return;
        }

        if (show) {
            showConsole(depth - 1, "|", file.getName());
        }

        File[] curFileList = file.listFiles();
        if (curFileList == null) {
            return;
        }
        for (File curFile : curFileList) {
            if (curFile.isFile()) {
                if (show) {
                    showConsole(depth, "",curFile.getName());
                }
                fileList.add(curFile);
            } else {
                find(fileList, curFile.getPath(), depth + 1, show);
            }
        }
    }

    private static void showConsole(int depth, String tag1,String tag2) {
        for (; depth > 0; depth--)
            System.out.print("   ");
        System.out.println("--" +  tag1+ tag2);
    }

    /**
     * 复制文件或者文件夹到目标路径
     * @param src 源路径
     * @param dst 目标路径
     * @return 是否复制成功
     */
    public static boolean copy(String src, String dst) {
        if (src == null || dst == null) {
            logger.error("param null");
            return false;
        }
        if ("".equals(src) || "".equals(dst)) {
            logger.error("param null");
            return false;
        }
        if (src.equals(dst)) {
            logger.error("src dst is same");
            return false;
        }
        File srcFile = new File(src);
        if (!srcFile.exists()) {
            logger.error("src not exsit");
            return false;
        }
        try {
            if (srcFile.isFile()) {
                // 文件处理
                File dstFile = new File(dst);
                copy1(srcFile, dstFile);
            } else {
                // 目录处理
                List<File> fileList = getFileList(src);
                for (File file : fileList) {
                    String path = file.getPath();
                    path = path.replace(src, dst);
                    File dstFile = new File(path);
                    copy1(file, dstFile);
                }
            }
        } catch (Exception e) {
            logger.error("copy error", e);
            return false;
        }
        return true;
    }

    private static void copy1(File src, File dst) throws IOException {
        FileInputStream in = new FileInputStream(src);
        FileOutputStream out = new FileOutputStream(dst);
        FileChannel inChan = in.getChannel();
        FileChannel outChan = out.getChannel();
        ByteBuffer buff = ByteBuffer.allocate(1024);
        int c = 0;
        while (true) {
            buff.clear();
            c = inChan.read(buff);
            if (c == -1) {
                break;
            }
            buff.flip();
            outChan.write(buff);
        }
        in.close();
        out.close();
    }

    /**
     * 写入内容到文件
     * @param path      路径
     * @param isAppend  是否尾添加，否则就覆盖
     * @param data      写入的内容
     */
    public static void writeToFile(String path, boolean isAppend, String data) {
        try {
            FileWriter fileWriter = new FileWriter(path,isAppend);
            fileWriter.append(data);
            fileWriter.flush();//立即保存
            fileWriter.close();//
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件，按行返回
     * @param path  路径
     * @return
     */
    public static List<String> readFileToStrings(String path) {
        List<String> data = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            String s = reader.readLine();
            while (s != null) {
                data.add(s);
                s = reader.readLine();
            }
            reader.close();
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 合并目录下所有文件为一个文件
     * 即 把所有文档内容拷贝到一个新文件中
     * @param path          路径
     * @param newFileName   新文件名
     * @param isAppend      如果新文件已存在,是否尾部添加, false则覆盖
     */
    public static void mergeToOneFile(String path, String newFileName, boolean isAppend) {
        List<File> fileList = getFileList(path);
        try {
            File newFile  = new File(path, newFileName);
            FileWriter writer = new FileWriter(newFile, isAppend);
            // 为防止出错, 先全部读, 再全部写
            // 如果读出错, 后面的写的内容没有实际意义
            List<String> data = new ArrayList<>();
            for (File file : fileList) {
                data.addAll(readFileToStrings(file.getPath()));
            }
            for (String s : data) {
                writer.append(s);
                writer.append("\n");
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回该目录以及子目录下所有类文件字节码内容
     * 包括.class文件， .jar下包含的文件
     * @param dir
     * @return
     */
    public static List<byte[]> getClassDataList(String dir) {
        List<byte[]> dataList = new ArrayList<>();
        List<File> jarFileList = new ArrayList<>();
        List<File> all = getFileList(dir);
        try {
            for (File file : all) {
                if (file.getName().endsWith(".class")) {
                    dataList.add(fileToByte(file));
                }
                if (file.getName().endsWith(".jar")) {
                    jarFileList.add(file);
                }
            }
            for (File file : jarFileList) {
                JarFile jar = new JarFile(file);
                Enumeration<JarEntry> jarEntryEnum = jar.entries();
                while (jarEntryEnum.hasMoreElements()) {
                    JarEntry jarEntry = jarEntryEnum.nextElement();
                    if (jarEntry.getName().endsWith(".class")) {
                        InputStream in = jar.getInputStream(jarEntry);
                        byte[] data = new byte[in.available()];
                        in.read(data);
                        dataList.add(data);
                    }
                }
                jar.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public static byte[] fileToByte(File file) throws Exception{
        InputStream in = new FileInputStream(file);
        byte[] data = new byte[in.available()];
        in.read(data);
        in.close();
        return data;
    }
}
