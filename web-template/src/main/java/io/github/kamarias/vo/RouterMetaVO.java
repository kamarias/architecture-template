package io.github.kamarias.vo;

import com.alibaba.fastjson2.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/29 12:45
 */
public class RouterMetaVO implements Serializable {


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
     * 页面级别权限设置
     */
    private List<String> roles;

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

    private Integer hiddenTag;

    /**
     * 动态路由可打开的最大数量 `可选`
     */
    private Integer dynamicLevel;

    /**
     * 页面加载动画
     */
    private RouterTransitionVO transition;

    /**
     * 单升序排序，值越高排的越后（只针对顶级路由）`可选`
     */
    private Integer rank;

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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
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

    public RouterTransitionVO getTransition() {
        return transition;
    }

    public void setTransition(RouterTransitionVO transition) {
        this.transition = transition;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
