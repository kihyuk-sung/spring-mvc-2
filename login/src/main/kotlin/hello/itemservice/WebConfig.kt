package hello.itemservice

import hello.itemservice.web.argumentresolver.LoginMemberArgumentResolver
import hello.itemservice.web.filter.LogFilter
import hello.itemservice.web.filter.LoginCheckFilter
import hello.itemservice.web.interceptor.LogInterceptor
import hello.itemservice.web.interceptor.LoginCheckInterceptor
import jakarta.servlet.Filter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig: WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(LoginMemberArgumentResolver())
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LogInterceptor())
            .order(1)
            .addPathPatterns("/**")
            .excludePathPatterns("/css/**", "/*.ico", "/error")

        registry.addInterceptor(LoginCheckInterceptor())
            .order(2)
            .addPathPatterns("/**")
            .excludePathPatterns("/", "/members/add", "/login", "/logout", "/css/**", "/*.ico", "/error")
    }

//    @Bean
    fun logFilter(): FilterRegistrationBean<Filter> = FilterRegistrationBean<Filter>().apply {
        filter = LogFilter()
        order = 1
        addUrlPatterns("/*")
    }

//    @Bean
    fun loginCheckFilter(): FilterRegistrationBean<Filter> = FilterRegistrationBean<Filter>().apply {
        filter = LoginCheckFilter()
        order = 2
        addUrlPatterns("/*")
    }

}
