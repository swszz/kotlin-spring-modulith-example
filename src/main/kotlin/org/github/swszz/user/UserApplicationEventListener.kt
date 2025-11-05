package org.github.swszz.user

import org.github.swszz.core.Logger
import org.github.swszz.event.inventory.InventoryAccessEvent
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Component

@Component
class UserApplicationEventListener {

    @ApplicationModuleListener
    fun listen(event: InventoryAccessEvent) {
        Logger.info { event.toString() }
    }
}