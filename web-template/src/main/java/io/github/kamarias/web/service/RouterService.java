package io.github.kamarias.web.service;



import com.baomidou.mybatisplus.extension.service.IService;
import io.github.kamarias.entity.RouterEntity;
import io.github.kamarias.form.add.AddRouterForm;
import io.github.kamarias.form.update.UpdateRouterForm;
import io.github.kamarias.query.base.IdForm;
import io.github.kamarias.vo.RouterManageVO;
import io.github.kamarias.vo.RouterSelectVO;
import io.github.kamarias.vo.RouterVO;

import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/29 14:22
 */
public interface RouterService extends IService<RouterEntity> {


    /**
     * 获取路由信息
     *
     * @return 返回路由结果
     */
    List<RouterManageVO> getRouterList();

    /**
     * 获取用户登录的路由信息
     *
     * @return
     */
    List<RouterVO> getRouters();

    /**
     * 获取路由选择树
     *
     * @return 返回结果
     */
    List<RouterSelectVO> getRouterSelect();


    /**
     * 添加路由信息
     *
     * @param form 添加路由表单
     * @return 添加结果
     */
    boolean addRouter(AddRouterForm form);

    /**
     * 更新路由信息
     *
     * @param form 更新路由表单
     * @return 更新结果
     */
    boolean updateRouter(UpdateRouterForm form);


    /**
     * 删除路由信息
     *
     * @param form id表单
     * @return 删除结果
     */
    boolean deleteRouter(IdForm form);

    /**
     * 更新路由状态
     *
     * @param form 表单Id
     * @return 更新结果
     */
    boolean updateStatus(IdForm form);


    /**
     * 获取路由的详细详细
     * @param form id表单
     * @return 返回结果
     */
    UpdateRouterForm getRouterInfoById(IdForm form);

}
