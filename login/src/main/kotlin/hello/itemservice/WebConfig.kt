package hello.itemservice

import hello.itemservice.web.filter.LogFilter
import hello.itemservice.web.filter.LoginCheckFilter
import jakarta.servlet.Filter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebConfig {

    @Bean
    fun logFilter(): FilterRegistrationBean<Filter> = FilterRegistrationBean<Filter>().apply {
        filter = LogFilter()
        order = 1
        addUrlPatterns("/*")
    }

    @Bean
    fun loginCheckFilter(): FilterRegistrationBean<Filter> = FilterRegistrationBean<Filter>().apply {
        filter = LoginCheckFilter()
        order = 2
        addUrlPatterns("/*")
    }

}
