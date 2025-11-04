package org.github.swszz.facade

import org.github.swszz.inventory.InventoryService
import org.github.swszz.order.OrderService
import org.springframework.stereotype.Service

@Service
class FacadeService(
    private val orderService: OrderService,
    private val inventoryService: InventoryService
) {
    fun getDashboard(): DashboardResponse {
        val orders = orderService.findAll()
        val inventories = inventoryService.findAll()

        return DashboardResponse(
            totalOrders = orders.size,
            totalInventoryItems = inventories.size,
            orders = orders.map { it.toSummary() },
            inventories = inventories.map { it.toSummary() },
            isAuthenticated = true // Authentication aspect를 통해 검증됨을 가정
        )
    }
}
