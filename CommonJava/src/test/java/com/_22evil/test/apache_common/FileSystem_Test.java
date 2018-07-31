package com._22evil.test.apache_common;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.junit.Test;

public class FileSystem_Test {

    @Test
    public void test_Jar() {
        try {
            FileSystemManager fsm = VFS.getManager();
            FileObject file = fsm.resolveFile("jar:/F:\\DDD2_122\\wyd-parent\\ServerDeploy\\worldServer\\worldserver.jar");
            FileObject[] fileObjects = file.getChildren();
            for (FileObject f : fileObjects) {
                System.out.println("===" + f.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
