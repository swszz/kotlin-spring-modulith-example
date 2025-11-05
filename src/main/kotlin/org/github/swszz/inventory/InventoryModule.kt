package org.github.swszz.inventory

import org.springframework.modulith.ApplicationModule

@ApplicationModule(allowedDependencies = ["authentication", "core", "event"])
class InventoryModule