package hello.itemservice.domain

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class IdGeneratorConfig {

    @Bean("itemIdGenerator")
    fun itemIdGenerator(): IdGenerator = IdGenerator()

    @Bean("memberIdGenerator")
    fun memberIdGenerator(): IdGenerator = IdGenerator()
}
