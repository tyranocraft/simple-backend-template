package com.tyranocraft.application.service.account

import com.tyranocraft.domain.account.Account
import com.tyranocraft.domain.account.repository.AccountRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AccountService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun createForTest(email: String, password: String): Long {
        val account = Account.createForBasicUser(
            email = email,
            username = email,
            password = passwordEncoder.encode(password),
        )

        return accountRepository.save(account).id!!

    }

    fun findAccountByEmail(email: String): Account {
        return accountRepository.findByEmail(email)
            ?: throw IllegalArgumentException("Account not found with email: $email")
    }
}