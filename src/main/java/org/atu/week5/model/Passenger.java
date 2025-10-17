package org.atu.week5.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Passenger {
    @Positive(message = "id must be positive")
    long id;

    @NotBlank(message = "name is required")
    String name;

    @Email(message = "email must be valid")
    @NotBlank(message = "email is required")
    String email;
}
