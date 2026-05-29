package task_tracker.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {


    @Autowired
    private JwtService jwtService;

    @Autowired
    @Lazy
    private UserDetailsService userDetailsService;

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.contains("/v3/api-docs") ||
                path.contains("/swagger-ui") ||
                path.contains("/error");
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
            ) throws ServletException, IOException{

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        log.info("Procesando peticion HTTP: [{}] {}", request.getMethod(), request.getRequestURI());

        // Si no viene la cabecera Authorization o no empieza por "Bearer ", dejamos pasar la petición
        // (ya decidirá Spring Security más adelante si la ruta requería autenticación o no)

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            log.info("Peticion sin token JWT Bearer para la ruta: {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7); // Extraemos el token quitando "Bearer "


        try{
            userEmail = jwtService.extarctUsername(jwt); //sacamos el email del token

            // si hay email y el usuario no esta autenticado con el contexto spring
            if(userEmail != null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);


                //si el token es totalmente valido ,lo metemos en el contexto de seguridad de spring
                if(jwtService.isTokenValid(jwt,userDetails)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.info("Usuario [{}] autenticado con exito en Spring Security", userEmail );
        }
                else {
                    log.warn("El token JWT provisto para el usuario [{}] no es valido o expiro", userEmail);
                }



            }
        }catch (Exception e){
            log.error("Fallo critico al interceptar y parsear el token JWT: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);

    }
}
