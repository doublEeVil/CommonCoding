package com._22evil.http_server;

import com._22evil.classloader.SimpleClassLoader;
import com._22evil.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 采用java IO，性能不会很高
 */
public class SimpleHttpServer {
    private static Logger logger = LogManager.getLogger(SimpleHttpServer.class);
    private int port;
    private ExecutorService pool;
    private String controllerPkg = "com._22evil.http_server.handler";

    public SimpleHttpServer(int port, String controllerPkg) {
        this.port = port;
        this.pool = Executors.newCachedThreadPool();
        if (controllerPkg != null) {
            this.controllerPkg = controllerPkg;
        }
    }

    public SimpleHttpServer(int port) {
        this.port = port;
        this.pool = Executors.newCachedThreadPool();
    }

    public void loadController(Class<?> clazz) throws IllegalAccessException, InstantiationException{
        if (!clazz.getSuperclass().getName().equals("com.BaseHandler")) {
            return;
        }
        Handler handler = (Handler)clazz.getAnnotation(Handler.class);
        if (handler == null) {
            return;
        }
        String url = handler.value();
        if (BaseHandler.handlerMap.containsKey(url)) {
            throw new RuntimeException("注解复用:" + clazz.getName());
        }
        BaseHandler.handlerMap.put(url, (BaseHandler) clazz.newInstance());
        logger.info("load url: " + url);
    }

    public void start() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        List<File> fileList = FileUtil.getFileList(".");
        // 在win系统中， 如果是jar运行，直接定位到jar内部，返回jar内部文件列表
        // 如果是在Linux， 上面返回的是jar当前所在的文件夹下的文件列表
        for (File file : fileList) {
            if (file.getName().endsWith(".class")) {
                if (!file.getPath().matches(".*" + controllerPkg + ".*")) {
                    continue;
                }
                Class<?> clazz = SimpleClassLoader.getInstance().load(file.getPath());
                loadController(clazz);
            }

            if (file.getName().endsWith(".jar")) {
                JarFile jar = new JarFile(file);
                Enumeration<JarEntry> jarEntryEnum = jar.entries();
                while (jarEntryEnum.hasMoreElements()) {
                    JarEntry jarEntry = jarEntryEnum.nextElement();
                    if (!jarEntry.getName().matches(".*" + controllerPkg + ".*")) {
                        continue;
                    }
                    if (jarEntry.getName().endsWith(".class")) {
                        InputStream in = jar.getInputStream(jarEntry);
                        byte[] data = new byte[in.available()];
                        in.read(data);
                        Class<?> clazz = SimpleClassLoader.getInstance().load(data);
                        loadController(clazz);
                    }
                }
                jar.close();
            }
        }
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(port));
        while (true) {
            Socket socket = null;
            socket = server.accept();
            pool.execute(new SocketThread(socket));
        }
    }
}

class SocketThread implements Runnable {
    private Socket socket;

    public SocketThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            InputStreamReader in = new InputStreamReader(socket.getInputStream());
            int c = in.read();
            StringBuilder rsb = new StringBuilder();
            while (c > 0) {
                rsb.append((char) c);
                if (rsb.toString().matches("GET[\\s\\S]*\\r\\n\\r\\n")) {
                    break;
                } else if (rsb.toString().matches("POST[\\s\\S]*\\r\\n\\r\\n[\\s\\S]*")){
                    if (!in.ready())
                        break;
                }
                c = in.read();
            }
            if (rsb.toString().length() == 0) {
                return;
            }
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
            HttpRequest request = new HttpRequest(rsb.toString());
            HttpResponse response = new HttpResponse(out);
            BaseHandler handler = BaseHandler.handlerMap.get(request.getUrl());
            if (handler == null) {
                // 404 错误
                out.write("HTTP/1.1 404 \n");
                out.flush();
                out.close();
                return;
            }
            Method method = handler.getClass().getMethod("doService", String.class, HttpRequest.class, HttpResponse.class);
            method.invoke(handler, request.getMethod(), request, response);
            in.close();
            out.close();
        } catch (Exception e) {
           e.printStackTrace();
           // 500 错误
        }
    }
}