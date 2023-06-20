package io.github.kamarias.vo;

import com.alibaba.fastjson2.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/8 9:16
 */
public class RouterManageVO implements Serializable {

    /**
     * 路由Id
     */
    private String id;

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
     * 路由类型（0、目录，1、菜单）
     */
    private Integer type;

    /**
     * 父级路由Id
     */
    private String parentRouterId;


    /**
     * 菜单名称（兼容国际化、非国际化，如何用国际化的写法就必须在根目录的`locales`文件夹下对应添加）`必填`
     */
    private String title;

    /**
     * 菜单图标 `可选`
     */
    private String icon;

    /**
     * 菜单名称右侧的额外图标 `可选`
     */
    private String extraIcon;

    /**
     * 是否在菜单中显示（默认`true`）`可选`
     */
    private Integer showLink;

    /**
     * 是否显示父级菜单
     */
    private Integer showParent;

    /**
     * 路由组件缓存（开启 `true`、关闭 `false`）`可选`
     */
    private Integer keepAlive;

    /**
     * 内嵌的`iframe`链接 `可选`
     */
    private String frameSrc;

    /**
     * `iframe`页是否开启首次加载动画（默认`true`）`可选`
     */
    private Integer frameLoading;

    /**
     * 是否在tag中显示
     */
    private Integer hiddenTag;

    /**
     * 动态路由可打开的最大数量 `可选`
     */
    private Integer dynamicLevel;

    /**
     * 是否路由启用状态（0、停用，1、启用）
     */
    private Integer state;

    /**
     * 页面加载动画 (存 json)
     *
     * @see RouterTransitionVO
     */
    private String transition;

    /**
     * 单升序排序，值越高排的越后（只针对顶级路由）`可选`
     */
    private Integer rank;

    /**
     * 子路由
     */
    private List<RouterManageVO> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getParentRouterId() {
        return parentRouterId;
    }

    public void setParentRouterId(String parentRouterId) {
        this.parentRouterId = parentRouterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getExtraIcon() {
        return extraIcon;
    }

    public void setExtraIcon(String extraIcon) {
        this.extraIcon = extraIcon;
    }

    public Integer getShowLink() {
        return showLink;
    }

    public void setShowLink(Integer showLink) {
        this.showLink = showLink;
    }

    public Integer getShowParent() {
        return showParent;
    }

    public void setShowParent(Integer showParent) {
        this.showParent = showParent;
    }

    public Integer getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Integer keepAlive) {
        this.keepAlive = keepAlive;
    }

    public String getFrameSrc() {
        return frameSrc;
    }

    public void setFrameSrc(String frameSrc) {
        this.frameSrc = frameSrc;
    }

    public Integer getFrameLoading() {
        return frameLoading;
    }

    public void setFrameLoading(Integer frameLoading) {
        this.frameLoading = frameLoading;
    }

    public Integer getHiddenTag() {
        return hiddenTag;
    }

    public void setHiddenTag(Integer hiddenTag) {
        this.hiddenTag = hiddenTag;
    }

    public Integer getDynamicLevel() {
        return dynamicLevel;
    }

    public void setDynamicLevel(Integer dynamicLevel) {
        this.dynamicLevel = dynamicLevel;
    }

    public String getTransition() {
        return transition;
    }

    public void setTransition(String transition) {
        this.transition = transition;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public List<RouterManageVO> getChildren() {
        return children;
    }

    public void setChildren(List<RouterManageVO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
