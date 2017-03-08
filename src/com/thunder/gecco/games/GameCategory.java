package com.thunder.gecco.games;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

import static com.sun.xml.internal.ws.api.message.Packet.Status.Request;

/**
 * Created by zhangjingjing on 2016/12/22.
 */
@Gecco(matchUrl = "http://www.4399.com/",pipelines = "consolePipeline")
public class GameCategory implements HtmlBean {
    @Request
    HttpRequest request;

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    @HtmlField(cssPath = "div.middle_2 > div")
    List<Game> games;

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public static void main(String[] args) {
        //先获取分类列表
        HttpGetRequest start = new HttpGetRequest("http://www.4399.com/");
        start.setCharset("GBK");
        GeccoEngine.create()
                .classpath("com.thunder.gecco.games")
                //开始抓取的页面地址
                .start(start)
                //开启几个爬虫线程
                .thread(1)
                //单个爬虫每次抓取完一个请求后的间隔时间
                .interval(200)
                .run();
    }
}
