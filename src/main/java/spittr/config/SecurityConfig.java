	package spittr.config;
	
	import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
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
			   		+ " from spitter where username =?")
			   .passwordEncoder(passwordEncoder());
		}
	
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		    characterEncodingFilter.setEncoding("UTF-8");
		    characterEncodingFilter.setForceEncoding(true);
			http.addFilterBefore(characterEncodingFilter, CsrfFilter.class);
			
			http
			.formLogin()//support form login (unauthorized requests will be redirected to loginPage automatically)
				.loginPage("/login22")
				.loginProcessingUrl("/login23")
				.defaultSuccessUrl("/")
			.and()
			.logout()
				.logoutUrl("/logout1")
				.logoutSuccessUrl("/")
			.and()
			//commented out because HTTPS is not enabled in Tomcat
			/*.requiresChannel()
				.antMatchers("/spitter/register").requiresSecure()
			.and()*/
			.authorizeRequests()
				//now anonymousUser can access /spitter/me
				//.antMatchers("/spitter/me").hasRole("ANONYMOUS")
				//.antMatchers("/spitter/me").access("hasRole('ROLE_ANONYMOUS')")//SpEL version
			
				//SpEL is good for gluing multiple conditions
				.antMatchers("/spitter/me").access("hasRole('ROLE_ANONYMOUS') and hasIpAddress('0:0:0:0:0:0:0:1')")
				
				.antMatchers(HttpMethod.POST, "/spittles").authenticated()
				.antMatchers("/spitter_only").access("hasRole('ROLE_SPITTER')" )
				.anyRequest().permitAll()
			.and()
			.rememberMe()
				.tokenValiditySeconds(2419200)
				.key("spitterKey");//use custom private key, default is SpringSecured
		}
		
		@Bean
		public PasswordEncoder passwordEncoder(){
			return new BCryptPasswordEncoder();
		}
	}
