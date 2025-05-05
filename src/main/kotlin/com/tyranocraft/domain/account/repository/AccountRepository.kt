package com.tyranocraft.domain.account.repository

import com.tyranocraft.domain.account.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long> {
    fun findByEmail(email: String): Account?

    fun existsByEmail(email: String): Boolean
}