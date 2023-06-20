package io.github.kamarias.web.controller.system;


import io.github.kamarias.annotation.RequiresPermissions;
import io.github.kamarias.annotation.WebLog;
import io.github.kamarias.dto.AjaxResult;
import io.github.kamarias.form.add.AddRouterForm;
import io.github.kamarias.form.update.UpdateRouterForm;
import io.github.kamarias.query.base.IdForm;
import io.github.kamarias.vo.RouterManageVO;
import io.github.kamarias.vo.RouterSelectVO;
import io.github.kamarias.vo.RouterVO;
import io.github.kamarias.web.annotation.RepeatSubmit;
import io.github.kamarias.web.service.RouterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/29 14:21
 */
@RestController
@RequestMapping("/router")
public class RouterController {

    private final RouterService routerService;

    public RouterController(RouterService routerService) {
        this.routerService = routerService;
    }

    /**
     * 查询用户的路由信息
     */
    @WebLog("查询用户的路由信息")
    @GetMapping("/getRouters")
    public AjaxResult<List<RouterVO>> getRouters() {
        List<RouterVO> list = routerService.getRouters();
        return AjaxResult.success(list);
    }


    /**
     * 获取路由管理树
     */
    @WebLog("获取路由管理树")
    @GetMapping("/list")
    @RequiresPermissions("system:router:query")
    public AjaxResult<List<RouterManageVO>> getList() {
        List<RouterManageVO> list = routerService.getRouterList();
        return AjaxResult.success(list);
    }


    /**
     * 获取路由选择树
     */
    @WebLog("获取路由选择树")
    @GetMapping("/getRouterSelect")
    public AjaxResult<List<RouterSelectVO>> getRouterSelect() {
        List<RouterSelectVO> list = routerService.getRouterSelect();
        return AjaxResult.success(list);
    }


    @WebLog("添加路由信息")
    @PostMapping("/add")
    @RepeatSubmit
    @RequiresPermissions("system:router:add")
    public AjaxResult<Boolean> addRouter(@RequestBody @Validated AddRouterForm form) {
        return AjaxResult.success(routerService.addRouter(form));
    }

    @WebLog("更新路由信息")
    @PutMapping("/update")
    @RepeatSubmit
    @RequiresPermissions("system:router:edit")
    public AjaxResult<Boolean> updateRouter(@RequestBody @Validated UpdateRouterForm form) {
        return AjaxResult.success(routerService.updateRouter(form));
    }


    @WebLog("删除路由信息")
    @DeleteMapping("/delete")
    @RepeatSubmit
    @RequiresPermissions("system:router:del")
    public AjaxResult<Boolean> deleteRouter(@RequestBody @Validated IdForm form) {
        return AjaxResult.success(routerService.deleteRouter(form));
    }

    /**
     * 更新路由状态
     */
    @WebLog("更新路由状态")
    @PostMapping("/state")
    @RequiresPermissions("system:router:status")
    public AjaxResult updateStatus(@RequestBody @Validated IdForm form) {
        return AjaxResult.success(routerService.updateStatus(form));
    }

    /**
     * 获取路由详细信息
     */
    @WebLog("获取路由详细信息")
    @PostMapping("/getRouterInfo")
    public AjaxResult getRouterInfoById(@RequestBody @Validated IdForm form) {
        return AjaxResult.success(routerService.getRouterInfoById(form));
    }

}
