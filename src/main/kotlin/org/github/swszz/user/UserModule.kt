package org.github.swszz.user

import org.springframework.modulith.ApplicationModule

@ApplicationModule(
    allowedDependencies = ["event", "core"]
)
class UserModule