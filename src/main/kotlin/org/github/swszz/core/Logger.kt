package org.github.swszz.core

object Logger {
    fun info(supplier: () -> String) {
        println((supplier()))
    }
}