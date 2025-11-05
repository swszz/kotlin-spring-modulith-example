package org.github.swszz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
internal class SpringBootModulithTemplateApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<SpringBootModulithTemplateApplication>(*args)
        }
    }
}