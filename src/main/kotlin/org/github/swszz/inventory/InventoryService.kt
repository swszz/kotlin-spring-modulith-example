package org.github.swszz.inventory

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InventoryService(
    private val inventoryRepository: InventoryRepository
) {
    @Transactional("inventoryTransactionManager")
    fun addInventory(productName: String, stockQuantity: Int): Inventory {
        val inventory = Inventory(productName = productName, stockQuantity = stockQuantity)
        return inventoryRepository.save(inventory)
    }

    fun findAll(): List<Inventory> = inventoryRepository.findAll().toList()
}
