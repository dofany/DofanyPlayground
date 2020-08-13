package com.dofany.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dofany.domain.Answer;
import com.dofany.domain.AnswerRepository;
import com.dofany.domain.Question;
import com.dofany.domain.QuestionRepository;
import com.dofany.domain.Result;
import com.dofany.domain.User;

@RestController
@RequestMapping("/api/qna/{questionUserId}/answer")
public class ApiAnswerController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@PostMapping("")
	public Answer create(@PathVariable Long questionUserId, String contents, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return null;
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(questionUserId).get();
		Answer answer = new Answer(loginUser,question,contents);
		return answerRepository.save(answer);
	}
	@DeleteMapping("/{userId}")
	public Result delete(@PathVariable Long questionUserId, @PathVariable Long userId, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인이 필요합니다.");
		}
		Answer answer = answerRepository.findById(userId).get();
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if(!answer.isSameWriter(loginUser)) {
			return Result.fail("자신의 글만 삭제할 수 있습니다.");
		}
		
		answerRepository.deleteById(userId);
		return Result.ok();
	}
}
