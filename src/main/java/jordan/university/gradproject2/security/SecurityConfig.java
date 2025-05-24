package jordan.university.gradproject2.security;

import jordan.university.gradproject2.security.jwt.JwtAuthenticationFilter;
import jordan.university.gradproject2.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login").permitAll() // Allow login without auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Pre-flight requests
                        .requestMatchers(HttpMethod.POST, "/api/activity-forms").authenticated() // ✅ Allow POST with token
                        .requestMatchers("/api/**").authenticated() // Protect other API routes
                        .anyRequest().denyAll() // Deny everything else (optional: restrict web access)
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*")); // Change to specific origin in production
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors
//                        .configurationSource(request -> {
//                            CorsConfiguration config = new CorsConfiguration();
//                            config.addAllowedOriginPattern("*"); // ✅ Allow all origins
//                            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//                            config.setAllowedHeaders(List.of("*"));
//                            config.setAllowCredentials(true); // only keep if you're using credentials (e.g., cookies)
//                            return config;
//                        })
//                )
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().authenticated()
//                )
//                .addFilterBefore(
//                        new JwtAuthenticationFilter(jwtTokenProvider),
//                        UsernamePasswordAuthenticationFilter.class
//                )
//                .exceptionHandling(exception -> exception
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                );
//
//        return http.build();
//    }


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors
//                        .configurationSource(request -> {
//                            CorsConfiguration config = new CorsConfiguration();
//                            config.setAllowedOrigins(List.of("http://localhost:3001"));
//                            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//                            config.setAllowedHeaders(List.of("*"));
//                            config.setAllowCredentials(true);
//                            return config;
//                        })
//                )
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .authorizeHttpRequests(auth -> auth
//                        // Public endpoints
//                        .requestMatchers("/api/auth/**", "/error").permitAll()
//                        .requestMatchers("/api/colleges", "/api/locations", "/api/activity-forms", "/api/auth/login").permitAll()
//
//                        // Everything else requires any authenticated user
//                        .anyRequest().authenticated()
//                )
//                .addFilterBefore(
//                        new JwtAuthenticationFilter(jwtTokenProvider),
//                        UsernamePasswordAuthenticationFilter.class
//                )
//                .exceptionHandling(exception -> exception
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                );
//
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/auth/**").permitAll()
//                        .requestMatchers("/error").permitAll()
//
//                        // Public endpoints
//                        .requestMatchers(HttpMethod.GET, "/api/colleges").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/locations").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/activity-types").permitAll()
//
//                        // User management - admin only
//                        .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
//
//                        // Activity management
//                        .requestMatchers(HttpMethod.GET, "/api/activities").authenticated()
//                        .requestMatchers(HttpMethod.GET, "/api/activities/**").authenticated()
//                        .requestMatchers(HttpMethod.POST, "/api/activities").hasAnyRole("STUDENT", "ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/api/activities/**").hasAnyRole("STUDENT", "ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/api/activities/**").hasAnyRole("STUDENT", "ADMIN")
//
//                        // Workflow management
//                        .requestMatchers(HttpMethod.GET, "/api/workflows").hasAnyRole("SUPERVISOR", "ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/api/workflows").hasAnyRole("SUPERVISOR", "ADMIN")
//
//                        // Other management endpoints
//                        .requestMatchers(HttpMethod.POST, "/api/**").hasRole("STUDENT")
//                        .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("STUDENT")
//                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("STUDENT")
//
//                        .anyRequest().authenticated()
//                )
//                .addFilterBefore(
//                        new JwtAuthenticationFilter(jwtTokenProvider),
//                        UsernamePasswordAuthenticationFilter.class
//                )
//                .exceptionHandling(exception -> exception
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                );
//
//        return http.build();
//    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}