package com.thunder.gecco.iwgc;

import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HtmlBean;

/**
 * Created by zhangjingjing on 2016/12/5.
 */
public class Group implements HtmlBean {
    @Href
    String href;

    @Text
    String name;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
