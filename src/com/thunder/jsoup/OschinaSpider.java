package com.thunder.jsoup;

import com.thunder.jsoup.Blog;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zhangjingjing on 2017/3/7.
 */
public class OschinaSpider {

    public static void main(String[] args) {
            String rootUrl = "https://www.oschina.net/blog";//需要爬取页面的根URl
            int pageIndex = 1;
            String sourcePath = "https://www.oschina.net/action/ajax/get_more_recommend_blog?classification=0&p=";//要爬取的地址

            //伪装UA，模拟浏览器访问
            String ua = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
            ArrayList<Blog> blogArrayList = new ArrayList<Blog>();

            try {
                Document document = Jsoup.connect(rootUrl).header("User-Agent",ua).timeout(5000).get();
                //获取博客分类名称
                Elements elements = document.select("span.nm-name");

            for (Element element:elements){
                System.out.print(element.html()+",");
            }

            while(true){//查询全部分页数据
                String path = sourcePath+pageIndex;
                Document doc = Jsoup.connect(path).header("User-Agent",ua).timeout(5000).get();
                Elements blogs = doc.select("div.item");
                if(blogs.size()==0||pageIndex>=20){
                    break;
                }
                for (Element element:blogs){
                    Element eleAuthor = element.select("div.box-fl > a").get(0);
                    String url = eleAuthor.attr("href");
                    String  authorHeaderImage = eleAuthor.select("img").attr("src");
                    String title = element.select("div.box-aw > header > a").get(0).attr("title");
                    String summary = element.select("div.box-aw > section").get(0).html();
                    String  author = element.select("div.box-aw > footer > span").get(0).html();
                    Blog blog = new Blog(url,title,summary,author,authorHeaderImage);
                    blogArrayList.add(blog);
                }
                pageIndex++;
            }
            for(Blog blog:blogArrayList){
                System.out.println(blog.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
