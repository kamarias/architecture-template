package io.github.kamarias.infrastructure.entity;

import io.github.kamarias.infrastructure.entity.base.BaseIdEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/12/17 23:41
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_user")
@org.hibernate.annotations.Table(appliesTo = "t_user", comment = "用户信息表")
public class UserEntity extends BaseIdEntity {


    /**
     * 姓名
     */
    @Column(columnDefinition = "varchar(32) default ''  COMMENT '姓名'", nullable = false)
    private String userName;


    /**
     * 姓名
     */
    @Column(columnDefinition = "varchar(32) default ''  COMMENT '密码'", nullable = false)
    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
