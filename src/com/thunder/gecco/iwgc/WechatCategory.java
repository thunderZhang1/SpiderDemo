package com.thunder.gecco.iwgc;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HrefBean;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

/**
 * Created by zhangjingjing on 2016/12/5.
 */
@Gecco(matchUrl="http://www.iwgc.cn/", pipelines={"categoriesPipeline","consolePipeline"})
public class WechatCategory implements HtmlBean {
    //分类列表
    @HtmlField(cssPath = "body > div.container.mt20 > div:nth-child(1) > div.col-lg-2.col-md-3.col-sm-3.col-xs-12 > div > a")
    List<HrefBean> groups;

    @Request
    HttpRequest request;

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public List<HrefBean> getGroups() {
        return groups;
    }

    public void setGroups(List<HrefBean> groups) {
        this.groups = groups;
    }

    public static void main(String[] args) {
        //先获取分类列表
        HttpGetRequest start = new HttpGetRequest("http://www.iwgc.cn/");
        start.setCharset("GBK");
        GeccoEngine.create()
                .classpath("com.thunder.gecco.iwgc")
                //开始抓取的页面地址
                .start(start)
                //开启几个爬虫线程
                .thread(1)
                //单个爬虫每次抓取完一个请求后的间隔时间
                .interval(200)
                .run();
        //将不同分类的URL加入爬取队列
        GeccoEngine.create().classpath("com.thunder.gecco.iwgc")
                .start(CategoriesPipeline.sortRequests)
                .thread(1)
                .interval(200)
                .run();
        //爬取不同分类下的数据
        GeccoEngine.create().classpath("com.thunder.gecco.iwgc")
                .start(WechatItemPagePipeline.sortRequests)
                .thread(1)
                .interval(200)
                .run();
    }
}
