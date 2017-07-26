package spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import spittr.Spitter;
import spittr.data.SpitterRepository;

@Controller
@RequestMapping("/spitter")
public class SpitterController {

	private SpitterRepository spitterRepository;

	@Autowired
	public SpitterController(SpitterRepository spitterRepository) {
		this.spitterRepository = spitterRepository;
	}

	// @ModelAttribute
	// public Spitter spitter() {
	// return new Spitter();
	// }

	@RequestMapping(value = "/register", method = GET)
	public String showRegistrationForm(Model model) {
		// key is inferred to "spitter"
		// which corresponds to the commandName in "registerForm.jsp"
		model.addAttribute(new Spitter());
		return "registerForm";
	}

	@RequestMapping(value = "/register", method = POST)
	public String processRegistration(@Valid Spitter spitter, Errors errors, ServletRequest request) {
		System.out.println("request character encoding: " + request.getCharacterEncoding());
		System.out.println("spitter in form: " + spitter);
		if (errors.hasErrors()) {
			System.out.println(errors.getFieldError("role"));
			return "registerForm";
		}
		spitterRepository.save(spitter);
		return "redirect:/spitter/" + spitter.getUsername();
	}

	@RequestMapping(value = "/me", method = GET)
	public String me() {
		System.out.println("ME ME ME ME ME ME ME ME ME ME ME");
		return "home";
	}

	@RequestMapping(value = "/{username}", method = GET)
	public String showSpitterProfile(@PathVariable String username, Model model) {
		Spitter spitter = spitterRepository.findByUsername(username);
		System.out.println("spitter found: " + spitter);
		model.addAttribute(spitter);
		return "profile";
	}
	
	@RequestMapping(value = "/spitter_only", method = GET)
	public String spitterOnly() {
		return "spitterOnly";
	}

}
