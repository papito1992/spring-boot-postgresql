package com.alpaka.RequestInterceptor;

import com.alpaka.model.customerLogs.CustomerActivity;
import com.alpaka.security.services.UserDetailsServiceImpl;
import com.alpaka.service.CustomerLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class ProductServiceInterceptor implements HandlerInterceptor {

    @Autowired
    private CustomerLogsService customerLogsService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("request.getRequestURL(): {} , USER: {}", request.getRequestURL(), request.getRemoteUser());
        System.out.println(request.getRequestURL());
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) {
        String username = request.getHeader("customer-email");
        UserDetails user = userDetailsService.loadUserByUsername(username);
        CustomerActivity customerActivity = new CustomerActivity();
        customerActivity.setVisitedEndpoint(request.getRequestURL().toString());
        customerActivity.setActionName(request.getMethod());
        customerActivity.setIpAddress(request.getRemoteAddr());
        customerActivity.setResponseStatus(response.getStatus());
        customerLogsService.storeActivityData(user.getUsername(), customerActivity);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) {
        System.out.println("AFTER Handle method is Calling");
    }
}
