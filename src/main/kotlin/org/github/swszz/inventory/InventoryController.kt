package org.github.swszz.inventory

import org.github.swszz.authentication.Authentication
import org.github.swszz.event.inventory.InventoryAccessEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/inventory")
class InventoryController(
    private val applicationEventPublisher: ApplicationEventPublisher,
) {

    @GetMapping
    @Authentication
    fun hello(): HelloResponse {
        val hello = "hello"
        applicationEventPublisher.publishEvent(InventoryAccessEvent(hello))
        return HelloResponse(hello)
    }
}

data class HelloResponse(val message: String)