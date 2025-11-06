package org.github.swszz.presentation

import org.springframework.modulith.ApplicationModule

@ApplicationModule(
    allowedDependencies =
        ["inventory", "order", "user", "authentication", "core"]
)
class PresentationModule
