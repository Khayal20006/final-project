package com.example.finalproject.config;
import com.example.finalproject.entity.User;
import com.example.finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.example.finalproject.entity.enums.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration


@RequiredArgsConstructor
public class AdminConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner createAdminUser() {
        return args -> {
            String adminEmail = "dream2006@gmail.com";

            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                User admin = new User();
                admin.setFullName("Admin");
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode("khayal2006"));
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);
                System.out.println(" Admin istifadəçisi  yaradıldı.");
            } else {
                System.out.println("Admin istifadəçisi artıq mövcuddur.");
            }
        };
    }
}
