package com.imooc.ad.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Seven
 * @Date 2019/10/16 23:30
 * @Description 请求出去时的过滤器，因为是记录耗时，要最后记录
 */
@Slf4j
@Component
public class AccessLogFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 获取上下文信息和request
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        // 计算耗时
        Long startTime = (Long) context.get("request-startTime");
        String requestURI = request.getRequestURI();
        long durationTime = System.currentTimeMillis() - startTime;

        log.info("URI is: " + requestURI + " , durationTime is: " + durationTime / 100 + " ms.");
        return null;
    }
}
