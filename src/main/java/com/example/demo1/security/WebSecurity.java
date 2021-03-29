package com.example.demo1.security;
// this class is used for addind security to our project
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.demo1.service.impl.userServiceImpl;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private final userServiceImpl userDetailsService; /*The userDetailsServices interface extended by the userService  interface provided by the
	//(org.springframework.security.core.userdetails.UserDetailsService) which has the method called loadUserByUsername and it is implemented in the 
	 * userservice class  */
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public WebSecurity(userServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST,SecurityConstants.SIGN_UP_URL)
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.addFilter(getAuthenticationFilter())
		.addFilter(new AuthorizationFilter(authenticationManager())) 
		.sessionManagement()
		 .sessionCreationPolicy(SessionCreationPolicy.STATELESS); 
	}
	/* edhi authentication cheyadaniki use avudhi the authentication manager class ni send  chestham man dentlo*/
 @Override
 public void configure(AuthenticationManagerBuilder auth) throws Exception
 {
	 auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
 }
 public AuthenticationFilter getAuthenticationFilter() throws Exception
 {
	 final AuthenticationFilter authenticatinfilter=new AuthenticationFilter(authenticationManager());
	 authenticatinfilter.setFilterProcessesUrl("/user/login");
	 return authenticatinfilter;
 }
}
