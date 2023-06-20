package io.github.kamarias.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.github.kamarias.utils.SecurityContextUtils;
import io.github.kamarias.vo.LoginUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * mybatis-plus 自动插入配置
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/20 20:57
 */
@Component
public class MybatisPlusAutoMetaObjectConfig implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LoginUser loginUser = SecurityContextUtils.getLoginUser();
        String userName = "SYSTEM_USER";
        if (Objects.nonNull(loginUser)) {
            userName = loginUser.getName();
        }
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "createBy", String.class, userName);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updateBy", String.class, userName);
        this.strictInsertFill(metaObject, "del_flag", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LoginUser loginUser = SecurityContextUtils.getLoginUser();
        String userName = "SYSTEM_USER";
        if (Objects.nonNull(loginUser)) {
            userName = loginUser.getName();
        }
        LocalDateTime now = LocalDateTime.now();
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, now);
        this.strictUpdateFill(metaObject, "updateBy", String.class, userName);
    }

    /**
     * 解决自动填充，字段不更新的情况
     *
     * @param metaObject 填充对象
     * @param fieldName  字段名
     * @param fieldVal   字段值
     * @return 返回填充结果
     */
    @Override
    public MetaObjectHandler strictFillStrategy(MetaObject metaObject, String fieldName, Supplier<?> fieldVal) {
        Object obj = fieldVal.get();
        if (Objects.nonNull(obj)) {
            metaObject.setValue(fieldName, obj);
        }
        return this;
    }

}
