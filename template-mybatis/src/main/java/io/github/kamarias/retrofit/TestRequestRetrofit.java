package io.github.kamarias.retrofit;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import java.util.Map;

/**
 * 模板 Retrofit 远程调用
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/29 10:39
 */
@RetrofitClient(baseUrl = "http://localhost:9080")
public interface TestRequestRetrofit {


    @GET("/web/login")
    String getToken(@QueryMap Map<String, Object> map);


}
