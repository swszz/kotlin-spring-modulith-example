package org.github.swszz.inventory

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("INVENTORY")
data class Inventory(
    @Id
    val id: Long? = null,
    val productName: String,
    val stockQuantity: Int,
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
