package com.tyranocraft.config

import com.tyranocraft.domain.account.AccountRole
import com.tyranocraft.security.handler.AuthLogoutSuccessHandler
import com.tyranocraft.security.handler.LoginAuthenticationFailureHandler
import com.tyranocraft.security.handler.LoginAuthenticationSuccessHandler
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
class SecurityConfig {

    @PostConstruct // 서버 부팅 시 1회만 설정
    fun init() {
        // MODE_INHERITABLETHREADLOCAL -> 비동기 작업 등 진행 시, 자식 쓰레드도 인증정보 복제됨
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL)
    }

    @Bean
    fun filterChain(
        http: HttpSecurity,
        successHandler: LoginAuthenticationSuccessHandler,
        failureHandler: LoginAuthenticationFailureHandler,
        authLogoutSuccessHandler: AuthLogoutSuccessHandler
    ): SecurityFilterChain {
        return http
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/", "/info", "/login", "/access-denied").permitAll()
                    .requestMatchers("/admin/**").hasRole(AccountRole.ROLE_MANAGER.role)
                    .requestMatchers(HttpMethod.GET, "/api/accounts/**").permitAll() // GET + /api/v1/members/** 허용

                    .requestMatchers("/h2-console/**").permitAll() // h2-console url:  http://localhost:8080/h2-console

                    .anyRequest().authenticated()
            }
            .csrf { csrf ->
                csrf.ignoringRequestMatchers("/h2-console/**")

                // CSRF 토큰을 쿠키에 저장하고, JavaScript에서도 읽을 수 있게(HttpOnly=false) 설정(React 를 쓰는 경우 프론트 별도 작업 필요)
                csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            }
            .cors { } // CORS 기능 ON(스프링 시큐리티가 corsConfigurationSource()를 자동으로 찾아서 적용함)
            .headers { headers ->
                headers.frameOptions { frame -> frame.disable() } // iframe 허용 (h2-console)
                headers.contentSecurityPolicy { csp ->
                    // CSP(corsConfigurationSource) 설정(XSS 방어 (스크립트 삽입 막기),  탈취 방어)
                    // csp.policyDirectives("default-src 'self'; img-src 'self' data:; script-src 'self'") // 모든 걸 내 서버 코드에서만 처리(좀더 학습 후 적용)
                }
            }
            .sessionManagement { sessionManagement ->
                sessionManagement.sessionFixation().migrateSession() // 새 세션 생성 (기본값이 migrateSession임)
                sessionManagement.maximumSessions(1).maxSessionsPreventsLogin(false) // 최대 세션 수 1개, (재로그인 시, 기존 로그인 세션 만료)
            }
            .formLogin { form ->
                form
                    .loginPage("/login") // (선택) 커스텀 로그인 페이지 사용
                    .successHandler(successHandler) // 로그인 성공 핸들러
                    .failureHandler(failureHandler) // 로그인 실패 핸들러
            }
            .logout { logout ->
                logout
                    .logoutUrl("/logout") // 로그아웃 요청 URL
                    //.logoutSuccessHandler(authLogoutSuccessHandler) // 로그아웃 성공 시, JSON 메세지 반환하는 handler
                    .logoutSuccessUrl("/") // 로그아웃 성공 후 이동할 URL
                    .invalidateHttpSession(true) // 세션 무효화
                    .deleteCookies("JSESSIONID") // 쿠키 삭제
            }
            .exceptionHandling { exceptionHandling ->
                exceptionHandling
                    .accessDeniedPage("/access-denied") // 인가 실패(403)시, 이동할 페이지
            }
            .build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        // 운영환경에서는 configuration.allowedOrigins = listOf("https://www.tyranocraft.com") 이런식으로 접근 가능한 Origin만 설정
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true // 쿠키허용(필수)

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}