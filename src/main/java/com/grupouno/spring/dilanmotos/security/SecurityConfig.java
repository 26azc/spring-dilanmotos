package com.grupouno.spring.dilanmotos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

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
 *   <li>Registra el {@link AuthenticationManager} usando {@link AuthenticationConfiguration}
 *       para validar credenciales contra la base de datos.</li>
 * </ul>
 *
 * @author Neyder Estiben Manrique Alvarez
 * @version 1.0
 */

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            // 👉 1. HABILITAMOS CORS (Para que React pueda entrar)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 👉 2. DESHABILITAMOS CSRF (Esencial para que React pueda hacer peticiones POST/PUT/DELETE)
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                // 👉 3. PERMITIMOS ACCESO A LA API (Temporalmente, para conectar React)
                .requestMatchers("/api/**").permitAll() 
                
                // 👇 TUS REGLAS ORIGINALES SE MANTIENEN INTACTAS
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
                .requestMatchers("/CuentaUsuario").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")       
                .usernameParameter("correo")        
                .passwordParameter("contrasena")    
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 👉 4. BEAN DE CONFIGURACIÓN DE CORS
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite el puerto donde corre Vite (React)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); 
        // Permite todos los métodos HTTP
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true); // Importante si luego usamos tokens o cookies
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}