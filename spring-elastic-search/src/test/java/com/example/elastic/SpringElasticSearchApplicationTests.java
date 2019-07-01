package com.example.elastic;

import com.example.elastic.domain.Blog;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringElasticSearchApplicationTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void contextLoads() {
    }

    private void createIndex() {
        elasticsearchTemplate.createIndex("website");
    }

    private void addDoc() {
        List<IndexQuery> indexQueries = Arrays.asList(
                new IndexQueryBuilder().withObject(new Blog(1, "Mary Jones", "Jane is an expert in her field", 80, parseDate("2019-06-21"))).build(),
                new IndexQueryBuilder().withObject(new Blog(2, "Jane Smith", "I am starting to get the hang of this...", 0, parseDate("2019-06-20"))).build(),
                new IndexQueryBuilder().withObject(new Blog(3, "John Smith", "The Query DSL is really powerful and flexible", 100, parseDate("2019-06-20"))).build(),
                new IndexQueryBuilder().withObject(new Blog(4, "Mary Jones", "Still trying this out...", 0, parseDate("2019-06-20"))).build(),
                new IndexQueryBuilder().withObject(new Blog(5, "Mary Jones", "However did I manage before Elasticsearch?", 200, parseDate("2019-06-19"))).build(),
                new IndexQueryBuilder().withObject(new Blog(6, "Jane Smith", "I like to collect rock albums", 0, parseDate("2019-06-19"))).build(),
                new IndexQueryBuilder().withObject(new Blog(7, "Douglas Fir", "I like to build cabinets", 50, parseDate("2019-06-19"))).build(),
                new IndexQueryBuilder().withObject(new Blog(8, "John Smith", "I love to go rock climbing", 40, parseDate("2019-06-18"))).build(),
                new IndexQueryBuilder().withObject(new Blog(9, "Mary Jones", "I am Mary Jones, welcome to my blog!", 500, parseDate("2019-06-17"))).build(),
                new IndexQueryBuilder().withObject(new Blog(10, "Mary Jones", "My first blog entry", 400, parseDate("2019-06-17"))).build()
        );
        elasticsearchTemplate.bulkIndex(indexQueries);
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
