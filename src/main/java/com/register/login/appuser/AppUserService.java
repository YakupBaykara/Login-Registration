package com.register.login.appuser;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.register.login.registration.token.ConfirmationToken;
import com.register.login.registration.token.ConfirmationtokenService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
	
	private final String USER_NOT_FOUND_MSG = "User with email %s not found";
	private final AppUserRepository appUserRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ConfirmationtokenService confirmationtokenService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {		
		return appUserRepository.findByEmail(email).
				orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
	}
	
	public String signUpUser(AppUser appUser) {
		
		boolean userExist = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
		if(userExist)
			throw new IllegalStateException("Email is already taken!");
		
		String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
		appUser.setPassword(encodedPassword);
		appUserRepository.save(appUser);
		
		String token = UUID.randomUUID().toString();
		ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);
		confirmationtokenService.saveConfirmationtoken(confirmationToken);
		
		return token;
	}
	
    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

}
