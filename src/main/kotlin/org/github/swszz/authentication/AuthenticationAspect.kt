package org.github.swszz.authentication

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Aspect
@Component
class AuthenticationAspect {

    @Before("@annotation(org.github.swszz.authentication.Authentication)")
    fun before(joinPoint: JoinPoint) {
        println("*** Authentication ***")
    }
}