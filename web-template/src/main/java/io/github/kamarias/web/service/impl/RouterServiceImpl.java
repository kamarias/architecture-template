package io.github.kamarias.web.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.kamarias.common.CaCheConstants;
import io.github.kamarias.entity.RouterEntity;
import io.github.kamarias.entity.RouterMetaEntity;
import io.github.kamarias.enums.BoolFlagEnum;
import io.github.kamarias.enums.RouterTypeEnum;
import io.github.kamarias.exception.CustomException;
import io.github.kamarias.form.add.AddRouterForm;
import io.github.kamarias.form.update.UpdateRouterForm;
import io.github.kamarias.mapper.RouterMapper;
import io.github.kamarias.mapstruct.RouterMapStruct;
import io.github.kamarias.mapstruct.RouterMetaMapStruct;
import io.github.kamarias.query.base.IdForm;
import io.github.kamarias.vo.RouterManageVO;
import io.github.kamarias.vo.RouterMetaVO;
import io.github.kamarias.vo.RouterSelectVO;
import io.github.kamarias.vo.RouterVO;
import io.github.kamarias.web.service.RouterMetaService;
import io.github.kamarias.web.service.RouterRoleService;
import io.github.kamarias.web.service.RouterService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/29 14:22
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@CacheConfig(cacheNames = CaCheConstants.ROUTER_CACHE_NAME, keyGenerator = "keyGenerator")
public class RouterServiceImpl extends ServiceImpl<RouterMapper, RouterEntity> implements RouterService {

    private final RouterMetaService routerMetaService;

    private final RouterRoleService routerRoleService;

    private final RouterMapStruct routerMapStruct;

    private final RouterMetaMapStruct routerMetaMapStruct;


    public RouterServiceImpl(RouterMetaService routerMetaService, RouterRoleService routerRoleService, RouterMapStruct routerMapStruct, RouterMetaMapStruct routerMetaMapStruct) {
        this.routerMetaService = routerMetaService;
        this.routerRoleService = routerRoleService;
        this.routerMapStruct = routerMapStruct;
        this.routerMetaMapStruct = routerMetaMapStruct;
    }

    @Override
    @Cacheable
    public List<RouterManageVO> getRouterList() {
        List<RouterEntity> list = this.list();
        return treeManage("0", list);
    }

    @Override
    @Cacheable
    public List<RouterVO> getRouters() {
        LambdaQueryWrapper<RouterEntity> wrapper = Wrappers.lambdaQuery(RouterEntity.class).eq(RouterEntity::getState, BoolFlagEnum.YES.getCode());
        return tree("0", this.list(wrapper));
    }

    @Override
    @Cacheable
    public List<RouterSelectVO> getRouterSelect() {
        List<RouterSelectVO> list = new ArrayList<>();
        RouterSelectVO vo = new RouterSelectVO();
        vo.setTitle("主路由");
        vo.setId("0");
        LambdaQueryWrapper<RouterEntity> wrapper = Wrappers.lambdaQuery(RouterEntity.class).eq(RouterEntity::getType, BoolFlagEnum.NOT.getCode());
        vo.setChildren(treeRouterSelect("0", this.list(wrapper)));
        list.add(vo);
        return list;
    }


    @Override
    @CacheEvict(allEntries = true)
    public boolean addRouter(AddRouterForm form) {
        RouterEntity bean = routerMapStruct.toRouterEntityByAddRouterForm(form);
        boolean save = this.save(bean);
        if (!save) {
            return false;
        }
        RouterMetaEntity routerMeta = routerMetaMapStruct.toRouterMetaEntityByAddRouterForm(form);
        routerMeta.setRouterId(bean.getId());
        routerMeta.setTransition(routerMetaService.getRouterTransitionJsonByVO(routerMetaService.getRouterTransitionVOByAddRouterForm(form)));
        if (CollectionUtils.isNotEmpty(form.getRoleIds())) {
            routerRoleService.saveRouterRoleByRoleIds(bean.getId(), form.getRoleIds());
        }
        return routerMetaService.save(routerMeta);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean updateRouter(UpdateRouterForm form) {
        RouterEntity router = this.getById(form.getId());
        routerMapStruct.updateRouterEntityByUpdateRouterForm(form, router);
        boolean update = this.updateById(router);
        if (!update) {
            return false;
        }
        if (!routerMetaService.deleteRouterMetaByRouterId(router.getId())) {
            return false;
        }
        routerRoleService.removeRouterRoleByRouterId(router.getId());
        if (CollectionUtils.isNotEmpty(form.getRoleIds())) {
            routerRoleService.saveRouterRoleByRoleIds(router.getId(), form.getRoleIds());
        }
        RouterMetaEntity routerMeta = routerMetaMapStruct.toRouterMetaEntityByAddRouterForm(form);
        routerMeta.setRouterId(router.getId());
        routerMeta.setTransition(routerMetaService.getRouterTransitionJsonByVO(routerMetaService.getRouterTransitionVOByAddRouterForm(form)));
        return routerMetaService.save(routerMeta);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean deleteRouter(IdForm form) {
        if (this.baseMapper.exists( Wrappers.lambdaQuery(RouterEntity.class).eq(RouterEntity::getParentRouterId, form.getId()))) {
            throw new CustomException("存在子路由，不可删除");
        }
        RouterEntity router = this.getById(form.getId());
        if (!routerMetaService.deleteRouterMetaByRouterId(router.getId())) {
            return false;
        }
        routerRoleService.removeRouterRoleByRouterId(router.getId());
        return this.removeById(router.getId());
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean updateStatus(IdForm form) {
        RouterEntity entity = this.getById(form.getId());
        if (Objects.nonNull(entity)) {
            entity.setState(BoolFlagEnum.YES.getCode().equals(entity.getState()) ? BoolFlagEnum.NOT.getCode() : BoolFlagEnum.YES.getCode());
            return this.updateById(entity);
        }
        throw new CustomException("更新失败，Id异常");
    }


    @Override
    public UpdateRouterForm getRouterInfoById(IdForm form) {
        RouterEntity router = this.getById(form.getId());
        RouterMetaEntity routerMeta = routerMetaService.getRouterMetaByRouterId(router.getId());
        UpdateRouterForm routerInfo = routerMapStruct.toUpdateRouterFormByRouterEntityAndRouterMetaEntity(router, routerMeta);
        routerInfo.setRoleIds(routerRoleService.getRoleIdsByRouterId(router.getId()));
        return routerMetaService.getAddRouterFormByRouterTransitionVO(routerMetaService.getRouterTransitionVOByJson(routerMeta.getTransition()), routerInfo);
    }


    private List<RouterSelectVO> treeRouterSelect(String parentRouterId, final List<RouterEntity> list) {
        return list.stream().filter(x -> parentRouterId.equals(x.getParentRouterId())).map(x -> {
            RouterSelectVO vo = new RouterSelectVO();
            vo.setId(x.getId());
            vo.setTitle(routerMetaService.getRouterMetaVOByRouterId(x.getId()).getTitle());
            vo.setChildren(treeRouterSelect(x.getId(), list));
            return vo;
        }).collect(Collectors.toList());
    }

    private List<RouterManageVO> treeManage(String parentRouterId, final List<RouterEntity> list) {
        return list.stream().filter(x -> parentRouterId.equals(x.getParentRouterId())).map(x -> {
            RouterManageVO vo = routerMapStruct.toRouterManageVOByRouterEntity(x);
            routerMapStruct.updateRouterManageVOByRouterMetaVO(routerMetaService.getRouterMetaVOByRouterId(x.getId()), vo);
            vo.setChildren(treeManage(x.getId(), list));
            return vo;
        }).sorted(Comparator.comparing(RouterManageVO::getRank)).collect(Collectors.toList());
    }

    private List<RouterVO> tree(String parentRouterId, final List<RouterEntity> list) {
        return list.stream().filter(x -> parentRouterId.equals(x.getParentRouterId())).map(x -> {
            RouterVO routerVO = routerMapStruct.toRouterVOByRouterEntity(x);
            RouterMetaVO routerMetaVO = routerMetaService.getRouterMetaVOByRouterId(x.getId());
            if (RouterTypeEnum.DIRECTORY.getCode().equals(x.getType())) {
                // 目录需要 将roles 置为null
                routerMetaVO.setRoles(null);
                RouterMetaVO vo = new RouterMetaVO();
                vo.setIcon(routerMetaVO.getIcon());
                vo.setTitle(routerMetaVO.getTitle());
                vo.setRank(routerMetaVO.getRank());
                routerVO.setMeta(vo);
            } else {
                routerVO.setMeta(routerMetaVO);
            }
            routerVO.setChildren(tree(x.getId(), list));
            return routerVO;
        }).sorted(Comparator.comparing(x -> x.getMeta().getRank())).collect(Collectors.toList());
    }

}
