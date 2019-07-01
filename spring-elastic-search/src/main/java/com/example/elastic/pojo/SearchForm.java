package com.example.elastic.pojo;

public class SearchForm {

    private int type;
    private String keyword;
    private String author;
    private String startDate;
    private String endDate;
    private String minLikes;
    private String maxLikes;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMinLikes() {
        return minLikes;
    }

    public void setMinLikes(String minLikes) {
        this.minLikes = minLikes;
    }

    public String getMaxLikes() {
        return maxLikes;
    }

    public void setMaxLikes(String maxLikes) {
        this.maxLikes = maxLikes;
    }
}
