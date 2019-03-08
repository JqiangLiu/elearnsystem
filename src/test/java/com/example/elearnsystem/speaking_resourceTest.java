package com.example.elearnsystem;

import com.example.elearnsystem.common.spider.util.MyChromeDriver;
import com.example.elearnsystem.speaking_resources.domain.SpeakingResource;
import com.example.elearnsystem.speaking_resources.service.ISpeakingResourcesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.elearnsystem.common.spider.util.MyPhantomJSDriver.getPhantomJSDriver;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class speaking_resourceTest {
    @Autowired
    private ISpeakingResourcesService speakingResourcesService;
    @Autowired
    private static ChromeDriverService service;

    @Test
    public void saveTest(){
        List<SpeakingResource> lists = new ArrayList<>();
        for(int i = 0; i<3; i++){
            SpeakingResource entity = new SpeakingResource();
            entity.setInSystem(true);
            entity.setResourcesCategory("exam");
            entity.setResourcesNetworkUrl("localhost:8080");
            entity.setResourcesText("i am a text");
            entity.setResourcesTranslation_text(" i am a transation_text");
            entity.setResourcesTitle("title");
            lists.add(entity);
            speakingResourcesService.saveOne(entity);
        }
        speakingResourcesService.saveAll(lists);
        lists.clear();
        for (int i=0; i<3; i++){
            SpeakingResource entity = new SpeakingResource();
            entity.setInSystem(true);
            entity.setResourcesCategory("trip");
            entity.setResourcesNetworkUrl("localhost:8080");
            entity.setResourcesText("i am a text");
            entity.setResourcesTranslation_text(" i am a transation_text");
            entity.setResourcesTitle("title");
            lists.add(entity);
            speakingResourcesService.saveOne(entity);
        }
        speakingResourcesService.saveAll(lists);
        lists.clear();
        for(int i = 0; i<3; i++){
            SpeakingResource entity = new SpeakingResource();
            entity.setInSystem(false);
            entity.setResourcesCategory("exam");
            entity.setResourcesNetworkUrl("localhost:8080");
            entity.setResourcesText("i am a text");
            entity.setResourcesTranslation_text(" i am a transation_text");
            entity.setResourcesTitle("title");
            lists.add(entity);
            speakingResourcesService.saveOne(entity);
        }
        speakingResourcesService.saveAll(lists);
        lists.clear();
        for (int i=0; i<3; i++){
            SpeakingResource entity = new SpeakingResource();
            entity.setInSystem(false);
            entity.setResourcesCategory("trip");
            entity.setResourcesNetworkUrl("localhost:8080");
            entity.setResourcesText("i am a text");
            entity.setResourcesTranslation_text(" i am a transation_text");
            entity.setResourcesTitle("title");
            lists.add(entity);
            speakingResourcesService.saveOne(entity);
        }
        speakingResourcesService.saveAll(lists);
        lists.clear();
    }

//    @Test
//    public void TestChromeDriver() throws IOException {
//        WebDriver driver = MyChromeDriver.getChromeDriver();
//        // 让浏览器访问 Baidu
//        driver.get("https://www.taobao.com/");
//        // 用下面代码也可以实现
//        //driver.navigate().to("http://www.baidu.com");
//        // 获取 网页的 title
//        System.out.println(" Page title is: " +driver.getTitle());
//        // 通过 id 找到 input 的 DOM
//        WebElement element =driver.findElement(By.id("q"));
//        // 输入关键字
//        element.sendKeys("爽肤水");
//        // 提交 input 所在的 form
//        element.submit();
//        // 通过判断 title 内容等待搜索页面加载完毕，间隔秒
//        new WebDriverWait(driver, 10).until(new ExpectedCondition() {
//            @Override
//            public Object apply(Object input) {
//                return ((WebDriver)input).getTitle().toLowerCase().startsWith("爽肤水");
//            }
//        });
//        // 显示搜索结果页面的 title
//        System.out.println(" Page title is: " +driver.getTitle());
//        // 关闭浏览器
//        driver.quit();
//        // 关闭 ChromeDriver 接口
//        service.stop();
//    }

//    @Test
//    public void TestPhantomJsDriver(){
//        WebDriver driver=getPhantomJSDriver();
//        driver.get("http://www.baidu.com");
//        System.out.println(driver.getCurrentUrl());
//    }
}
