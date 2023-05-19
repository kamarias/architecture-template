package io.github.kamarias.utils;

import io.github.kamarias.utils.string.StringUtils;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.Objects;

/**
 * 路径匹配工具类 （基于AntPathMatcher）
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/11 11:56
 */
public class PathMatchUtils {

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * Match boolean.
     *
     * @param patterns the ignore urls
     * @param path     the path
     * @return the boolean
     */
    public static boolean match(final List<String> patterns, final String path) {
        if (Objects.isNull(patterns) || patterns.isEmpty() || StringUtils.isBlank(path)) {
            return false;
        }
        for (String pattern : patterns) {
            boolean match = PATH_MATCHER.match(pattern, path);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
