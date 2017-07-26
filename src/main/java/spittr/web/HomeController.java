package spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

	@RequestMapping(method = GET)
	public String home(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.out.println("currentPrincipalName: "+currentPrincipalName);
		System.out.println("authentication: "+authentication);
		System.out.println("authentication.isAuthenticated(): "+authentication.isAuthenticated());
		for(GrantedAuthority authority:authentication.getAuthorities()){
			System.out.println(authority);
		}
		System.out.println("authentication.isAuthenticated(): "+authentication.isAuthenticated());
		return "home";
	}

}
