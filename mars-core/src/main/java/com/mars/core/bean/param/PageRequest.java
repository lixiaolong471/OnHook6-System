package com.mars.core.bean.param;

/**
 * Created by lixl on 2017/2/17.
 */
public class PageRequest extends Request {

    private static final int DEFAULT_PAGE_ROWS = 1000;
    private static final int START_PAGE_NUMBER = 1;

    private int page;

    private int rows;

    public org.springframework.data.domain.PageRequest getPageRequest() {
        org.springframework.data.domain.PageRequest pageRequest = new org.springframework.data.domain.PageRequest(page,rows);


        return pageRequest;
    }

    public PageRequest(){
        super();
        rows = DEFAULT_PAGE_ROWS;
        this.page = START_PAGE_NUMBER;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getDisplayStart(){
        return (page - 1) * rows;
    }
}
