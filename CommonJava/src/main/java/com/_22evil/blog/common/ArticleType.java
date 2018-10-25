package com._22evil.blog.common;
public enum  ArticleType {
    HTML("html"),
    MARKDOWN("markdown");

    private String value;
    ArticleType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
