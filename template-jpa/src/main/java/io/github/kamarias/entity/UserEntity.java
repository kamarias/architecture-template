package io.github.kamarias.entity;

import com.alibaba.fastjson.JSON;
import io.github.kamarias.entity.base.BaseOperateEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author wangyuxing@gogpay.cn
 * @date @DATE @TIME
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_user"
//        , indexes = {
//        @Index(name = "idx_law_user", columnList = "lawId,userId")
//        , @Index(name = "idx_law_user", columnList = "lawId,userId")
//}
)
@org.hibernate.annotations.Table(appliesTo = "t_user", comment = "用户信息表")
@DynamicInsert
@DynamicUpdate
@Where(clause = "del_flag = 0")
@SQLDelete(sql = "update t_user set del_flag = 1 where id = ? ")
public class UserEntity extends BaseOperateEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @Column(columnDefinition = "varchar(32) default ''  COMMENT '姓名'", nullable = false)
    private String name;


    /**
     * 年龄
     */
    @Column(columnDefinition = "int(3) default 0  COMMENT '年龄'", nullable = false)
    private Integer age;

    /**
     * 邮箱
     */
    @Column(columnDefinition = "varchar(32) default ''  COMMENT '邮箱'", nullable = false)
    private String email;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
