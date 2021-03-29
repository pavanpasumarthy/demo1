package com.example.demo1.security;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
// authentication and generating the jwtToken
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo1.SpringApplicationContext;
import com.example.demo1.Shared.dto.UserDto;
import com.example.demo1.service.UserServices;
import com.example.demo1.ui.model.userRequests.UserLoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private AuthenticationManager authenticationmanager;
 

    public AuthenticationFilter(AuthenticationManager authenticationManager)
    {
    	this.authenticationmanager=authenticationManager;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
    		HttpServletResponse res) throws AuthenticationException
    {
    	//here the jason paylod will come for authentication
    	try {
    		//here the inpu json data maps with the UserLoginRequestModel class
    	UserLoginRequestModel creds= new ObjectMapper()
    			.readValue(req.getInputStream(), UserLoginRequestModel.class);
    	
    	return authenticationmanager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getEmail(),
                        creds.getPassword(),
                        new ArrayList<>())
        );
        
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
    		                       HttpServletResponse res,
    		                       FilterChain chain,
    		                       Authentication auth)
    { 
    	String username=((User)auth.getPrincipal()).getUsername();
          String token=Jwts.builder().setSubject(username)
        		  .setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
        		  .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret() )
                  .compact();
          UserServices userservice=(UserServices)SpringApplicationContext.getBean("userServiceImpl");
          UserDto userdto=userservice.getUser(username);
          res.addHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_PREFIX+token);
          res.addHeader("UseId",userdto.getUserId());
    }
}
