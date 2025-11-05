package org.github.swszz.authentication

import org.springframework.modulith.ApplicationModule

@ApplicationModule(
    allowedDependencies = ["core"]
)
class AuthenticationModule