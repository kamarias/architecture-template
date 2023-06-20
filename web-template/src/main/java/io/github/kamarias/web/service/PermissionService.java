package io.github.kamarias.web.service;



import com.baomidou.mybatisplus.extension.service.IService;
import io.github.kamarias.entity.PermissionEntity;
import io.github.kamarias.form.add.AddPermissionForm;
import io.github.kamarias.form.update.UpdatePermissionForm;
import io.github.kamarias.query.base.IdForm;
import io.github.kamarias.query.system.QueryPermissionForm;
import io.github.kamarias.vo.PageVO;

import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/31 17:07
 */
public interface PermissionService extends IService<PermissionEntity> {


    /**
     * 查询权限列表
     * @param form 查询权限表单
     * @return 返回查询结果
     */
    PageVO<PermissionEntity> getList(QueryPermissionForm form);


    /**
     * 添加权限信息
     * @param form 权限表单
     * @return 返回结果
     */
    boolean addPermission(AddPermissionForm form);

    /**
     * 更新权限信息
     * @param form 权限表单
     * @return 返回结果
     */
    boolean updatePermission(UpdatePermissionForm form);

    /**
     * 删除权限
     * @param form 权限id表单
     * @return 删除结果
     */
    boolean deletePermission(IdForm form);

    /**
     * 获取所有权限列表
     * @return 返回所有权限信息
     */
    List<PermissionEntity> getListAll();
}
