package org.github.swszz.facade

import org.github.swszz.authentication.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/dashboard")
class FacadeController(
    private val facadeService: FacadeService
) {
    @GetMapping
    @Authentication
    fun getDashboard(): DashboardResponse {
        return facadeService.getDashboard()
    }
}
