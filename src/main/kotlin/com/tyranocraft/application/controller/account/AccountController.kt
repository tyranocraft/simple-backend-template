package com.tyranocraft.application.controller.account

import com.tyranocraft.application.service.account.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/accounts")
class AccountController(
    private val accountService: AccountService,
) {

    @GetMapping
    fun createByGetMethodForTest(
        @RequestParam(value = "email", defaultValue = "") email: String,
        @RequestParam(value = "password", defaultValue = "") password: String,
    ): ResponseEntity<Long> {
        val accountId = accountService.createForTest(email, password)

        return ResponseEntity.created(URI.create("/api/accounts/$accountId")).body(accountId)
    }
}