package task_tracker.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task_tracker.demo.dto.LoginRequestDTO;
import task_tracker.demo.dto.RegisterRequestDTO;
import task_tracker.demo.service.AuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    //Definamos el logger oficial de la clase
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);




    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register( @RequestBody RegisterRequestDTO request) {
        //log nivel Info para registrar eventos del sistema
        log.info("Peticion de registro recibida para el email: {}",request.getEmail());

        try{
            var response = authService.register(request);
            log.info("Usuario registrado con exito: {}", request.getEmail());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            //log de nivel Error capturando la excepcion real
            log.error("Error en el proceso de registro para [{}]: {}", request.getEmail(), e.getMessage());
            throw e;
        }
    }


    @PostMapping("/login")
    public  ResponseEntity<?> login( @RequestBody LoginRequestDTO request) {

        log.info("Intento de inicio de sesion para el email: {}",request.getEmail());

        try {
            var response = authService.login(request);
            log.info("Login exitoso para el usuario: {}", request.getEmail());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            //Log de nivel WARN para advertir de comportamientos esperados pero fallidos (como malas credenciales)
            log.warn("Fallo de autenticacion para [{}]: {}", request.getEmail(), e.getMessage());
            throw e;
        }
    }

}
