package io.github.kamarias.web.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.kamarias.entity.RouterMetaEntity;
import io.github.kamarias.exception.CustomException;
import io.github.kamarias.form.add.AddRouterForm;
import io.github.kamarias.mapper.RouterMetaMapper;
import io.github.kamarias.mapstruct.RouterMetaMapStruct;
import io.github.kamarias.vo.RouterMetaVO;
import io.github.kamarias.vo.RouterTransitionVO;
import io.github.kamarias.web.service.RouterMetaService;
import io.github.kamarias.web.service.RouterRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/30 10:30
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RouterMetaServiceImpl extends ServiceImpl<RouterMetaMapper, RouterMetaEntity> implements RouterMetaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouterMetaServiceImpl.class);

    private final RouterRoleService routerRoleService;


    private final RouterMetaMapStruct routerMetaMapStruct;

    public RouterMetaServiceImpl(RouterRoleService routerRoleService, RouterMetaMapStruct routerMetaMapStruct) {
        this.routerRoleService = routerRoleService;
        this.routerMetaMapStruct = routerMetaMapStruct;
    }

    @Override
    public RouterMetaVO getRouterMetaVOByRouterId(String routerId) {
        RouterMetaEntity routerMeta = this.getRouterMetaByRouterId(routerId);
        if (Objects.nonNull(routerMeta)) {
            RouterMetaVO convertBean = routerMetaMapStruct.toRouterMetaVOByRouterMetaEntity(routerMeta);
            convertBean.setTransition(getRouterTransitionVOByJson(routerMeta.getTransition()));
            convertBean.setRoles(routerRoleService.getRoleCodeByRouterId(routerId));
            return convertBean;
        }
        LOGGER.error("路由源数据为空，路由Id：{}", routerId);
        throw new CustomException("路由源数据为空");
    }

    @Override
    public RouterMetaEntity getRouterMetaByRouterId(String routerId) {
        return this.baseMapper.findByRouterIdRouter(routerId);
    }


    @Override
    public boolean deleteRouterMetaByRouterId(String routerId) {
        return this.remove(Wrappers.lambdaQuery(RouterMetaEntity.class).eq(RouterMetaEntity::getRouterId, routerId));
    }

    @Override
    public RouterTransitionVO getRouterTransitionVOByJson(String routerTransitionJson) {
        RouterTransitionVO vo = JSON.parseObject(routerTransitionJson, RouterTransitionVO.class);
        // 动画配置一个属性不为null才返回
        if (StringUtils.isNotBlank(vo.getName()) || StringUtils.isNotBlank(vo.getEnterTransition()) || StringUtils.isNotBlank(vo.getLeaveTransition())){
            return vo;
        }
        return null;
    }

    @Override
    public String getRouterTransitionJsonByVO(RouterTransitionVO routerTransitionVO) {
        return JSON.toJSONString(routerTransitionVO);
    }

    @Override
    public <T extends AddRouterForm> RouterTransitionVO getRouterTransitionVOByAddRouterForm(T form) {
        RouterTransitionVO vo = routerMetaMapStruct.toRouterTransitionVOByExtendsAddRouterForm(form);
        vo.setName(form.getTransitionName());
        return vo;
    }

    @Override
    public <T extends AddRouterForm> T getAddRouterFormByRouterTransitionVO(RouterTransitionVO vo, T form) {
        if (Objects.isNull(vo)) {
            return form;
        }
        form.setTransitionName(vo.getName());
        form.setEnterTransition(vo.getEnterTransition());
        form.setLeaveTransition(vo.getLeaveTransition());
        return form;
    }

}
