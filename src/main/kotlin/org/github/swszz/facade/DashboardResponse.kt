package org.github.swszz.facade

import org.github.swszz.inventory.Inventory
import org.github.swszz.order.Order

data class DashboardResponse(
    val totalOrders: Int,
    val totalInventoryItems: Int,
    val orders: List<OrderSummary>,
    val inventories: List<InventorySummary>,
    val isAuthenticated: Boolean
)

data class OrderSummary(
    val id: Long?,
    val productName: String,
    val quantity: Int
)

data class InventorySummary(
    val id: Long?,
    val productName: String,
    val stockQuantity: Int
)

fun Order.toSummary() = OrderSummary(
    id = this.id,
    productName = this.productName,
    quantity = this.quantity
)

fun Inventory.toSummary() = InventorySummary(
    id = this.id,
    productName = this.productName,
    stockQuantity = this.stockQuantity
)
