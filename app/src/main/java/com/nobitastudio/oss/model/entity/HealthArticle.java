package com.nobitastudio.oss.model.entity;

import com.nobitastudio.oss.model.enumeration.HealthArticleType;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 17:00
 * @description 健康资讯实例
 */
@Data
@Entity
@Table(name = "health_article")
@Getter
@Setter
public class HealthArticle implements Serializable {

    private static final long serialVersionUID = -7258563717193340205L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private HealthArticleType type;

    @Column(name = "publish_time")
    private LocalDateTime publishTime;

    @Column(name = "publish_man")
    private String publishMan;

    @Column(name = "label")
    private String label;

    @Column(name = "url")
    private String url;

    @Column(name = "other_data")
    private String otherData;
}
