package com.bwie.threadstudy.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class FileInfo implements Serializable {
    @Id
    private long id;
    @Property(nameInDb = "url")
    private String url; //URL
    @Property(nameInDb = "length")
    private int length; //长度或结束位置
    @Property(nameInDb = "start")
    private int start; //开始位置
    @Property(nameInDb = "now")
    private int now;//当前进度
    public int getNow() {
        return this.now;
    }
    public void setNow(int now) {
        this.now = now;
    }
    public int getStart() {
        return this.start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getLength() {
        return this.length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Generated(hash = 692062177)
    public FileInfo(long id, String url, int length, int start, int now) {
        this.id = id;
        this.url = url;
        this.length = length;
        this.start = start;
        this.now = now;
    }
    @Generated(hash = 1367591352)
    public FileInfo() {
    }
}
