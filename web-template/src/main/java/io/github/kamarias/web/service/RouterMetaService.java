package io.github.kamarias.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.github.kamarias.entity.RouterMetaEntity;
import io.github.kamarias.form.add.AddRouterForm;
import io.github.kamarias.vo.RouterMetaVO;
import io.github.kamarias.vo.RouterTransitionVO;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/30 10:29
 */
public interface RouterMetaService extends IService<RouterMetaEntity> {

    /**
     * 通过路由Id 获取路由原数据信息
     * @param routerId 路由Id
     * @return 返回结果
     */
    RouterMetaVO getRouterMetaVOByRouterId(String routerId);

    /**
     * 通过路由Id获取路由信息
     * @param routerId 路由Id
     * @return 返回路由结果
     */
    RouterMetaEntity getRouterMetaByRouterId(String routerId);

    /**
     * 通过路由id删除路由元数据
     * @param routerId 路由Id
     * @return 返回结果
     */
    boolean deleteRouterMetaByRouterId(String routerId);


    /**
     * 通过 json 字符转换为 RouterTransitionVO 对象
     *
     * @param routerTransitionJson 路由动画对象
     * @return 返回对象
     */
    RouterTransitionVO getRouterTransitionVOByJson(String routerTransitionJson);

    /**
     * 通过 RouterTransitionVO 对象转为json
     *
     * @param routerTransitionVO 路由动画对象
     * @return 返回json
     */
    String getRouterTransitionJsonByVO(RouterTransitionVO routerTransitionVO);

    /**
     * 通过继承addRouterForm的对象转换 RouterTransitionVO
     * @param form 表单
     * @return 返回结果
     * @param <T> 继承 AddRouterForm 的对象
     */
    <T extends AddRouterForm> RouterTransitionVO getRouterTransitionVOByAddRouterForm(T form);


    /**
     * 将 RouterTransitionVO 转为 泛型
     * @param vo RouterTransitionVO 对象
     * @param form 添加的表单信息
     * @return 返回结果
     * @param <T> 继承 AddRouterForm 的对象
     */
    <T extends AddRouterForm> T getAddRouterFormByRouterTransitionVO(RouterTransitionVO vo, T form);


}
