package api.config;

import api.services.impl.UsuarioServiceImpl;
import api.services.interfaces.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Configuração de segurança (Autorização e Autenticação)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/admin/**").hasRole("ADMIN")  // Somente administradores podem acessar rotas "/admin/"
                .requestMatchers("/user/**").hasRole("USER")    // Somente usuários comuns podem acessar rotas "/user/"
                .anyRequest().authenticated()                   // Qualquer outra rota requer autenticação
                .and()
                .formLogin()                                         // Login via formulário
                .loginPage("/login")                             // Página de login personalizada
                .permitAll()                                     // Permite o acesso à página de login para todos
                .and()
                .logout()                                            // Logout
                .permitAll();                                    // Permite o logout para todos

        return http.build();  // Construir a configuração de segurança
    }

    // Define o UserDetailsService (para carregar os dados do usuário)
    @Bean
    public UserDetailsService userDetailsService() {
        return new UsuarioServiceImpl();  // Usamos o serviço para carregar o usuário
    }

    // Define o PasswordEncoder para criptografar as senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCryptPasswordEncoder para codificar a senha
    }

    // Define o DaoAuthenticationProvider para autenticar o usuário
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}
