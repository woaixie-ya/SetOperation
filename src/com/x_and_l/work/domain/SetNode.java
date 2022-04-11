package com.x_and_l.work.domain;

import java.io.Serializable;

// 集合元素类
public class SetNode implements Serializable {
    public String node; // 集合单个元素
    public SetNode next; //结点指针

    public void setNode(String node) {
        this.node = node;
    }
}
