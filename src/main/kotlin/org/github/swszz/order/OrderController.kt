package org.github.swszz.order

import org.github.swszz.authentication.Authentication
import org.github.swszz.core.Logger
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrderController(
    private val orderService: OrderService
) {
    @PostMapping
    fun createOrder(@RequestBody request: CreateOrderRequest): Order {
        return orderService.createOrder(request.productName, request.quantity)
    }

    @GetMapping
    fun getAllOrders(): List<Order> {
        Logger.info { "get all orders" }
        return orderService.findAll()
    }
}

data class CreateOrderRequest(
    val productName: String,
    val quantity: Int
)
