package hello.itemservice

import hello.itemservice.web.filter.LogFilter
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

}
