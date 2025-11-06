package org.github.swszz.user

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService {
    fun getUser(): String {
        return UUID.randomUUID().toString()
    }
}