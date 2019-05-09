package com.liyu.cms.data.web.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by liyu on 2018/11/4.
 */
public class ChainFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("begin filter");

        filterChain.doFilter(servletRequest,servletResponse);

        System.out.println("end filter");
    }

    @Override
    public void destroy() {

    }
}
