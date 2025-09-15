package com.example.demo.form;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class AdminForm implements Serializable {

	private Long id;
	
	@NotBlank
	private String lastName;
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@Size(min = 4, max = 8)
	private String password;
}
