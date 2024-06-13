package com.example.demo.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController // Marks this class as a Spring MVC controller where every method returns a domain object instead of a view.
@RequestMapping(path = "api/v1/registration") // Maps HTTP requests to /api/v1/registration to methods in this controller.
@AllArgsConstructor // Lombok annotation to generate a constructor with all class fields as parameter.
public class RegistrationController {

    // Dependency on the RegistrationService.
    private RegistrationService registrationService;

    // Handles HTTP POST requests to /api/v1/registration.
    // Expects a request body containing registration details.
    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    // Handles HTTP GET requests to /api/v1/registration/confirm
    // Expects a query parameter named token.
    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {

        return registrationService.confirmToken(token);
    }
}
