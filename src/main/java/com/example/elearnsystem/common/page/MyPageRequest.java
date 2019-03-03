package com.example.elearnsystem.common.page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class MyPageRequest {
    private int 	page 	= 1;
    private int 	limit	= 15;
    private String  sort	= "id";
    private String  dir 	= "ASC";

    public void setPage(int page) {this.page = page;}
    public void setLimit(int limit) {this.limit = limit;}
    public void setSort(String sort) {this.sort = sort;}
    public void setDir(String dir) {this.dir = dir;}

    public Pageable getPageable()
    {
        Pageable pageable = null;
        if(!sort.trim().equals("") && !dir.trim().equals(""))
        {
            Sort pageSort = new Sort(Sort.Direction.DESC,sort);
            if(!dir.equals("DESC")) {
                pageSort = new Sort(Sort.Direction.ASC,sort);
            }
            pageable =  org.springframework.data.domain.PageRequest.of(page-1, limit,pageSort);
        }else {
            pageable = org.springframework.data.domain.PageRequest.of(page-1, limit);
        }
        return pageable;
    }
}
