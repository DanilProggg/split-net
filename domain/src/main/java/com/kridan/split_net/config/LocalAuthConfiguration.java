package com.kridan.split_net.config;

import com.kridan.split_net.infrastructure.security.JpaUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class LocalAuthConfiguration {

    private final JpaUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtDecoder jwtDecoder) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // disable HttpSession
                )
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/gateways/config").hasRole("GATEWAY")
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().denyAll()
                )
                .userDetailsService(userDetailsService)
                .addFilterBefore(new RequestLoggingFilter(), AuthorizationFilter.class)
                .oauth2ResourceServer(oauth -> oauth
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Value("${jwt.secret}")
    private String jwtSecret;


    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey secretKey = new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = grantedAuthoritiesConverter.convert(jwt);

            // Детальное логирование
            System.out.println("🔐 === JWT DEBUG INFO ===");
            System.out.println("📧 Subject: " + jwt.getSubject());
            System.out.println("🏷️ All claims: " + jwt.getClaims());
            System.out.println("👥 Roles claim ('roles'): " + jwt.getClaimAsStringList("roles"));
            System.out.println("🛡️ Extracted authorities:");
            authorities.forEach(auth -> System.out.println("   - " + auth.getAuthority()));
            System.out.println("❓ Has ROLE_GATEWAY: " +
                    authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_GATEWAY")));
            System.out.println("==========================");

            return authorities;
        });

        return jwtAuthenticationConverter;
    }

    public static class RequestLoggingFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                        FilterChain filterChain) throws ServletException, IOException {
            System.out.println("🌐 === REQUEST INFO ===");
            System.out.println("📍 Path: " + request.getRequestURI());
            System.out.println("🔐 Auth Header: " + request.getHeader("Authorization"));
            System.out.println("========================");

            filterChain.doFilter(request, response);

            System.out.println("📡 Response Status: " + response.getStatus());
            System.out.println("========================");
        }
    }


}
