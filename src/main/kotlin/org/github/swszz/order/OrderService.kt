package org.github.swszz.order

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {
    @Transactional("orderTransactionManager")
    fun createOrder(productName: String, quantity: Int): Order {
        val order = Order(productName = productName, quantity = quantity)
        return orderRepository.save(order)
    }

    fun findAll(): List<Order> = orderRepository.findAll().toList()
}
