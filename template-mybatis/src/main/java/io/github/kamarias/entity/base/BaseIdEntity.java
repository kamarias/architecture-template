package io.github.kamarias.entity.base;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * 抽象 Id 实体
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/4 10:49
 */
public abstract class BaseIdEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


}
