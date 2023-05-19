package io.github.kamarias.entity.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/3/24 17:10
 */
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseOperateEntity extends BaseIdEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 创建人
     */
    @Column(columnDefinition = "varchar(36) NOT NULL DEFAULT '' COMMENT '创建人'")
    private String createBy;


    /**
     * 创建时间
     */
    @CreatedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(columnDefinition = "datetime  comment '创建时间'")
    private LocalDateTime createTime;


    /**
     * 更新人
     */
    @Column(columnDefinition = "varchar(36) NOT NULL DEFAULT '' COMMENT '更新人'")
    private String updateBy;

    /**
     * 更新时间
     */
    @LastModifiedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JSONField(serialize = false)
    @Column(columnDefinition = "datetime  comment '更新时间'")
    private LocalDateTime updateTime;


    /**
     * 删除状态
     */
    @Column(columnDefinition = "tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标记 0:未删除 1:已删除'")
    private Integer delFlag;


    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
