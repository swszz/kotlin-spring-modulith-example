package org.github.swszz.inventory

import org.github.swszz.authentication.Authentication
import org.github.swszz.core.Logger
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/inventory")
class InventoryController(
    private val inventoryService: InventoryService
) {
    @PostMapping
    fun addInventory(@RequestBody request: AddInventoryRequest): Inventory {
        return inventoryService.addInventory(request.productName, request.stockQuantity)
    }

    @GetMapping
    @Authentication
    fun getAllInventory(): List<Inventory> {
        Logger.info { "get all inventory" }
        return inventoryService.findAll()
    }
}

data class AddInventoryRequest(
    val productName: String,
    val stockQuantity: Int
)
