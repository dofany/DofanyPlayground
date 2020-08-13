package com.dofany.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dofany.domain.Question;
import com.dofany.domain.QuestionRepository;
import com.dofany.domain.Result;
import com.dofany.domain.User;

@Controller
@RequestMapping("/qna")
public class QnaController {
	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("")
	public String qnaHome(Model model) {
		model.addAttribute("question", questionRepository.findAll());
		return "/qna/index";
	}

	@GetMapping("/form")
	public String qnaForm(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		return "/qna/form";
	}

	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}

		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser, title, contents);
		questionRepository.save(newQuestion);
		return "redirect:/qna";
	}

	@GetMapping("/{userId}")
	public String show(@PathVariable Long userId, Model model) {
		model.addAttribute("question", questionRepository.findById(userId).get());
		return "/qna/show";
	}

	@GetMapping("/{userId}/form")
	public String updateForm(@PathVariable Long userId, Model model, HttpSession session) {
		Question question = questionRepository.findById(userId).get();
		Result result = valid(session, question);
		if(!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}
		
			model.addAttribute("question", question);
			return "/qna/updateForm";
		
	}
	
	private Result valid(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인이 필요합니다.");
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!question.isSameWriter(loginUser)) {
			return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
		}
		return Result.ok();
	}
	
	
	@PostMapping("/{userId}")
	public String update(@PathVariable Long userId, Model model, String title, String contents, HttpSession session) {
		Question question = questionRepository.findById(userId).get();
		Result result = valid(session, question);
		if(!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}
		question.update(title, contents);
		questionRepository.save(question);
		return String.format("redirect:/qna/%d", userId);
	}

	@DeleteMapping("/{userId}")
	public String delete(@PathVariable Long userId, Model model, HttpSession session) {
		
		Question question = questionRepository.findById(userId).get();
		Result result = valid(session, question);
		if(!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}
		questionRepository.deleteById(userId);
		return "redirect:/qna";
		

	}

}
