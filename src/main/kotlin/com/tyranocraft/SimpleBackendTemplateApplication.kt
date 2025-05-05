package com.tyranocraft

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleBackendTemplateApplication

fun main(args: Array<String>) {
    runApplication<SimpleBackendTemplateApplication>(*args)
}
