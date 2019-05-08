package com.example.elearnsystem.common.spider.pageProcessor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExamResourcesPageProcess implements PageProcessor {
    private static boolean flag = true;
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
        if (flag){
            analysisDetailPageFirst(page,html);
        }
        else {
            analysisDetailPageSecond(page,html);
        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    private void analysisDetailPageFirst(Page page, Html html){
        List<String> pageList = html.xpath("//div[@class='pic']/div/a/@href").all();
        /**用set集合进行过滤*/
        List<String> uniquePageList = new ArrayList<String>(new HashSet<String>(pageList));
        List<String> list = new ArrayList<>();
        for (String s:uniquePageList) {
            list.add("www.kekenet.com" + s);
        }
        page.addTargetRequests(list);
        flag = false;
        page.putField("resourcesParentUrl",page.getUrl().toString());
        page.putField("resourcesTitle",html.xpath("//div[@class='e_title']/h1/text()").toString());
        page.putField("resourcesDate",html.xpath("//cite/time/text()").regex("时间:([\\s\\S]*)").toString());
        page.putField("resourcesCite",html.xpath("//cite/text()").toString());
        page.putField("resourcesNetworkUrl","http://k6.kekenet.com/"+html.xpath("//div[@style='margin-bottom:4px;']/script[2]")
                .regex("var thunder_url =\"([\\s\\S]*)mp3").toString()+"mp3");
        List<String> listEn = html.xpath("//div[@class='info-qh']/div[@class='qh_en']").all();
        List<String> listZg = html.xpath("//div[@class='info-qh']/div[@class='qh_zg']").all();
        StringBuffer contentText = new StringBuffer();
        StringBuffer contentTranslationText = new StringBuffer();
        for (String s:listEn) {
            contentText.append(s.substring(s.indexOf(">")+1,s.indexOf("</div>")));
        }
        for (String s:listZg) {
            contentTranslationText.append(s.substring(s.indexOf(">")+1,s.indexOf("</div>")));
        }
        page.putField("resourcesText",contentText);
        page.putField("resourcesTranslation_text",contentTranslationText);
    }

    private void analysisDetailPageSecond(Page page, Html html){
        page.putField("resourcesParentUrl",page.getUrl().toString());
        page.putField("resourcesTitle",html.xpath("//div[@class='e_title']/h1/text()").toString());
        page.putField("resourcesDate",html.xpath("//cite/time/text()").regex("时间:([\\s\\S]*)").toString());
        page.putField("resourcesCite",html.xpath("//cite/text()").toString());
        page.putField("resourcesNetworkUrl","http://k6.kekenet.com/"+html.xpath("//div[@style='margin-bottom:4px;']/script[2]")
                .regex("var thunder_url =\"([\\s\\S]*)mp3").toString()+"mp3");
        List<String> listEn = html.xpath("//div[@class='info-qh']/div[@class='qh_en']").all();
        List<String> listZg = html.xpath("//div[@class='info-qh']/div[@class='qh_zg']").all();
        StringBuffer contentText = new StringBuffer();
        StringBuffer contentTranslationText = new StringBuffer();
        for (String s:listEn) {
            contentText.append(s.substring(s.indexOf(">")+1,s.indexOf("</div>")));
        }
        for (String s:listZg) {
            contentTranslationText.append(s.substring(s.indexOf(">")+1,s.indexOf("</div>")));
        }
        page.putField("resourcesText",contentText);
        page.putField("resourcesTranslation_text",contentTranslationText);
    }
}
