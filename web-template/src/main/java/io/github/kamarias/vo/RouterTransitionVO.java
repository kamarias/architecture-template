package io.github.kamarias.vo;

import com.alibaba.fastjson2.JSON;

import java.io.Serializable;

/**
 * 路由过度动画
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/29 13:48
 */
public class RouterTransitionVO implements Serializable {

    /**
     * 动画效果
     *
     * @see {@link 'https://next.router.vuejs.org/guide/advanced/transitions.html#transitions'}
     * @see 'animate.css' {@link 'https://animate.style'}
     */
    private String name;

    /**
     * 进场动画
     */
    private String enterTransition;

    /**
     * 离场动画
     */
    private String leaveTransition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
