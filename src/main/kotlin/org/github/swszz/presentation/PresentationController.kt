package org.github.swszz.presentation

import org.github.swszz.authentication.Authentication
import org.github.swszz.event.inventory.InventoryAccessEvent
import org.github.swszz.inventory.HelloResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/dashboard")
class PresentationController(
    private val presentationService: PresentationService
) {
    @GetMapping
    @Authentication
    fun getDashboard(): DashboardResponse {
        return presentationService.getDashboard()
    }
}
