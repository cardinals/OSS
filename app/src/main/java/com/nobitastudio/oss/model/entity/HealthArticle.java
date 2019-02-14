package com.nobitastudio.oss.model.entity;

import com.nobitastudio.oss.model.enumeration.HealthArticleType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 17:00
 * @description 健康资讯实例
 */
public class HealthArticle implements Serializable {

    public HealthArticle() {
        this(1,"id","tile", HealthArticleType.HEALTH_HEADLINE,LocalDateTime.now(),"publishMan","label","url");
    }

    public HealthArticle(Integer id, String iconId, String title, HealthArticleType type, LocalDateTime publishTime, String publishMan, String label, String url) {
        this.id = id;
        this.iconId = iconId;
        this.title = title;
        this.type = type;
        this.publishTime = publishTime;
        this.publishMan = publishMan;
        this.label = label;
        this.url = url;
    }

    private static final long serialVersionUID = -7258563717193340205L;

    private Integer id;

    private String iconId;

    private String title;

    private HealthArticleType type;

    private LocalDateTime publishTime;

    private String publishMan;

    private String label;

    private String url;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HealthArticleType getType() {
        return type;
    }

    public void setType(HealthArticleType type) {
        this.type = type;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublishMan() {
        return publishMan;
    }

    public void setPublishMan(String publishMan) {
        this.publishMan = publishMan;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
