package io.github.kamarias.utils;

import com.querydsl.core.QueryResults;
import io.github.kamarias.vo.PageVO;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/19 11:03
 */
public class PageUtil extends io.github.kamarias.utils.page.PageUtil {

    /**
     * 处理翻页结果
     *
     * @param queryResults queryDsl 查询结果
     * @param <T>          所有泛型
     * @return 返回计算的翻页结果
     */
    public static <T> PageVO<T> handlerResult(QueryResults<T> queryResults) {
        return new PageVO<>(queryResults.getTotal(), calcPage(queryResults.getTotal(), queryResults.getLimit()), queryResults.getResults());
    }

}
