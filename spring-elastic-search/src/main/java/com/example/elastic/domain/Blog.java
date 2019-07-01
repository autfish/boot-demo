package com.example.elastic.domain;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Document(indexName = "website", type = "blog")
public class Blog {

    private int id;
    private String author;
    private String text;
    private int likes;

    @JSONField(format = "yyyy-MM-dd")
    private Date date;

    public Blog() {
    }

    public Blog(int id, String author, String text, int likes, Date date) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.likes = likes;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String toString() {
        return "[author=" + this.author + ",text=" + this.text + ",likes=" + this.likes + ",date=" + this.date + "]";
    }
}
