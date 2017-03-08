package com.thunder.gecco.iwgc;

import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

/**
 * Created by zhangjingjing on 2016/12/5.
 */
@Gecco(matchUrl = "http://www.iwgc.cn/{index}", pipelines = {"consolePipeline","wechatItemPagePipeline"})
public class WechaItems implements HtmlBean {
    @Request
    HttpRequest request;

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }
    //标题
    @Text
    @HtmlField(cssPath = "body > div.container.mt20 > div:nth-child(1) > div.col-lg-10.col-md-9.col-sm-9.col-xs-12 > div > div.panel-heading > h3")
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //记录request对应的分类的index
    @RequestParameter
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    //公众号数据集
    @HtmlField(cssPath = "body > div.container.mt20 > div:nth-child(1) > div.col-lg-10.col-md-9.col-sm-9.col-xs-12 > div > div.panel-body.listcon > div > div")
    List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
