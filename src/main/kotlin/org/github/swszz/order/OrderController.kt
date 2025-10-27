package org.github.swszz.order

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
        return orderService.findAll()
    }
}

data class CreateOrderRequest(
    val productName: String,
    val quantity: Int
)
