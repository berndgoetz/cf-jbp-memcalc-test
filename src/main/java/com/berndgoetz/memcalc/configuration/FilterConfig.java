package com.berndgoetz.memcalc.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This is the preferred way of registering servlets, filters, and listeners. The advantage to use the
 * @Bean approach is to see all those components in one place, instead of having it scan/annotation based
 * spread in mulitple place where it's hard to track it down
 */
@Configuration
public class FilterConfig {

    // deactivate error page filter forward
    @Bean
    public ErrorPageFilter errorPageFilter() {
        return new ErrorPageFilter();
    }

    // deactivate error page filter forward
    @Bean
    public FilterRegistrationBean disableSpringBootErrorFilter(ErrorPageFilter filter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.setEnabled(false);
        return filterRegistrationBean;
    }

}
