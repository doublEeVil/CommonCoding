package com._22evil.apache_common_test;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;

public class FileSystem_Test {

    public static void test_Jar() {
        try {
            FileSystemManager fsm = VFS.getManager();
            FileObject file = fsm.resolveFile("jar:/F:\\DDD2_122\\wyd-parent\\ServerDeploy\\worldServer\\worldserver.jar");
            FileObject[] fileObjects = file.getChildren();
            for (FileObject f : fileObjects) {
                System.out.println(f.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test_Jar();
    }
}
