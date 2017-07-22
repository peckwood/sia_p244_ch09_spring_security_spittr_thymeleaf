package spittr.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	static{
		System.out.println("SecurityConfig loaded");
	}
	@Autowired
	DataSource dataSource;
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
		 .dataSource(dataSource)
		  .usersByUsernameQuery("select username, password, enabled "
		  		+ " from spitter where username = ?")
		   .authoritiesByUsernameQuery("select username, role "
		   		+ " from spitter where username =?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	    characterEncodingFilter.setEncoding("UTF-8");
	    characterEncodingFilter.setForceEncoding(true);
		http.addFilterBefore(characterEncodingFilter, CsrfFilter.class);
	}
	
	
	
}
