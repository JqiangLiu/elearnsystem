package com.example.elearnsystem.common.spider.scheduler;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.PriorityScheduler;

import java.util.HashMap;
import java.util.Map;

public class LevelLimitScheduler extends PriorityScheduler {
    private int levelLimit = 3;
    public LevelLimitScheduler(){}
    public LevelLimitScheduler(int levelLimit) {
        this.levelLimit = levelLimit;
    }

//    @Override
//    public synchronized void push(Request request, Task task) {
//        if (((Integer) request.getExtra("_level")) <= levelLimit) {
//            Map<String,Object> extras = new HashMap<>();
//            extras.put("_level",(Integer) request.getExtra("_level") + 1);
//            request.setExtras(extras);
//        }
//    }
}
