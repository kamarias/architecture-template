package io.github.kamarias.vo;

import com.alibaba.fastjson2.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * 动态路由实体
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/29 12:04
 */
public class RouterVO implements Serializable {

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 重定向地址
     */
    private String redirect;

    /**
     * 组件名称
     */
    private String name;

    /**
     * 路由类型
     */
    private Integer type;

    /**
     * 路由原数据
     */
    private RouterMetaVO meta;

    /**
     * 子路由
     */
    private List<RouterVO> children;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RouterMetaVO getMeta() {
        return meta;
    }

    public void setMeta(RouterMetaVO meta) {
        this.meta = meta;
    }

    public List<RouterVO> getChildren() {
        return children;
    }

    public void setChildren(List<RouterVO> children) {
        this.children = children;
    }



    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
