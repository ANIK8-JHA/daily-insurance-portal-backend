package com.dip.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {

	private String token;
	private String username;
	private long userId;
		
}
