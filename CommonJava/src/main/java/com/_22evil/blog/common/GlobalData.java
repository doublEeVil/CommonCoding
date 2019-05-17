package com._22evil.blog.common;

import java.util.LinkedList;
import java.util.List;

public class GlobalData {
    private volatile static GlobalData instance;
    private List<String> recentPicList = new LinkedList<>(); //最近30张图片路径
    private int articleCount = 0;
    private GlobalData() {
    }

    public static GlobalData getInstance() {
        if (instance == null) {
            synchronized (GlobalData.class) {
                if (instance == null) {
                    instance = new GlobalData();
                }
            }
        }
        return instance;
    }

    public void addRecentPic(String path) {
        recentPicList.add(0, path);
        if (recentPicList.size() > 30) {
            recentPicList.remove(recentPicList.size() - 1);
        }
    }

    public List<String> getRecentPicList() {
        return recentPicList;
    }

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }

    public void addArticleCount() {
        this.articleCount++;
    }
}
