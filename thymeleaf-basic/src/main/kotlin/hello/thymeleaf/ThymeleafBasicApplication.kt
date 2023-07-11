package hello.thymeleaf

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ThymeleafBasicApplication

fun main(args: Array<String>) {
	runApplication<ThymeleafBasicApplication>(*args)
}
