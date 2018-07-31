package com._22evil.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class SimpleClassLoader extends ClassLoader{
    private static SimpleClassLoader loader = new SimpleClassLoader();

    private SimpleClassLoader() {

    }

    public static SimpleClassLoader getInstance() {
        if (loader == null) {
            loader = new SimpleClassLoader();
        }
        return loader;
    }

    public Class<?> load(byte[] data) throws ClassNotFoundException {
        return defineClass(null, data, 0, data.length);
    }

    public Class<?> load(String path) throws IOException, ClassNotFoundException {
        FileChannel channel = new FileInputStream(path).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int)channel.size());
        channel.read(buffer);
        return load(buffer.array());
    }

    public Class<?> load(File file) throws IOException, ClassNotFoundException {
        FileChannel channel = new FileInputStream(file).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int)channel.size());
        channel.read(buffer);
        return load(buffer.array());
    }
}
