package com.thunder.gecco.iwgc;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HrefBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjingjing on 2016/12/5.
 */
@PipelineName("categoriesPipeline")

public class CategoriesPipeline implements Pipeline<WechatCategory> {
    public static List<HttpRequest> sortRequests = new ArrayList<HttpRequest>();
    @Override
    public void process(WechatCategory bean) {
        List<HrefBean> groups = bean.getGroups();
        process(bean,groups);
    }

    public void process(WechatCategory wechatCategory, List<HrefBean> urls){
        if(urls == null) {
            return;
        }
        for(HrefBean hrefBean : urls) {
            String url  = hrefBean.getUrl();
            HttpRequest currRequest = wechatCategory.getRequest();
            sortRequests.add(currRequest.subRequest(url));
        }
    }
}
