package com.opm.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
//	@Autowired
//	@Qualifier("myUserDetailsService")
//	UserDetailsService userDetailsService;

	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		auth.inMemoryAuthentication().withUser("user@opm.com").password("123456").roles("MAILER");
//		auth.inMemoryAuthentication().withUser("mouadtk").password("123456").roles("MAILER");
//		auth.inMemoryAuthentication().withUser("admin").password("123456").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http
//		.sessionManagement()
//			//.maximumSessions(1)
//			.expiredUrl("/Auth/expired");
			
		
		http	
		.authorizeRequests()
			.antMatchers("/Auth/expired").permitAll()
	        .antMatchers("/resources/**").permitAll()
	        .antMatchers("/assets/**").permitAll()
//	        .anyRequest().authenticated() 
	        .anyRequest().permitAll()	              
	        .and().exceptionHandling().accessDeniedPage("/403")	        
	        .and()
	        
	        .formLogin()
	        //.loginPage("/Auth/login")
	        .usernameParameter("username")
			.passwordParameter("password")
			.loginProcessingUrl("/Auth/Validate")
			.defaultSuccessUrl("/")
			//.failureUrl("/Auth/login?error")
	        .permitAll()
	        .and()
	    .logout()
	    	//.logoutSuccessUrl("/Auth/logout")
	        .permitAll()
	        .and()
	    .csrf().disable();
//		
	}

	@Bean
	public Md5PasswordEncoder passwordEncoder(){
		Md5PasswordEncoder encoder =  new Md5PasswordEncoder();
		encoder.setEncodeHashAsBase64(true);		
		return encoder;
	}
	
	
}
