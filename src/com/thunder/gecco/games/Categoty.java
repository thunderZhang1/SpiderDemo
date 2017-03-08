package com.thunder.gecco.games;

import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HtmlBean;

/**
 * Created by zhangjingjing on 2016/12/22.
 */
public class Categoty implements HtmlBean {
    @Text
    @HtmlField(cssPath = "a")
    private String title;

    @Href
    @HtmlField(cssPath = "a")
    private String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
