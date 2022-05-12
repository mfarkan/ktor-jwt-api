package com.account.service

import com.account.domain.ApplicationUserDomain
import com.account.model.response.ApplicationUserResponse
import com.account.repository.UserRepository

class UserService(private val userRepository: UserRepository) {
    suspend fun findUserByNameAndPassword(name: String, password: String): ApplicationUserDomain =
        userRepository.findUserByUserNameAndPassword(name, password)


    suspend fun findUserByName(userName: String): ApplicationUserResponse = userRepository.findUserByName(userName)
}