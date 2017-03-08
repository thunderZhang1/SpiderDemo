package com.thunder.jsoup;

/**
 * Created by zhangjingjing on 2017/3/7.
 */
public class Blog {
    private String url;
    private String title;
    private String summary;
    private String author;
    private String authorHeader;

    public Blog(String url, String title, String summary, String author, String authorHeader) {
        this.url = url;
        this.title = title;
        this.summary = summary;
        this.author = author;
        this.authorHeader = authorHeader;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", author='" + author + '\'' +
                ", authorHeader='" + authorHeader + '\'' +
                '}';
    }
}
