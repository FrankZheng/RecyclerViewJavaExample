package com.example.recyclerviewjavaexample;

import java.util.List;

public class GroupItemVO implements VO {
    private long id;
    private String text;
    private List<VO> items;

    public GroupItemVO(long id, String text, List<VO> items) {
        this.id = id;
        this.text = text;
        this.items = items;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<VO> getItems() {
        return items;
    }

    public void setItems(List<VO> items) {
        this.items = items;
    }
}
