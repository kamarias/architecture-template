package io.github.kamarias.vo;

import com.alibaba.fastjson2.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/8 13:00
 */
public class RouterSelectVO implements Serializable {


    /**
     * 路由Id
     */
    private String id;

    /**
     * 路由名称
     */
    private String title;


    /**
     * 子路由
     */
    private List<RouterSelectVO> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RouterSelectVO> getChildren() {
        return children;
    }

    public void setChildren(List<RouterSelectVO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


}
