package com.sdhsie.push;

/**
 * Created by zcx on 2017/1/11.
 */
public class MessageBean {
    private String id;
    private  String title;
    private  String content;
    private  String contentType;

    public MessageBean(String id, String title, String content, String contentType) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.contentType = contentType;
    }

    public MessageBean() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
