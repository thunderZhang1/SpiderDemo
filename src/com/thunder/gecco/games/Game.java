package com.thunder.gecco.games;

import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

/**
 * Created by zhangjingjing on 2016/12/22.
 */
public class Game implements HtmlBean {
    @HtmlField(cssPath = " div > div > a")
    private List<Categoty> categoties;
    @Text
    @HtmlField(cssPath = "div > a")
    private String name;

    public List<Categoty> getCategoties() {
        return categoties;
    }

    public void setCategoties(List<Categoty> categoties) {
        this.categoties = categoties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
