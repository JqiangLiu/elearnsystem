package com.example.elearnsystem.common.spider.pageProcessor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewsPageProcessor implements PageProcessor{
    private static boolean flag = true;
    private String LIST_URL = "http://www\\.kekenet\\.com/read/news/([\\s\\S]*)";
    private Site site = Site
            .me().setCharset("UTF-8")
            .setCycleRetryTimes(3) // 重试次数
            .setSleepTime(3 * 1000) //抓取间隔
            .addHeader("Connection", "keep-alive") // 请求头：表示是否需要持久连接（HTTP 1.1默认进行持久连接）
            .addHeader("Cache-Control", "max-age=0") // 指定请求和响应遵循的缓存机制
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/535.1"); //User-Agent的内容包含发出请求的用户信息

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        String s = html.xpath("//div[@class='page th']/a[6]/text()").all().toString();
        Integer sumPageNum = 0;
        if (!s.equals("")) {
            String regEx = "[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(s);
            String r = m.replaceAll("").trim();
            if (r.length() > 0) {
                sumPageNum = Integer.parseInt(m.replaceAll("").trim());
            }
        }
        // 把所有分页链接添加
        if (flag){
            HashSet<String> list = new HashSet<>();
            for (int i = sumPageNum; i>0; i--){
                String src = "http://www.kekenet.com/read/news/List_"+i+".shtml";
                list.add(src);
            }
            List<String> l = new ArrayList(list);
            page.addTargetRequests(l);
            flag = false; // 添加完所有分页地址
        }
        else if (page.getUrl().regex(LIST_URL).match()){ // 列表页
            analysisListPagination(page,html);
        }else{ // 详细页
            analysisDetailPage(page,html);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    private void analysisListPagination (Page page,Html html){ // 分析列表页
        List<String> lists = html.xpath("//ul[@id='menu-list']/li/h2/a/@href").all();
        page.addTargetRequests(lists);
    }

    private void analysisDetailPage(Page page,Html html){ // 抽取详细页内容
        page.putField("resourceImg",html.xpath("//div[@class='info-qh']//img/@src").toString());
        page.putField("resourcesText",html.xpath(""));
    }
}
