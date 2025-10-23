package org.jordicunillerarivera.spaceship.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CreateSpaceshipDTO(@NotBlank @Size(max = 255) String name,
                                 @Size(max = 255) String model,
                                 @Size(max = 255) String manufacturer,
                                 @PositiveOrZero Integer crewCapacity) {}
