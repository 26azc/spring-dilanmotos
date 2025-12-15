package com.grupouno.spring.dilanmotos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad para la aplicación.
 * 
 * <p>Define las reglas de autorización, login, logout y el mecanismo de
 * encriptación de contraseñas usando Spring Security.</p>
 * 
 * <p>Características principales:</p>
 * <ul>
 *   <li>Permite acceso público a rutas de autenticación y recursos estáticos
 *       (login, registro, recuperación de contraseña, CSS y JS).</li>
 *   <li>Restringe el acceso a rutas bajo <b>/admin/**</b> únicamente a usuarios con rol ADMIN.</li>
 *   <li>Requiere autenticación para cualquier otra ruta.</li>
 *   <li>Configura un formulario de login personalizado en <b>/login</b> y redirige
 *       al dashboard tras autenticación exitosa.</li>
 *   <li>Configura el logout en <b>/logout</b> y redirige al login con parámetro <b>?logout</b>.</li>
 *   <li>Define un {@link PasswordEncoder} basado en {@link BCryptPasswordEncoder}
 *       para almacenar contraseñas de forma segura.</li>
 * </ul>
 * 
 * @author Neyder Estiben Manrique Alvarez
 * @version 1.0
 */
@Configuration
public class SecurityConfig {

    /**
     * Configura el filtro principal de seguridad de la aplicación.
     *
     * @param http objeto {@link HttpSecurity} para definir reglas de seguridad
     * @return instancia de {@link SecurityFilterChain} con las configuraciones aplicadas
     * @throws Exception en caso de error en la configuración
     */
   @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/login",
                    "/register",
                    "/forgot-password",
                    "/verify-code",
                    "/reset-password",
                    "/css/**",
                    "/js/**"
                ).permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // 🔹 Nuevo: proteger la ruta de perfil/cuenta
                .requestMatchers("/CuentaUsuario").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }


    /**
     * Define el codificador de contraseñas para la aplicación.
     * 
     * <p>Utiliza {@link BCryptPasswordEncoder}, un algoritmo seguro de hashing
     * que incluye salt y múltiples rondas de encriptación.</p>
     *
     * @return instancia de {@link PasswordEncoder} basada en BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
