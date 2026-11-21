package com.bebrample.diva.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(@NotBlank
                              String username,

                              @NotBlank(message = "must not be empty")
                              @Email
                              String mail,

                              @NotBlank(message = "must not be empty")
                              @Size(min = 7, max = 32,
                                      message = "password too short or too long"
                              )
                              String password) {


}
