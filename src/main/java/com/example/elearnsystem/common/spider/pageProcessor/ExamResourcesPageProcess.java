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

public class ExamResourcesPageProcess implements PageProcessor {
    private Site site = Site
            .me().setCharset("UTF-8")
            .setCycleRetryTimes(3) // 重试次数
            .setSleepTime(3 * 1000) //抓取间隔
            .addHeader("Connection", "keep-alive") // 请求头：表示是否需要持久连接（HTTP 1.1默认进行持久连接）
            .addHeader("Cache-Control", "max-age=0") // 指定请求和响应遵循的缓存机制
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/535.1"); //User-Agent的内容包含发出请求的用户信息
    @Override
    public void process(Page page) {
//        Html html = page.getHtml();
//        if (page.getUrl().regex(FIRST_PAGINATION_URL).match()){
//            if (isFirstListPageonOne(page,html)){
//                analysisFirstPagination(page,html);
//            }
//            analysisFirstListPage(page,html);
//        }else {
//            analysisDetailPage(page,html);
//        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    /*
     * 将首目录的链接抽取，包括所有分页的目录链接
     * 1、判断首目录是否有分页，并将第一页链接全部保存进队列*/
    private void analysisFirstListPage(Page page, Html html){
        List<String> pageList = html.xpath("//ul[@id=\"menu-list\"]/li/h2/a[2]/@href").all();
        page.addTargetRequests(pageList);
    }

    /*
     * 分析首目录分页规则
     * */
    private void analysisFirstPagination(Page page, Html html){
        int i = 2;
//        String s = html.xpath("//*[@id=\"list_container\"]/div/div/div/em/text()").all().toString();
//        Integer sumPageNum = 0;
//        if (!s.equals("")){
//            String regEx="[^0-9]";
//            Pattern p = Pattern.compile(regEx);
//            Matcher m = p.matcher(s);
//            String r = m.replaceAll("").trim();
//            if (r.length() > 0){
//                sumPageNum = Integer.parseInt(m.replaceAll("").trim())/7+1;
//            }
//        }
        List<String> pageList = new ArrayList<>();
        while (i<=50){
            String cpage = page.getUrl()+"&tab=spoken&page="+i;
            pageList.add(cpage);
            ++i;
        }
        pageList = new ArrayList(new HashSet(pageList));

        List<String> pageParameterList = new ArrayList<>();
        for (String value: pageList) {
            pageParameterList.add(value);
        }
        page.addTargetRequests(pageParameterList);
    }
}
