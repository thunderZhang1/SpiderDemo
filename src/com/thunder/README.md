
---
title: 自己动手网络写爬虫
---

#### Jsoup
>[Jsoup](https://jsoup.org/)是一个操作Html的java库。它提供了非常方便[Api](https://jsoup.org/apidocs/)，通过DOM、CSS、类似JQuery的方式，来取出和操作数据。

在这里我通过分析[开源中国](https://www.oschina.net/blog)页面结构和爬取部分的博客信息来实战Jsoup爬虫示例。至于Jsoup的更多细节的使用请参考[Jsoup开发指南](http://www.open-open.com/jsoup/),那么废话不多说。

1. 打开开源中国博客页面，我们主要抓取的数据如下的博客列表数据
![博客列表](https://ww3.sinaimg.cn/large/006tNc79gy1fdebc83cxvj31cq0j4q8r.jpg)
2. 打开Chrome的开发者工具
	在Element标签下点击图示按钮，在页面中选中你要查看的内容，即可查看到对应的Html代码了
	
   ![](https://ww1.sinaimg.cn/large/006tNc79gy1fdebm7fu3hj30eb05uwfk.jpg)
   
   
   从Html代码中我们看到最新推荐博客列表对应的Html代码为id=“topsOfRecomend”下面的div中。下面图示是列表的item内容，找到这里其实已经成功了一大半了，我们已经找到了我们想要的全部信息。
   
   ![](https://ww4.sinaimg.cn/large/006tNc79gy1fdebwfrwksj30hr0bzdhw.jpg)
   
3. 再来思考一个问题，这个列表肯定是有分页的，第二页的数据怎么获取呢？
   下面就需要到开发工具中的Network找答案了。
   
   ![](https://ww1.sinaimg.cn/large/006tNc79gy1fdec6ugbwqj30mj02bdg8.jpg)
   
   在Network中可以找到页面加载过程中发生的网络请求，其中代表XHR(XMLHttpRequest)，上滑页面加载新的数据，同时在这里找到了这样一个请求信息。
   ![](https://ww2.sinaimg.cn/large/006tNc79gy1fdecc1itygj30kc02eweq.jpg)
   返回的数据是和列表数据一致的item的集合。由于返回的HTML代码的格式不是很优雅，这里就不上图了。
   
****

   https://www.oschina.net/action/ajax/get_more_recommend_blog?classification=0&p=2  这个URL是一个普通的get请求，包含两个参数classification、p。p应该就是page，是递增的页码。classification应该是博客的分类。这里的分类的参数值就留给你去尝试吧。
   
![](https://ww3.sinaimg.cn/large/006tNc79gy1fdedk3kgdqj307o042a9z.jpg)
****
4. 页面结构和分析我们已经分析完成了，下面开始撸代码，来验证我们分析的是否正确
 
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
                System.out.println(element.html());
            }

            while(true){//查询全部分页数据
                String path = sourcePath+pageIndex;
                Document doc = Jsoup.connect(path).header("User-Agent",ua).timeout(5000).get();
                System.out.println(path);
                Elements blogs = doc.select("div.item");
                if(blogs.size()==0){//没有数据时表示已经没有下一页结束循环
                    break;
                }
                for (Element element:blogs){
                    Element eleAuthor = element.select("div.box-fl > a").get(0);
                    String url = eleAuthor.attr("href");//博客URL
                    String  authorHeaderImage = eleAuthor.select("img").attr("src");//发布者头像
                    String title = element.select("div.box-aw > header > a").get(0).attr("title");//标题
                    String summary = element.select("div.box-aw > section").get(0).html();//简介
                    String  author = element.select("div.box-aw > footer > span").get(0).html();//作者
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
        
  相信上面代码阅读起来没有什么难度。简单的做几点说明：
  * header中加入User-Agent是因为直接读取时报了403错误，使用UA伪装成浏览器访问。
  * 获取到博客数据集只是做了print处理，可以根据自己的需要保存到文件或者数据库。
  * 在使用Select的时候可以在Chrome开发工具中选中要获取的Html标签邮件->Copy->Copy Selector
   
  附上执行结果，程序猿是不会骗人的
  ![](https://ww2.sinaimg.cn/large/006tNc79gy1fdee2yctoaj310t03djur.jpg)
  
##### webmagic
>有些站点,通过异步的方式渲染页面的，通过上述Jsoup的方式获取的Html数据不包含异步请求之后渲染的数据。在爬取类似这样的站点时可以使用[webmagic](http://webmagic.io/docs/zh/)
上面分析过开源中国的博客，这次爬取csdn博客页面，分析方法同上

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    public static void main(String[] args){
        //使用五个线程开启爬虫
        Spider.create(new WebmagicDemo())
                .addPipeline(new webmagic.ConsolePipeline())
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
同样有几点说明的地方

* 获取Html相应标签的信息，作者使用了XSoup的方式获取，XSoup是webmaigci在Jsoup的基础上开发的一款XPath解析器。
* 可以在Chrome开发工具中选中要获取的Html标签邮件->Copy->Copy XPath
* 这里只是webmagic的最简单最基本的用法，在实际的开发中请参照webmagic的官方文档

##### Gecco
>[Gecco]()是一款用java语言开发的轻量化的易用的网络爬虫。Gecco整合了jsoup、httpclient、fastjson、spring、htmlunit、redission等优秀框架，让您只需要配置一些jquery风格的选择器就能很快的写出一个爬虫。Gecco框架有优秀的可扩展性，框架基于开闭原则进行设计，对修改关闭、对扩展开放。

开发手册中详细介绍了爬取京东全部商品信息。我这里是分析了[微广场](http://www.iwgc.cn/)，爬取站点收录的全部微信公众号信息。上面3种使用Java语言的爬取方式代码已上传至[github](https://github.com/thunderZhang1/RobotDemo)。有关分页爬取采用了稍显low的方式，如果你发现了比较优雅的方式还望不吝赐教。
![](https://ww3.sinaimg.cn/large/006tNc79gy1fdffjrh2x1j30mz04fjrw.jpg)

    int pages = pageCount/40+1;//最后一页不足40条
        for(int i=0;i<pages;i++){
            String url = "http://www.iwgc.cn/"+bean.getIndex()+"/p/"+i;
            HttpRequest currentRequest = bean.getRequest();
            sortRequests.add(currentRequest.subRequest(url));
    }
* 获取每个类别收录的总数
* 以每页40条数据，计算总页数

##### Pyhton + urllib
有关[Python](http://www.liaoxuefeng.com/wiki/001374738125095c955c1e6d8bb493182103fac9270762a000)爬虫的教程网上很多，请自行Google。我这里的demo是爬取[豆瓣电影](https://movie.douban.com),并将数据保存到movie.scv文件中
![](https://ww3.sinaimg.cn/large/006tNc79gy1fdfgd8yzuyj30s10gjwgm.jpg)
![](https://ww4.sinaimg.cn/large/006tNc79gy1fdfggn3j5lj30c506ymxs.jpg)

##### 总结
1. 不论使用哪种方式实现网络爬虫,关键在分析目标站点的页面结构
2. 对应直接返回全部HTML信息的站点，直接下载HTMl，然后操作DOC直接获取数据
3. 对于前端异步渲染的页面，分析对应的AJAX请求，获取数据
4. 可以按照自己的需求对爬取的数据进行本地持久化保存。
5. 可以对爬取的数据进行分析，配合加入数据可视化等工具进行分析。
6. 本文只做入门了解，实际中的网络爬虫需求要复杂的多，增量爬取，深度爬取等还需深入研究。