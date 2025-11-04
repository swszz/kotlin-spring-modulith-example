package org.github.swszz.inventory

import org.github.swszz.core.Logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InventoryService(
    private val inventoryRepository: InventoryRepository
) {
    @Transactional
    fun addInventory(productName: String, stockQuantity: Int): Inventory {
        Logger.info { "Adding $productName $stockQuantity" }
        val inventory = Inventory(productName = productName, stockQuantity = stockQuantity)
        return inventoryRepository.save(inventory)
    }

    fun findAll(): List<Inventory> = inventoryRepository.findAll().toList()
}
