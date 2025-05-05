package com.tyranocraft.application.service.auth

import com.tyranocraft.domain.account.repository.AccountRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthUserDetailsService(
    private val accountRepository: AccountRepository,
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val account = accountRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found with email: $email")

        return User(account.email, account.password, listOf(SimpleGrantedAuthority(account.role.name)))
    }
}