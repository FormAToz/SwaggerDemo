package swagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import swagger.entity.User;
import swagger.repository.UserRepository;
import swagger.request.EmailRequest;
import swagger.request.UserRequest;
import swagger.response.ErrorResponse;
import swagger.response.UserIdResponse;
import swagger.security.jwt.JwtUtils;
import swagger.service.EmailService;

import java.util.List;

@Api(description = "Работа с профилем. Работа с публичной информацией пользователя")
@RestController
@RequestMapping("/profiles/")
public class ProfileController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;

    @Value("${application.user.role}")
    private String role;

    public ProfileController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder, JwtUtils jwtUtils, EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.emailService = emailService;
    }

    // Создает запись профиля и присваивает ему id
    @ApiOperation(value = "Создать новый профиль", tags = { "profiles" })
    @PostMapping("/set")
    public ResponseEntity<?> createProfile(@RequestBody UserRequest userRequest) {

        System.out.printf("User request: %s, %s, %s%n", userRequest.getName(), userRequest.getEmail(), userRequest.getAge());

        // Проверяем корректность email
        if (!emailService.validate(userRequest.getEmail())) {
            return new ResponseEntity<>(new ErrorResponse("Email is incorrect"), HttpStatus.BAD_REQUEST);
        }

        // Проверяем зарегистрирован ли email?
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return new ResponseEntity<>(new ErrorResponse("Email is already in use"), HttpStatus.FORBIDDEN);
        }

        // Создаем нового юзера
        User user = new User(userRequest.getName(),
                userRequest.getEmail(),
                userRequest.getAge(),
                System.currentTimeMillis(),
                encoder.encode(userRequest.getEmail()),
                role);
        userRepository.save(user);

        // наделяем правами доступа
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getName(), user.getEmail()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>(new UserIdResponse(user.getId()), HttpStatus.OK);
    }

    // Возвращает последний созданный профиль
    @ApiOperation(value = "Последний созданный профиль", tags = { "profiles" })
    @GetMapping("/last")
    public ResponseEntity<User> getProfile() {
        // TODO возвратить последний профиль
        return new ResponseEntity<>(new User(), HttpStatus.OK);
    }

    // Возвращает все созданные профили
    @ApiOperation(value = "Показать все созданные профили", tags = { "profiles" })
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = (List<User>) userRepository.findAll();

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    // Возвращает профиль по его ID
    @ApiOperation(value = "Найти профиль по id", tags = { "profiles" })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        User user = userRepository.findById(id).get();

        if (user == null) {
            return new ResponseEntity<>(new ErrorResponse("User not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Возвращает профиль по email
    @ApiOperation(value = "Найти профиль по email", tags = { "profiles" })
    @PostMapping("/get")
    public ResponseEntity<?> getUserByEmail(@RequestBody EmailRequest emailRequest) {
        User user = userRepository.findByEmail(emailRequest.getEmail()).get();

        if (user == null) {
            return new ResponseEntity<>(new ErrorResponse("User not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }




//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }
//
//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already in use!"));
//        }
//
//        // Create new user's account
//        User user = new User(signUpRequest.getUsername(),
//                signUpRequest.getEmail(),
//                encoder.encode(signUpRequest.getPassword()));
//
//        Set<String> strRoles = signUpRequest.getRole();
//        Set<Role> roles = new HashSet<>();
//
//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
//                    case "mod":
//                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
//
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }
//
//        user.setRoles(roles);
//        userRepository.save(user);
//
//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//    }
}
