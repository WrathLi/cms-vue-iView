package com.liyu.cms.data.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.alibaba.fastjson.JSONObject;
import com.liyu.cms.data.web.util.JWT;
import com.liyu.cms.data.web.util.LoginInfo;
import com.liyu.cms.data.web.util.ResponseData;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * Created by liyu on 2018/3/22.
 */
public class DomainFilter implements Filter {

    private static final String EXCLUDE_PATHS = "ExcludePaths"; //过滤列表

    private String strExcludeFile;
    private String[] arrExcludeFile = null;

    public DomainFilter() {
        setStrExcludeFile("");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //设置过滤url
        String tmpExcludeFile = filterConfig.getInitParameter(EXCLUDE_PATHS);
        if (null != tmpExcludeFile) {
            this.setStrExcludeFile(filterConfig.getInitParameter(EXCLUDE_PATHS));
        }

        if (strExcludeFile != null && strExcludeFile.trim().length() > 0) {
            arrExcludeFile = strExcludeFile.split(";");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token");
        response.setHeader("Access-Control-Expose-Headers", "*");

        HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;

        //excludeFile 跳出filter
        String requestStr = httpRequest.getRequestURL().toString();
        if (this.checkExcludeUrl(requestStr)) {
            filterChain.doFilter(servletRequest, response);
            return;
        }

        String token = httpRequest.getParameter("token");
        ResponseData responseData = ResponseData.ok();
        //token不存在
        if(null != token) {
            LoginInfo login = JWT.unsign(token, LoginInfo.class);
            String loginId = httpRequest.getParameter("loginId");
            //解密token后的loginId与用户传来的loginId不一致，一般都是token过期
            if(null != loginId && null != login) {
                if(Integer.parseInt(loginId) == login.getLoginId()) {
                    filterChain.doFilter(servletRequest, response);
                    return;
                }
                else{
                    responseData = ResponseData.forbidden();
                    responseMessage(response, response.getWriter(), responseData);
                }
            }
            else
            {
                responseData = ResponseData.forbidden();
                responseMessage(response, response.getWriter(), responseData);
            }
        }
        else
        {
            responseData = ResponseData.forbidden();
            responseMessage(response, response.getWriter(), responseData);
        }
    }

    //请求不通过，返回错误信息给客户端
    private void responseMessage(HttpServletResponse response, PrintWriter out, ResponseData responseData) {
        responseData = ResponseData.forbidden();
        response.setContentType("application/json; charset=utf-8");
        String json = JSONObject.toJSONString(responseData);
        out.print(json);
        out.flush();
        out.close();
    }

    private boolean checkExcludeUrl(final String requestStr) {
        boolean ret = false;

        PathMatcher matcher = new AntPathMatcher();
        if(arrExcludeFile != null){
            for(String excludePath : arrExcludeFile){
                ret = matcher.match(excludePath, requestStr);
                if(!ret){
                    ret = requestStr.indexOf(excludePath) > 0;
                }
                if (ret) {
                    break;
                }
            }
        }
        return ret;
    }

    @Override
    public void destroy() {

    }

    public void setStrExcludeFile(String strExcludeFile) {
        this.strExcludeFile = strExcludeFile;
    }
}
