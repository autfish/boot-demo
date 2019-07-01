package com.example.elastic.controller;

import com.example.elastic.domain.Blog;
import com.example.elastic.domain.PageHelper;
import com.example.elastic.es.HighlightResultMapper;
import com.example.elastic.pojo.SearchForm;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.Date;

@Controller
public class TemplateDemo {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @RequestMapping("/blogs")
    public String query(Model model, SearchForm searchForm, Integer pageNum, Integer pageSize) {

        if(pageNum == null || pageNum <= 0)
            pageNum = 1;
        if(pageSize == null || pageSize <= 0)
            pageSize = 5;

        SortBuilder sortBuilder = SortBuilders.fieldSort("date").order(SortOrder.DESC);

        SearchQuery searchQuery = null;
        String authorFieldName = "author";
        String textFieldName = "text";
        String preTags = "<span style=\"color:#F56C6C\">";
        String postTags = "</span>";
        HighlightBuilder.Field authorField = new HighlightBuilder.Field(authorFieldName).preTags(preTags).postTags(postTags);
        HighlightBuilder.Field textField = new HighlightBuilder.Field(textFieldName).preTags(preTags).postTags(postTags);

        if(searchForm.getType() == 0) {
            if (StringUtils.isNotBlank(searchForm.getKeyword())) {
                searchQuery = new NativeSearchQueryBuilder()
                        .withPageable(new QPageRequest(pageNum - 1, pageSize))
                        .withQuery(QueryBuilders.multiMatchQuery(searchForm.getKeyword(), "text", "author"))
                        .withHighlightFields(authorField, textField)
                        .withSort(sortBuilder)
                        .build();
            } else {
                searchQuery = new NativeSearchQueryBuilder()
                        .withPageable(new QPageRequest(pageNum - 1, pageSize))
                        .withQuery(QueryBuilders.matchAllQuery())
                        .withSort(sortBuilder)
                        .build();
            }
        } else {
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            if(StringUtils.isNotBlank(searchForm.getAuthor())) {
                queryBuilder.must(QueryBuilders.termQuery("author.keyword", searchForm.getAuthor()));
            }
            if(StringUtils.isNotBlank(searchForm.getStartDate()) || StringUtils.isNotBlank(searchForm.getEndDate())) {
                RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("date");
                if(StringUtils.isNotBlank(searchForm.getStartDate())) {
                    rangeQueryBuilder = rangeQueryBuilder.gte(parseDate(searchForm.getStartDate()).getTime());
                }
                if(StringUtils.isNotBlank(searchForm.getEndDate())) {
                    rangeQueryBuilder = rangeQueryBuilder.lte(parseDate(searchForm.getEndDate()).getTime());
                }
                queryBuilder.must(rangeQueryBuilder);
            }
            if(StringUtils.isNotBlank(searchForm.getMinLikes()) || StringUtils.isNotBlank(searchForm.getMaxLikes())) {
                RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("likes");
                if(StringUtils.isNotBlank(searchForm.getMinLikes())) {
                    int minLikes = NumberUtils.toInt(searchForm.getMinLikes());
                    rangeQueryBuilder = rangeQueryBuilder.gte(minLikes);
                }
                if(StringUtils.isNotBlank(searchForm.getMaxLikes())) {
                    int maxLikes = NumberUtils.toInt(searchForm.getMaxLikes());
                    rangeQueryBuilder = rangeQueryBuilder.lte(maxLikes);
                }
                queryBuilder.must(rangeQueryBuilder);
            }
            searchQuery = new NativeSearchQueryBuilder()
                    .withPageable(new QPageRequest(pageNum - 1, pageSize))
                    .withQuery(queryBuilder)
                    .withHighlightFields(authorField, textField)
                    .withSort(sortBuilder)
                    .build();
        }
        Page<Blog> blogs = elasticsearchTemplate.queryForPage(searchQuery, Blog.class, new HighlightResultMapper());
        model.addAttribute("blogs", blogs.getContent());
        model.addAttribute("pageHelper", new PageHelper<>(blogs, pageNum, pageSize));
        model.addAttribute("searchForm", searchForm);
        return "index";
    }

    @RequestMapping("/like")
    @ResponseBody
    public String like(int id) {
        GetQuery getQuery = new GetQuery();
        getQuery.setId(String.valueOf(id));
        Blog blog = elasticsearchTemplate.queryForObject(getQuery, Blog.class);
        blog.setLikes(blog.getLikes() + 1);

        IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(blog.getId())).withObject(blog).build();
        elasticsearchTemplate.index(indexQuery);
        return "OK";
    }

    private Date parseDate(String dateString) {
        String parsePattern = "yyyy-MM-dd";
        try {
            Date d = DateUtils.parseDate(dateString, parsePattern);
            return d;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
