package io.github.kamarias.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.kamarias.vo.PageVO;

/**
 * 分页结果处理工具类 增强支持mybatis-plus的分页查询结果
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/4 15:53
 */
public class PageUtil extends io.github.kamarias.utils.page.PageUtil {

    /**
     * 处理翻页结果
     *
     * @param page 翻页数据
     * @param <T>  所有泛型
     * @return 返回计算的翻页结果
     */
    public static <T> PageVO<T> handlerResult(IPage<T> page) {
        return new PageVO<>(page.getTotal(), page.getPages(), page.getRecords());
    }

}
