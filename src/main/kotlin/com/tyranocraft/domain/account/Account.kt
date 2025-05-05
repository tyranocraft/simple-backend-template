package com.tyranocraft.domain.account

import jakarta.persistence.*

@Entity
@Table(name = "account")
class Account(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(unique = true, nullable = false)
    val username: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    val role: AccountRole
) {
    companion object {

        fun createForBasicUser(email: String, username: String, password: String): Account {
            return Account(
                email = email,
                username = username,
                password = password,
                role = AccountRole.ROLE_USER
            )
        }
    }
}