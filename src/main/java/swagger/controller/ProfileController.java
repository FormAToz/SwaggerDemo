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
import org.springframework.web.bind.annotation.*;
import swagger.entity.User;
import swagger.repository.UserRepository;
import swagger.request.EmailRequest;
import swagger.request.UserRequest;
import swagger.response.ErrorResponse;
import swagger.response.UserIdResponse;
import swagger.security.jwt.JwtUtils;
import swagger.service.EmailService;
import swagger.service.UserService;

import java.util.List;

@Api(tags = "Работа с профилем")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/profiles/")
public class ProfileController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;
    private final UserService userService;

    @Value("${application.user.role}")
    private String role;

    public ProfileController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder, JwtUtils jwtUtils, EmailService emailService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.emailService = emailService;
        this.userService = userService;
    }

    // Создает запись профиля и присваивает ему id
    @ApiOperation(value = "Создать новый профиль")
    @PostMapping("/set")
    public ResponseEntity<?> createProfile(@RequestBody UserRequest userRequest) {

        // Проверяем корректность email
        if (!emailService.validate(userRequest.getEmail())) {
            return new ResponseEntity<>(new ErrorResponse("Email is incorrect"), HttpStatus.BAD_REQUEST);
        }

        // Проверяем зарегистрирован ли email?
        if (userRepository.existsByEmailIgnoreCase(userRequest.getEmail())) {
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

        String token = jwtUtils.generateJwtToken(authentication);
        System.out.printf("Token: %s%n: ", token);

        return new ResponseEntity<>(new UserIdResponse(user.getId()), HttpStatus.OK);
    }

    // Возвращает последний созданный профиль
    @ApiOperation(value = "Последний созданный профиль")
    @GetMapping("/last")
    public ResponseEntity<?> getProfile() {

        return userService.getUser(userRepository.findFirstByOrderByIdDesc());
    }

    // Возвращает все созданные профили
    @ApiOperation(value = "Показать все созданные профили")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = (List<User>) userRepository.findAll();

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    // Возвращает профиль по его ID
    @ApiOperation(value = "Найти профиль по id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {

        return userService.getUser(userRepository.findById(id));
    }

    // Возвращает профиль по email
    @ApiOperation(value = "Найти профиль по email")
    @PostMapping("/get")
    public ResponseEntity<?> getUserByEmail(@RequestBody EmailRequest emailRequest) {

        return userService.getUser(userRepository.findByEmailIgnoreCase(emailRequest.getEmail()));

    }
}
