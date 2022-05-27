package com.register.login.registration;

import java.util.function.Predicate;

public class EmailValidator implements Predicate<String>{

	@Override
	public boolean test(String email) {	
		// TODO: Regex to validate email
		return true;
	}

}
