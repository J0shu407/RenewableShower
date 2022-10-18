package de.joshi.renewableshower

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
open class RenewableShowerApplication

fun main(args: Array<String>) {
    SpringApplication.run(RenewableShowerApplication::class.java, *args)
}