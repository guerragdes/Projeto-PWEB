package com.example.projetopweb.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UsuarioDetailsConfig usuarioDetailsConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                customizer -> customizer
                        // ===== ACESSO AO PÚBLICO =====
                        .requestMatchers("/login").permitAll() // Pagina de login
                        .requestMatchers("/cadastro").permitAll() // Pagina de cadastro de novos usuários
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**").permitAll() // Recursos estáticos
                        .requestMatchers("/produtos/loja").permitAll() // Pagina da loja

                        // ===== ACESSO RESTRITO (APENAS ADMINS) =====
                        // Paginas de cadastro
                        .requestMatchers("/admin/cadastrar").hasAnyRole("ADMIN")
                        .requestMatchers("/produtos/novo").hasAnyRole("ADMIN")
                        // Endpoints de cadastro
                        .requestMatchers(HttpMethod.POST, "/produtos").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/clientes").hasAnyRole("ADMIN")

                        // ===== ACESSO RESTRITO A AUTENTICADOS =====
                        .requestMatchers(HttpMethod.GET, "/produtos").authenticated()
                        .requestMatchers(HttpMethod.GET, "/vendas/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/carrinho").authenticated()
                        .requestMatchers("/enderecos/**").authenticated()

                        // Autenticação para qualquer outra requisição
                        .anyRequest()
                        .authenticated())
                .formLogin(customizer -> customizer
                        .loginPage("/login") // Pagina de login personalizada
                        .defaultSuccessUrl("/produtos/loja", true) // Redireciona para a loja após login
                        .permitAll() // Permite acesso a pagina de login para todos
                )
                .httpBasic(withDefaults()) // Autenticação basica
                .logout(LogoutConfigurer::permitAll) // Permite logout para todos
                .rememberMe(withDefaults()); // Mantem usuario logado apos fechar navegador

        return http.build();
    }

    @Autowired
    public void configureUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioDetailsConfig).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
