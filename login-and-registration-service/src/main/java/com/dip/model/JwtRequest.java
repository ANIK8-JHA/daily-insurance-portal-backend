package com.dip.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtRequest {
	
	@NotBlank(message = "Username cannot be blank")
	private String username;
	@NotBlank(message = "Password cannot be blank")
	private String password;
	
	
	
}
