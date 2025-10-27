package org.github.swszz.order

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("ORDERS")
data class Order(
    @Id
    val id: Long? = null,
    val productName: String,
    val quantity: Int,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
