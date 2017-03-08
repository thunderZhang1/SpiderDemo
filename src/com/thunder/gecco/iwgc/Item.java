package com.thunder.gecco.iwgc;

import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HtmlBean;

/**
 * Created by zhangjingjing on 2016/12/5.
 */
public class Item implements HtmlBean {
    @Text
    @HtmlField(cssPath = "div > div > h3 > a")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
