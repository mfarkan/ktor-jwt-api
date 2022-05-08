package com.account.exception

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

class UserNotFoundException : RuntimeException()

class TokenCreationException : RuntimeException()