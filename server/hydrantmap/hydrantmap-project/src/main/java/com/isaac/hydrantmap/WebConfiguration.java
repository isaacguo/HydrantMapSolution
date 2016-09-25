package com.isaac.hydrantmap;

import org.h2.server.web.WebServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebConfiguration {
	@Bean
	ServletRegistrationBean h2servletRegistration()
	{
		ServletRegistrationBean registrationBean=new ServletRegistrationBean(new  WebServlet());
		registrationBean.addUrlMappings("/console/*");
		registrationBean.addInitParameter("webAllowOthers", "true");
		return registrationBean;
	}
}

@Configuration
class SecurityConfiguration extends WebSecurityConfigurerAdapter
{

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// TODO Auto-generated method stub
		httpSecurity.authorizeRequests().antMatchers("/").permitAll().and().authorizeRequests().antMatchers("/console/**").permitAll();
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
	}
	
}
