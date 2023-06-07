package com.crudproject.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crudproject.springboot.model.User;
import com.crudproject.springboot.repository.UserRepo;
import com.crudproject.springboot.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserRepo myUserRepo;
	@Autowired
	private UserService myUserService;

	//login
	@RequestMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("loginError", true);
		return "login";
	}

	//registerGet
	@RequestMapping("/register")
	//@GetMapping("/register")
	public String registerPage(Model model) {

		User user = new User();

		model.addAttribute("user", user);
		model.addAttribute("error", true);
		return "register";
	}

	//registerPost
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	//@PostMapping("/register")
	public String registerPagePost(Model model, @ModelAttribute User user) {
		User db_user = myUserRepo.checkEmail(user.getEmail());

		if (db_user == null) {
			myUserRepo.save(user);
			//model.addAttribute("error", true);
			model.addAttribute("user", new User());
			model.addAttribute("loginError", true);

			return "login";
			//go to login
		} else {
			model.addAttribute("error", false);
			model.addAttribute("user", user);
		}
		model.addAttribute("user", user);
		return "register";
	}

	//login sumit button
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String sumitLoginPage(Model model, @ModelAttribute User user) {

		User db_user = myUserRepo.checkLogin(user.getEmail(), user.getPassword());

		//not register
		if (db_user == null) {
			//show error

			model.addAttribute("loginError", false);

		} else {
			// registered
			List<User> users = myUserRepo.findAll();
			model.addAttribute("users", users);
			return "main";

		}

		model.addAttribute("user", user);

		return null;
	}

	//Edit Page
	@RequestMapping("/edit/{id}")
	public String editPage(Model model, @PathVariable("id") long id) {

		User user = myUserRepo.findById(id).orElseThrow();
		model.addAttribute("user", user);

		return "edit";
	}

	//Edit Sumit
	@RequestMapping("/edit")
	public String editSumit(Model model, @ModelAttribute User user) {
		myUserRepo.save(user);
		List<User> users = myUserRepo.findAll();
		model.addAttribute("users", users);

		return "main";

	}
	
	//Delete Page
	@RequestMapping("/delete/{id}")
	public String deletePage(Model model, @PathVariable("id") long id) {

		User user = myUserRepo.findById(id).orElseThrow();
		model.addAttribute("user", user);

		return "delete";
	}
	
	//Delete Sumit
	@RequestMapping("/delete")
	public String deleteSumit(Model model, @ModelAttribute User user) {
		myUserRepo.delete(user);
		List<User> users = myUserRepo.findAll();
		model.addAttribute("users", users);

		return "main";

	}
	
    @GetMapping("/search")
    public String searchUsers(Model model, @ModelAttribute User user,@RequestParam("keyword") String keyword) {
        List<User> users = myUserService.searchUsers(keyword);
		model.addAttribute("users", users);
        return "main";
    }
	


}
