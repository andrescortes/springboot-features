package com.debuggeando_ideas.best_travel.api.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
public class ReservationRequest {

    @Size(min = 18, max = 20, message = "The size must be between 18 and 20 characters.")
    @NotBlank(message = "The field is required.")
    private String idClient;
    @Positive(message = "The field must be positive.")
    private Long idHotel;
    @Min(value = 1, message = "The field must be positive.")
    @Max(value = 30, message = "The field must be between 1 and 30.")
    private Integer totalDays;
    //    @Pattern(regexp = "^(.+)@(.+)$", message = "The field must be a number.")
    @Email
    private String email;
}
