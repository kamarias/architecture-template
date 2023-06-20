package io.github.kamarias.form.add;

import com.alibaba.fastjson2.JSON;
import io.github.kamarias.vo.RouterTransitionVO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/5 22:36
 */
public class AddRouterForm implements Serializable {

    /**
     * 路由地址
     */
    @NotBlank(message = "路由路径不能为空")
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
    @NotNull(message = "路由类型不能为空")
    private Integer type;

    /**
     * 父级路由Id
     */
    @NotBlank(message = "上级路由不能为空")
    private String parentRouterId;


    /**
     * 菜单名称（兼容国际化、非国际化，如何用国际化的写法就必须在根目录的`locales`文件夹下对应添加）`必填`
     */
    @NotBlank(message = "路由名称不能为空")
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
     * 是否显示页签
     */
    private Integer hiddenTag;

    /**
     * 动态路由可打开的最大数量 `可选`
     */
    private Integer dynamicLevel;

    /**
     * 单升序排序，值越高排的越后（只针对顶级路由）`可选`
     */
    @NotNull(message = "路由排序不能为空")
    private Integer rank;

    /**
     * 页面加载动画 (存 json)
     *
     * @see RouterTransitionVO
     */
    private String transitionName;

    /**
     * 进场动画
     */
    private String enterTransition;

    /**
     * 离场动画
     */
    private String leaveTransition;

    /**
     * 角色Id的集合
     */
    private List<String> roleIds;

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


    public String getTransitionName() {
        return transitionName;
    }

    public void setTransitionName(String transitionName) {
        this.transitionName = transitionName;
    }

    public String getEnterTransition() {
        return enterTransition;
    }

    public void setEnterTransition(String enterTransition) {
        this.enterTransition = enterTransition;
    }

    public String getLeaveTransition() {
        return leaveTransition;
    }

    public void setLeaveTransition(String leaveTransition) {
        this.leaveTransition = leaveTransition;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
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
