package com.example.demo1.security;

import com.example.demo1.SpringApplicationContext;

public class SecurityConstants {
	public static final long EXPIRATION_TIME =86400000;//1day
	public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/user/createUser";
    //public static final String Token_Secret="i5sfa7a8fegg85a7";
   
    public  static String getTokenSecret()
    {
    	AppProperties approperties=(AppProperties)SpringApplicationContext.getBean("AppProperties");
    	return approperties.getTokenSecret();
    }
}
