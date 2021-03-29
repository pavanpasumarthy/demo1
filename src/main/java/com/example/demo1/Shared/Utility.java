package com.example.demo1.Shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;
@Component
public class Utility {
	public String userIdGenrator() {
		final String Values = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        Random RANDOM = new SecureRandom();
        StringBuilder returnValue = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            returnValue.append(Values.charAt(RANDOM.nextInt(Values.length())));
        }
		return new String(returnValue);
	}
	public String addressIdGenrator() {
		final String Values = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        Random RANDOM = new SecureRandom();
        StringBuilder returnValue = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            returnValue.append(Values.charAt(RANDOM.nextInt(Values.length())));
        }
		return new String(returnValue);
	}
}
