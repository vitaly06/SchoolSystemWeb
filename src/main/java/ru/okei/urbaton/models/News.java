package ru.okei.urbaton.models;

public class News {
    private String content;

    public News() {
    }

    public News(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
