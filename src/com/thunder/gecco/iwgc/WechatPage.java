package com.thunder.gecco.iwgc;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

/**
 * Created by zhangjingjing on 2016/12/5.
 */
@Gecco(matchUrl = "http://www.iwgc.cn/{index}/p/{pageIndex}",pipelines = "consolePipeline")
public class WechatPage implements HtmlBean {
    @Request
    HttpRequest request;

    public HttpRequest getRequest() {
        return request;
    }
    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    //
    @HtmlField(cssPath = "body > div.container.mt20 > div:nth-child(1) > div.col-lg-10.col-md-9.col-sm-9.col-xs-12 > div > div.panel-body.listcon > div > div")
    List<Item> items;


    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
