package com.jzz.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 字符编码过滤器
 * Character Encoding Filter
 *
 * 统一设置请求和响应的字符编码为 UTF-8
 * Uniformly set request and response character encoding to UTF-8
 *
 * 解决中文乱码问题
 * Solves Chinese garbled text issues
 *
 * @author Jzz
 * @version 1.0
 */
public class EncodingFilter implements Filter {

    /**
     * 字符编码，默认 UTF-8
     * Character encoding, default UTF-8
     */
    private String encoding = "UTF-8";

    /**
     * 初始化过滤器
     * Initialize filter
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 从配置中读取编码参数 (Read encoding parameter from configuration)
        String encodingParam = filterConfig.getInitParameter("encoding");
        if (encodingParam != null && !encodingParam.isEmpty()) {
            this.encoding = encodingParam;
        }
        System.out.println("EncodingFilter 初始化完成，编码设置为: " + this.encoding);
    }

    /**
     * 执行过滤
     * Execute filtering
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 设置请求编码 (Set request encoding)
        request.setCharacterEncoding(encoding);

        // 设置响应编码 (Set response encoding)
        response.setCharacterEncoding(encoding);
        response.setContentType("text/html;charset=" + encoding);

        // 继续执行过滤器链 (Continue filter chain)
        chain.doFilter(request, response);
    }

    /**
     * 销毁过滤器
     * Destroy filter
     */
    @Override
    public void destroy() {
        System.out.println("EncodingFilter 销毁");
    }
}
