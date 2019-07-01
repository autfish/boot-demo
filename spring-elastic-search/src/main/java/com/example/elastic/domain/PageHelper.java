package com.example.elastic.domain;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageHelper<T> {

    private long total;
    private List<T> list;
    private int pageNum;
    private int pageSize;
    private int pages;
    private int previousPageNum;
    private int nextPageNum;
    private boolean previousPage;
    private boolean nextPage;
    private int[] navigatePageNums;

    public PageHelper(Page<T> page, int pageNum, int pageSize) {

        this.list = page.getContent();
        this.total = page.getTotalElements();
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = page.getTotalPages();
        this.previousPage = false;
        this.nextPage = false;

        int startNum = 1;
        int endNum = pages;
        int navigateNums = 10; //导航栏显示数字个数
        if(pages <= navigateNums) {
        } else if ((pageNum + (navigateNums + 1) / 2) > pages) {
            startNum = pages - (navigateNums - 1);
            endNum = pages;
        } else if (pageNum <= (navigateNums + 1) / 2) {
            endNum = navigateNums;
        } else {
            if (navigateNums % 2 == 0) {
                startNum = pageNum - navigateNums / 2;
                endNum = pageNum + (navigateNums - 1) / 2;
            } else {
                startNum = pageNum - navigateNums / 2;
                endNum = pageNum + navigateNums / 2;
            }
        }
        this.navigatePageNums = new int[endNum - startNum + 1];
        for(int i = startNum; i <= endNum; i++) {
            this.navigatePageNums[i - startNum] = i ;
        }

        for(int i = 0; i < this.pages; ++i) {
            this.navigatePageNums[i] = i + 1;
        }
        if (this.pageNum > 1) {
            this.previousPage = true;
            this.previousPageNum = this.pageNum - 1;
        }

        if (this.pageNum < this.pages) {
            this.nextPage = true;
            this.nextPageNum = this.pageNum + 1;
        }
    }

    public long getTotal() {
        return total;
    }

    public List<T> getList() {
        return list;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPages() {
        return pages;
    }

    public int getPreviousPageNum() {
        return previousPageNum;
    }

    public int getNextPageNum() {
        return nextPageNum;
    }

    public boolean isPreviousPage() {
        return previousPage;
    }

    public boolean isNextPage() {
        return nextPage;
    }

    public int[] getNavigatePageNums() {
        return navigatePageNums;
    }
}
