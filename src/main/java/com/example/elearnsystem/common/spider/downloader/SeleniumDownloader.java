package com.example.elearnsystem.common.spider.downloader;

import com.example.elearnsystem.common.spider.util.MyPhantomJSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;

public class SeleniumDownloader extends AbstractDownloader {
    private int threadNum;
    @Override
    public Page download(Request request, Task task) {
        Page page = new Page();
        WebDriver driver = MyPhantomJSDriver.getPhantomJSDriver();
        driver.get(request.getUrl());
        WebElement webElement = driver.findElement(By.xpath("/html")); // 定位网页元素，返回第一个匹配的元素
        String str = webElement.getAttribute("outerHTML");
        Html html = new Html(str);
        page.setRawText(str);
        page.setHtml(new Html(str, request.getUrl()));
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        return page;
    }

    @Override
    public void setThread(int threadNum) {
        this.threadNum = threadNum;
    }
}
