package org.github.swszz.presentation

import org.github.swszz.inventory.InventoryService
import org.github.swszz.order.OrderService
import org.github.swszz.user.UserService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PresentationService(
    private val orderService: OrderService,
    private val inventoryService: InventoryService,
    private val userService: UserService,
) {
    fun getDashboard(): DashboardResponse {
        val orders = orderService.findAll()
        val inventories = inventoryService.findAll()
        val name = userService.getUser()

        return DashboardResponse(
            totalOrders = orders.size,
            totalInventoryItems = inventories.size,
            orders = orders.map { it.toSummary() },
            inventories = inventories.map { it.toSummary() },
            isAuthenticated = true, // Authentication aspect를 통해 검증됨을 가정
            userName = name
        )
    }
}
