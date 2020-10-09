package com.zp.fw.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单，包含了标题，href,儿子节点
 * @author Administrator
 *
 */
public class ContentItem{

    private String title;
    private String url;//href
    private int size;//resource的大小

    //孩子节点
    private List<ContentItem> children;

    public ContentItem() {
        super();
    }

    public ContentItem(String title, String url,int size) {
        super();
        this.title = title;
        this.url = url;
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ContentItem> getChildren() {
        if(this.children==null){
            children = new ArrayList<ContentItem>();
        }
        return children;
    }

    public void setChildren(List<ContentItem> children) {
        this.children = children;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}