package com.thunder.gecco.iwgc;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangjingjing on 2016/12/5.
 */
@PipelineName("wechatItemPagePipeline")
public class WechatItemPagePipeline implements Pipeline<WechaItems> {
    public static List<HttpRequest> sortRequests = new ArrayList<HttpRequest>();
    @Override
    public void process(WechaItems bean) {
        String title = bean.getTitle();
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(title);
        int count = 0;
        try{
            count = Integer.parseInt(m.replaceAll("").trim());
        }catch (Exception e){
            count = 0;
        }
        List<Item> items = bean.getItems();
        process(bean,items,count);

    }
    public void process(WechaItems bean,List<Item> items,int pageCount){
        if(items == null){
            return;
        }
        int pages = pageCount/40+1;//最后一页不足40条
        for(int i=0;i<pages;i++){
            String url = "http://www.iwgc.cn/"+bean.getIndex()+"/p/"+i;
            HttpRequest currentRequest = bean.getRequest();
            sortRequests.add(currentRequest.subRequest(url));
        }
    }
}
