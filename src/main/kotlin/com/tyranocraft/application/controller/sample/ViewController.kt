package com.tyranocraft.application.controller.sample

import com.tyranocraft.application.service.account.AccountService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.security.Principal

@Controller /** resources/templates 에 있는 xxx.html 파일일 그릴려면 @RestController 대신 @Controller 사용 */
class ViewController(
    private val accountService: AccountService,
) {

    @GetMapping("/")
    fun index(model: Model, principal: Principal?): String {
        // 로그인 안한 사람
        if (principal == null) {
            model.addAttribute("message", "index : Hello, Spring Security Guest!")
        } else {
            model.addAttribute("message", "index : " + principal.name)
        }

        return "index"
    }

    @GetMapping("/info")
    fun info(model: Model): String {
        // info: 로그인 여부에 상관없이 아무나 접근가능
        model.addAttribute("message", "info : 아무나 접근 가능")

        return "info"
    }

    @GetMapping("/dashboard")
    fun dashboard(model: Model, principal: Principal): String {
        // dashboard: 로그인 성공한 사람만 접근가능(principal 필요)
        model.addAttribute("message", "dashboard : 로그인 성공한 사람 -> " + principal.name)

        return "dashboard"
    }

    @GetMapping("/admin")
    fun admin(model: Model, principal: Principal): String {
        // admin: 로그인 성공 & 관리자만 접근가능(principal 필요)
        val account = accountService.findAccountByEmail(principal.name)
        model.addAttribute("message", "admin : 로그인 성공 & 관리자 -> " + principal.name)
        model.addAttribute("account", account)

        return "admin"
    }

    @GetMapping("/login")
    fun loginPage(): String {
        return "login" // templates/login.html
    }

    @GetMapping("/access-denied")
    fun accessDenied(model: Model): String {
        model.addAttribute("message", "접근 권한이 없습니다.")
        return "access-denied" // templates/access-denied.html
    }
}