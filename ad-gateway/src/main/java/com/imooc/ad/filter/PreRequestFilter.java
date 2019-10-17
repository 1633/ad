package com.imooc.ad.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @author Seven
 * @Date 2019/10/16 23:24
 * @Description 请求进来时的过滤器
 */
@Slf4j
@Component
public class PreRequestFilter extends ZuulFilter {

    /**
     * 定义过滤器类型
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 定义过滤器的执行顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 定义该过滤器是否执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 定义具体的业务逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        // 获取上下文，记录开始毫秒数
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set("request-startTime", System.currentTimeMillis());

        return null;
    }
}
