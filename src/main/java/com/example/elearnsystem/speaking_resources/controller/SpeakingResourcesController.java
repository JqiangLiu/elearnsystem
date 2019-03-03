package com.example.elearnsystem.speaking_resources.controller;

import com.example.elearnsystem.common.page.MyPageRequest;
import com.example.elearnsystem.speaking_resources.domain.SpeakingResource;
import com.example.elearnsystem.speaking_resources.domain.dto.SpeakingResourceDTO;
import com.example.elearnsystem.speaking_resources.service.SpeakingResourcesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/managerSpeakingResource")
public class SpeakingResourcesController {
    private final static Logger logger = LoggerFactory.getLogger(SpeakingResourcesController.class);

    @Autowired
    private SpeakingResourcesService speakingResourcesService;

    @PostMapping("/search")
    public List<SpeakingResourceDTO> saveAll(){
        //1、调用爬虫去抓
        // 2、保存在List集合传进去
//        speakingResourcesService.saveAll(resources);
        String resourcesCategory = null;
        Boolean inSystem = null;
        MyPageRequest pageReq = new MyPageRequest();
        pageReq.setPage(1);
        pageReq.setLimit(15);
        pageReq.setSort("inSystem");
        pageReq.setDir("ASC");
        //3、把保存的数据返回到前台
        return speakingResourcesService.findAll(pageReq.getPageable(),resourcesCategory,inSystem);
    }

    @PostMapping("/find")
    public List<SpeakingResourceDTO> find(@RequestParam(name="page") int page, @RequestParam(name="limit") int limit, String resourcesCategory, Boolean inSystem){
        MyPageRequest pageReq = new MyPageRequest();
        pageReq.setPage(page);
        pageReq.setLimit(limit);
        pageReq.setSort("inSystem");
        pageReq.setDir("ASC");
        return speakingResourcesService.findAll(pageReq.getPageable(),resourcesCategory,inSystem);
    }

    @PutMapping("/update")
    public Boolean update(@RequestBody SpeakingResource entity){
        try {
            speakingResourcesService.update(entity);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @PostMapping("/delete")
    public List<SpeakingResourceDTO> delete(Long[] ids,@RequestParam(name="page") int page, @RequestParam(name="limit") int limit, String resourcesCategory, Boolean inSystem){
//        Long[] mids = {43L,44L,46L};
        speakingResourcesService.deleteAll(ids);
        MyPageRequest pageReq = new MyPageRequest();
        pageReq.setPage(1);
        pageReq.setLimit(limit);
        pageReq.setSort("inSystem");
        pageReq.setDir("ASC");
        return speakingResourcesService.findAll(pageReq.getPageable(),resourcesCategory,inSystem);
    }
}
