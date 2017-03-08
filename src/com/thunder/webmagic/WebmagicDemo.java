package com.thunder.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by zhangjingjing on 2016/12/2.
 */
public class WebmagicDemo implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    public static void main(String[] args){
        //使用五个线程开启爬虫
        Spider.create(new WebmagicDemo())
                .addPipeline(new com.thunder.webmagic.ConsolePipeline())
                .addUrl("http://blog.csdn.net/").thread(5).run();
    }

    @Override
    public void process(Page page) {
        //博客列表标题

        page.putField("title",page.getHtml().xpath("//*[@class='blog_list']/dd/h3/a/text()").all().toString());
        //将分页的链接URL加入爬取队列中
        page.addTargetRequests(page.getHtml().xpath("//*[@class='page_nav']/a/@href").all());
        //打印分页URL
        page.putField("url",page.getHtml().xpath("//*[@class='page_nav']/a/@href").all().toString());
    }

    @Override
    public Site getSite() {
        return site.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36");
    }
}
