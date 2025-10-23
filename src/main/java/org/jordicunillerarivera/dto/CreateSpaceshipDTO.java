package org.jordicunillerarivera.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateSpaceshipDTO(@NotBlank String name,
                                 String model,
                                 String manufacturer,
                                 @NotNull Integer crewCapacity) {}
