package com.example.elearnsystem.common.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EPageProcessor implements PageProcessor {
    private static Integer firstPageNum = 1;
    private static Integer secondPageNum = 1;
    private String FIRST_PAGINATION_URL = "http://xiu\\.kekenet\\.com/index\\.php/main/column\\.html\\?tag\\_id=\\d+";
    private String SECOND_PAGINATION_URL = "http://xiu\\.kekenet\\.com/list/\\d+";
    private Site site = Site
            .me().setCharset("UTF-8")
            .setCycleRetryTimes(3) // 重试次数
            .setSleepTime(3 * 1000) //抓取间隔
            .addHeader("Connection", "keep-alive") // 请求头：表示是否需要持久连接（HTTP 1.1默认进行持久连接）
            .addHeader("Cache-Control", "max-age=0") // 指定请求和响应遵循的缓存机制
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/535.1"); //User-Agent的内容包含发出请求的用户信息

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {
//        WebDriver driver = MyPhantomJSDriver.getPhantomJSDriver();
//        driver.get(page.getRequest().getUrl());
//        WebElement webElement = driver.findElement(By.id("bodycon")); // 定位网页元素，返回第一个匹配的元素
//        String str = webElement.getAttribute("outerHTML"); //获取元素中的值
//        System.out.println(page.getHtml());
        Html html = page.getHtml();
        if (page.getUrl().regex(FIRST_PAGINATION_URL).match()){ //判断
//            if (isFirstListPageonOne(page,html)){
//                analysisFirstPagination(page,html);
//            }
            analysisFirstListPage(page,html);
        }else if (page.getUrl().regex(SECOND_PAGINATION_URL).match()){
//            if (isSecondListPageonOne(page,html)){
//                analysisSecondPagination(page,html);
//            }
            analysisSecondListPage(page,html);
        }else {
            analysisDetailPage(page,html);
        }
    }

    /*
    * 由于该页面具有双层目录，需要定制深度
    * 将首目录的链接抽取，包括所有分页的目录链接
    * 1、判断首目录是否有分页，并将第一页链接全部保存进队列*/
    private void analysisFirstListPage(Page page, Html html){
        List<String> pageList = html.xpath("//div[@class='column_list']/dl/dt/p[1]/a/@href").all();
        page.addTargetRequests(pageList);
    }
    /*
    * 分析首目录分页规则
    * */
    private void analysisFirstPagination(Page page, Html html){
        int i = 2;
        String s = html.xpath("//*[@id=\"list_container\"]/div/div/div/em/text()").all().toString();
        Integer sumPageNum = 0;
        if (!s.equals("")){
            String regEx="[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(s);
            sumPageNum = Integer.parseInt(m.replaceAll("").trim())/7+1;
        }
        List<String> pageList = new ArrayList<>();
        while (i<=sumPageNum){
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
    /*
    * 分析次目录分页规则*/
    private void analysisSecondPagination(Page page, Html html){
        int i = 2;
        String s = html.xpath("//*[@id=\"list_container\"]/div/div/em").all().toString();
        Integer sumPageNum = 0;
        if (!s.equals("")){
            String regEx="[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(s);
            sumPageNum = Integer.parseInt(m.replaceAll("").trim())/10;
        }
        List<String> pageList = new ArrayList<>();
        while (i<=sumPageNum){
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
    /*
    * 分析次目录列表*/
    private void analysisSecondListPage(Page page, Html html){
        List<String> pageList = html.xpath("//*[@id=\"list_container\"]/div/ul/li/a/@href").all();
        page.addTargetRequests(pageList);
    }
    /*
    * 分析页详情
    * */
    private void analysisDetailPage(Page page, Html html){
        page.putField("resourcesParentUrl",page.getUrl().toString());
        page.putField("resourcesTitle",html.xpath("//*[@id=\"nav_btn10\"]/div[7]/div[1]/dl[2]/dt/span[2]/text()").toString());
        page.putField("resourcesNetworkUrl",html.xpath("//*[@id=\"player\"]/param[4]/@value")
                .regex("http://k6.kekenet.com/Sound/\\d+/\\d+/\\w+\\.mp3").toString());
        page.putField("resourcesText",html.xpath("//*[@id=\"nav_btn10\"]/div[7]/div[1]/div/div[1]/text()").toString());
        page.putField("resourcesTranslation_text",html.xpath("//*[@id=\"nav_btn10\"]/div[7]/div[1]/div/div[2]/text()").toString());
    }

    /*
    * 是否为首列表页的第一页
    * */
    private Boolean isFirstListPageonOne(Page page,Html html){
        String s = html.xpath("//*[@id=\"list_container\"]/div/div/div/b/text()").all().toString();
        if (!s.equals("")){
            String regEx="[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(s);
            return (Integer.parseInt(m.replaceAll("").trim()) == 1);
        }else{
            return false;
        }
    }
    /*
    * 是否为次列表页的第一页*/
    private Boolean isSecondListPageonOne(Page page,Html html){
        String s = html.xpath("//*[@id=\"list_container\"]/div/div/b/text()").all().toString();
        if (!s.equals("")){
            String regEx="[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(s);
            return (Integer.parseInt(m.replaceAll("").trim()) == 1);
        }
        return null;
    }
}
