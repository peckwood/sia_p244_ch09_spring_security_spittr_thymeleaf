package spittr.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer{
	static{
		System.out.println("SecurityWebApplicationInitializer loaded");
	}
	
}
