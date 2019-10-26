package com.example.demo.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserRequest extends LoginUserRequest {
	private String confirmPassword;

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
