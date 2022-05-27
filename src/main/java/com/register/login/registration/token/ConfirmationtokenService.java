package com.register.login.registration.token;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfirmationtokenService {

	private final ConfimationTokenRepository confirmationTokenRepository;
	
	public void saveConfirmationtoken(ConfirmationToken token) {
		confirmationTokenRepository.save(token);
	}

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }
	
    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
