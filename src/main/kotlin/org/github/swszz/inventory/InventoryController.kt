package org.github.swszz.inventory

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
    fun getAllInventory(): List<Inventory> {
        return inventoryService.findAll()
    }
}

data class AddInventoryRequest(
    val productName: String,
    val stockQuantity: Int
)
