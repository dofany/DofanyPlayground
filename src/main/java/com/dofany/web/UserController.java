package com.dofany.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.dofany.domain.User;
import com.dofany.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;


	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}

	@PostMapping("/login")
	public String login(String id, String pswd1, HttpSession session) {
		User user = userRepository.findById(id);
		if (user == null) {
			System.out.println("Login Fallure!");
			return "redirect:/users/loginForm";
		}
		if (!user.matchPassword(pswd1)) {
			System.out.println("Login Fallure!");
			return "redirect:/users/loginForm";
		}
		System.out.println("Login Success!");
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return "redirect:/";
	}

	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}

	@PostMapping("")
	public String create(User user) {
		System.out.println("user : " + user);
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("")
	public String admin(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}

	@GetMapping("/{userId}/form")
	public String updateForm(@PathVariable Long userId, Model model, HttpSession session) {
		if (HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		if (!sessionedUser.matchUserId(userId)) {
			throw new IllegalStateException("자신의 정보만 수정가능합니다.");
		}

		User user = userRepository.findById(userId).get();
		model.addAttribute("user", user);
		return "/user/updateForm";
	}

	@PostMapping("/{userId}")
	public String update(@PathVariable Long userId, User updateUser, HttpSession session) {
		if (HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		if (!sessionedUser.matchUserId(userId)) {
			throw new IllegalStateException("자신의 정보만 수정가능합니다.");
		}

		User user = userRepository.findById(userId).get();
		user.update(updateUser);
		userRepository.save(user);
		return "redirect:/users";
	}
}
