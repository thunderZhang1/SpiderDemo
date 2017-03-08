package com.thunder.gecco.iwgc;

import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.spider.HtmlBean;

/**
 * Created by zhangjingjing on 2016/12/5.
 */
public class Page implements HtmlBean {
    @Href
    @HtmlField(cssPath = "a")
    String pageUrl;

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }
}
