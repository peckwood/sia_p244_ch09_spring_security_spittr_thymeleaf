package spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
		//key is inferred to "spitter"
		//which corresponds to the commandName in "registerForm.jsp"
		model.addAttribute(new Spitter());
		return "registerForm";
	}

	@RequestMapping(value = "/register", method = POST)
	public String processRegistration(@Valid Spitter spitter, Errors errors) {
		System.out.println(spitter.getFirstName());
		if (errors.hasErrors()) {
			return "registerForm";
		}

		spitterRepository.save(spitter);
		return "redirect:/spitter/" + spitter.getUsername();
	}

	@RequestMapping(value = "/{username}", method = GET)
	public String showSpitterProfile(@PathVariable String username, Model model) {
		Spitter spitter = spitterRepository.findByUsername(username);
		model.addAttribute(spitter);
		return "profile";
	}

}
