package io.github.kamarias.web.service;



import com.baomidou.mybatisplus.extension.service.IService;
import io.github.kamarias.entity.UserEntity;
import io.github.kamarias.form.add.AddUserForm;
import io.github.kamarias.form.update.UpdateUserForm;
import io.github.kamarias.query.base.IdForm;
import io.github.kamarias.query.system.QueryUserForm;
import io.github.kamarias.uuid.LoginObject;
import io.github.kamarias.vo.PageVO;
import io.github.kamarias.vo.RoleOptionsVO;

import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/29 10:08
 */
public interface UserService extends IService<UserEntity> {

    /**
     * 添加用户信息
     * @param form 用户信息表单
     * @return 添加结果
     */
    boolean addUser(AddUserForm form);


    /**
     * 修改用户信息
     * @param form 用户信息表单
     * @return 修改结果
     */
    boolean updateUser(UpdateUserForm form);


    /**
     * 通过用户id 更新用户状态
     * @param form id表单
     * @return 更新结果
     */
    boolean updateStatus(IdForm form);

    /**
     * 通过用户Id 删除用户信息
     * @param form  id表单
     * @return 删除结果
     */
    boolean deleteUser(IdForm form);

    /**
     * 通过用户Id 获取用户详细信息
     * @param form id表单
     * @return 返回结果
     */
    UpdateUserForm getUserInfoById(IdForm form);

    /**
     * 校验电话号码是否存在
     * @param phone 电话号码
     * @return 存在返回 true
     */
    boolean phoneExists(String phone);

    /**
     * 通过电话号码获取用户信息
     * @param phone 电话号码
     * @return 返回查找到的用户
     */
    UserEntity selectUserByPhone(String phone);

    /**
     * 通过邮箱号获取用户信息
     * @param email 邮箱
     * @return 返回查找到的用户
     */
    UserEntity selectUserByEmail(String email);


    /**
     * 通过用户名获取用户信息
     * @param userName 用户名
     * @return 返回查找到的用户
     */
    UserEntity selectUserByAccount(String userName);

    /**
     * 校验用户邮箱是否存在
     * @param email 邮箱账号
     * @return 存在返回 true
     */
    boolean emailExists(String email);

    /**
     * 校验用户账号是否存在
     * @param userName 用户名称
     * @return 存在返回 true
     */
    boolean accountExists(String userName);


    /**
     * 生成BCryptPasswordEncoder密码
     * @param passWord 密码
     * @return 加密字符串
     */
    String encryptPassword(String passWord);

    /**
     * 校验密码（密码正确返回-true，错误返回-false）
     * @param loginPassWord 登录输入密码
     * @param databasePassWord 数据库密码
     * @return 校验结果
     */
    boolean matchesPassword(String loginPassWord, String databasePassWord);


    /**
     * 重置用户登录密码
     * @param userId 用户Id
     * @param passWord 密码
     * @return 重置结果
     */
    boolean resetPassword(String userId, String passWord);


    /**
     * 获取登录的用户信息(已登录时获取)
     * @return 登录的用户信息
     * @param <T>  继承UuidObject的登录用户信息
     */
    <T extends LoginObject> T getLoginUser();


    /**
     * 查询列表集合
     * @param form 查询表单
     * @return 返回结果
     */
    PageVO<UserEntity> getList(QueryUserForm form);

    /**
     * 获取登录用户角色列表
     * @return 返回结果
     */
    List<RoleOptionsVO> getLoginUserRole();
}
