package task_tracker.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import task_tracker.demo.config.JwtService;
import task_tracker.demo.dto.AuthResponseDTO;
import task_tracker.demo.dto.LoginRequestDTO;
import task_tracker.demo.dto.RegisterRequestDTO;
import task_tracker.demo.model.User;
import task_tracker.demo.repository.UserRepository;

@Service
public class AuthService {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager  authenticationManager;

    public AuthResponseDTO register(RegisterRequestDTO reguest) {
        if(userRepository.existsByEmail(reguest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = new User();



        user.setName(reguest.getName());
        user.setEmail(reguest.getEmail());

        //Se encripta la contraseña
        user.setPassword(passwordEncoder.encode(reguest.getPassword()));
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        return new AuthResponseDTO(jwtToken);
    }


    public AuthResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())

        );
        User user= userRepository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponseDTO(jwtToken);
    }

}
