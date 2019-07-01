package com.example.elastic.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseException;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

@RestController
public class ClientDemo {

    @Autowired
    private RestClient restClient;

    @RequestMapping("/createIndex")
    public String createIndex(String indexName) {
        //http://localhost:8080/createIndex?indexName=website
        Request request = new Request("PUT", "/" + indexName);
        request.setJsonEntity("{\"mappings\":{\"blog\":{\"properties\":{\"date\":{\"type\":\"date\",\"format\":\"yyyy-MM-dd\"}}}}}");
        Response response = null;
        try {
            response = restClient.performRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        if(response.getStatusLine().getStatusCode() == 200) {
            JSONObject jsonObject = parseResponse(response);
            return jsonObject.toJSONString();
        } else {
            return "status:" + response.getStatusLine().getStatusCode();
        }
    }

    @RequestMapping("/deleteIndex")
    public String deleteIndex(String indexName) {
        //http://localhost:8080/deleteIndex?indexName=website
        Request request = new Request("DELETE", "/" + indexName);
        Response response = null;
        try {
            response = restClient.performRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        if(response.getStatusLine().getStatusCode() == 200) {
            JSONObject jsonObject = parseResponse(response);
            return jsonObject.toJSONString();
        } else {
            return "status:" + response.getStatusLine().getStatusCode();
        }
    }

    @RequestMapping("/addDoc")
    public String addDoc(String indexName, String type, String doc) {
        //http://localhost:8080/addDoc?indexName=boss&type=login&doc=%7b%22accountId%22%3a%22220499965%22%2c%22channelId%22%3a206700000%2c%22charId%22%3a%22%22%2c%22deviceId%22%3a%22b5e9bf5a-e8e0-5a9a-0adb-5a804c70ddc1%22%2c%22gameId%22%3a67%2c%22ip%22%3a%22101.93.88.9%22%2c%22serverId%22%3a1220%2c%22time%22%3a%222019-06-19+13%3a41%3a23%22%7d
        Request request = new Request("POST", "/" + indexName + "/" + type + "/");
        request.setJsonEntity(doc);
        Response response = null;
        try {
            response = restClient.performRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        if(response.getStatusLine().getStatusCode() == 201) {
            JSONObject jsonObject = parseResponse(response);
            return jsonObject.toJSONString();
        } else {
            return "status:" + response.getStatusLine().getStatusCode();
        }
    }

    @RequestMapping("/emptySearch")
    public String emptySearch(String indexName, String type, int from , int size) {
        Request request = new Request("GET", "/" + indexName + "/" + type + "/_search?from=" + from + "&size=" + size);
        Response response = null;
        try {
            response = restClient.performRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        if(response.getStatusLine().getStatusCode() == 200) {
            JSONObject jsonObject = parseResponse(response);
            System.out.println(jsonObject.toJSONString());
            return formatHits(jsonObject);
        } else {
            return "status:" + response.getStatusLine().getStatusCode();
        }
    }

    @RequestMapping("/simpleSearch")
    public String simpleSearch(String indexName, String type, String query) {
        //Smith
        //author:Smith
        //+author:Smith +text:to
        //+author:Smith -text:like
        //+author:(Mary Fir John)
        //+author:(Mary Fir John) +date:>2019-06-18
        //date:2019
        //date:2019-06-20
        //likes:>=40
        String endpoint = "/" + indexName + "/" + type + "/_search?q=";
        try {
            endpoint = endpoint + java.net.URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Request request = new Request("GET", endpoint);
        Response response = null;
        try {
            response = restClient.performRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        if(response.getStatusLine().getStatusCode() == 200) {
            JSONObject jsonObject = parseResponse(response);
            return formatHits(jsonObject);
        } else {
            return "status:" + response.getStatusLine().getStatusCode();
        }
    }

    @RequestMapping("/getById")
    public String getById(String indexName, String type, String id) {
        Request request = new Request("GET", "/" + indexName + "/" + type + "/" + id);
        Response response = null;
        try {
            response = restClient.performRequest(request);
        } catch (ResponseException e) {
            if(e.getResponse().getStatusLine().getStatusCode() == 404) {
                return "not found";
            }
            return "error";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        if(response.getStatusLine().getStatusCode() == 200) {
            JSONObject jsonObject = parseResponse(response);
            if(jsonObject.getBooleanValue("found")) {
                return jsonObject.getString("_source");
            }
            return jsonObject.toJSONString();
        } else {
            return "status:" + response.getStatusLine().getStatusCode();
        }
    }

    @RequestMapping("/exist")
    public String exist(String indexName, String type, String id) {
        Request request = new Request("HEAD", "/" + indexName + "/" + type + "/" + id);
        Response response = null;
        try {
            response = restClient.performRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        if(response.getStatusLine().getStatusCode() == 200) {
            return "exists";
        } else if(response.getStatusLine().getStatusCode() == 404) {
            return "not exists";
        } else {
            return "status:" + response.getStatusLine().getStatusCode();
        }
    }

    @RequestMapping("/updateDoc")
    public String updateDoc(String indexName, String type, String id, String doc) {
        Request request = new Request("PUT", "/" + indexName + "/" + type + "/" + id);
        request.setJsonEntity(doc);
        Response response = null;
        try {
            response = restClient.performRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        if(response.getStatusLine().getStatusCode() == 200) {
            JSONObject jsonObject = parseResponse(response);
            return jsonObject.toJSONString();
        } else {
            return "status:" + response.getStatusLine().getStatusCode();
        }
    }

    @RequestMapping("/deleteDoc")
    public String deleteDoc(String indexName, String type, String id) {
        Request request = new Request("DELETE", "/" + indexName + "/" + type + "/" + id);
        Response response = null;
        try {
            response = restClient.performRequest(request);
        } catch (ResponseException e) {
            if(e.getResponse().getStatusLine().getStatusCode() == 404) {
                return "not found";
            }
            return "error";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        if(response.getStatusLine().getStatusCode() == 200) {
            return "success";
        } else {
            return "status:" + response.getStatusLine().getStatusCode();
        }
    }

    @RequestMapping("/getMapping")
    public String getMapping(String indexName, String type) {
        //http://localhost:8080/getMapping?indexName=website&type=blog
        Request request = new Request("GET", "/" + indexName + "/_mapping/" + type);
        Response response = null;
        try {
            response = restClient.performRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        if(response.getStatusLine().getStatusCode() == 200) {
            JSONObject jsonObject = parseResponse(response);
            return jsonObject.toJSONString();
        } else {
            return "status:" + response.getStatusLine().getStatusCode();
        }
    }

    @RequestMapping("/queryDSL")
    public String queryDSL(String indexName, String type, String query) {
        //{"query":{"match":{"_all": "Jane"}}}
        //{"query":{"match":{"author": "Smith"}}}
        //{"query":{"match_phrase":{"text":"I like"}},"highlight":{"fields":{"text":{}}}}
        //{"query":{"multi_match":{"query":"I like Jane","fields":["author","text"]}},"highlight":{"fields":{"text":{},"author":{}}}}
        //{"query":{"bool":{"must":{"match":{"author":"Smith"}},"must_not":{"match":{"author":"Jane"}},"should":{"match":{"text":"to"}}}}}
        //{"query":{"range":{"likes":{"gte":30,"lt":110}}}}
        //{"query":{"term":{"date":"2019-06-20"}}}
        //{"query":{"exists":{"field":"date"}}}
        //{"query":{"exists":{"field":"dislikes"}}}
        //{"query":{"exists":{"field":"date"}},"sort":{"date":{"order":"desc"}}}
        String endpoint = "/" + indexName + "/" + type + "/_search";
        Request request = new Request("GET", endpoint);
        request.setJsonEntity(query);
        Response response = null;
        try {
            response = restClient.performRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        if(response.getStatusLine().getStatusCode() == 200) {
            JSONObject jsonObject = parseResponse(response);
            return formatHits(jsonObject);
        } else {
            return "status:" + response.getStatusLine().getStatusCode();
        }
    }

    private JSONObject parseResponse(Response response) {
        StringBuilder builder = new StringBuilder();
        try(InputStream inputStream= response.getEntity().getContent()) {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                builder.append(new String(buffer, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            return JSON.parseObject(builder.toString());
        } catch (Exception ex) {
            return null;
        }
    }

    private String formatHits(JSONObject jsonObject) {
        if(jsonObject.containsKey("hits")) {
            StringBuilder builder = new StringBuilder();
            JSONObject hits = jsonObject.getJSONObject("hits");
            int total = hits.getIntValue("total");
            builder.append("共").append(total).append("条").append("<BR />");
            if(total > 0) {
                JSONArray jsonArray = hits.getJSONArray("hits");
                for(Object o : jsonArray) {
                    JSONObject hit = (JSONObject)o;
                    builder.append(hit.getString("_id")).append(":");
                    builder.append(hit.getString("_source")).append("<BR />");
                }
            }
            return builder.toString();
        } else {
            return jsonObject.toJSONString();
        }
    }
}
