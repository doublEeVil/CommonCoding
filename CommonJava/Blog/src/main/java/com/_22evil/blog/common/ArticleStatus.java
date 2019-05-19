package com._22evil.blog.common;
public enum  ArticleStatus {
    PUBLISH("publish"),
    PRIVATE("private");

    private String status;
    ArticleStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
