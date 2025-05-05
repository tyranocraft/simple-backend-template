package com.tyranocraft.domain.account

enum class AccountRole(val role: String) {
    ROLE_USER("USER"),
    ROLE_OPERATION_USER("OPERATION_USER"),
    ROLE_OPERATION_LEADER("OPERATION_LEADER"),
    ROLE_MANAGER("MANAGER");
}