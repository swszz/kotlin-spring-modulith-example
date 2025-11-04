package org.github.swszz.facade

import org.springframework.modulith.ApplicationModule

@ApplicationModule(allowedDependencies = ["inventory", "order", "authentication", "core"])
class FacadeModule
