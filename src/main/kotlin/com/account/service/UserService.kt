package com.account.service

import com.account.domain.ApplicationUserDomain
import com.account.repository.UserRepository

class UserService(private val userRepository: UserRepository) {
    suspend fun findUserByNameAndPassword(name: String, password: String): ApplicationUserDomain {
        return userRepository.findUserByUserNameAndPassword(name, password)
    }
}