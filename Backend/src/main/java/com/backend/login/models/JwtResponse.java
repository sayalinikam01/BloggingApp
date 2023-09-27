package com.backend.login.models;

import lombok.*;
import lombok.Builder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtResponse {

    public String jwtToken;
    public String username;


}
