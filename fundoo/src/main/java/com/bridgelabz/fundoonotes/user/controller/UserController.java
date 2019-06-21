package com.bridgelabz.fundoonotes.user.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.ResponseToken;
import com.bridgelabz.fundoonotes.user.dto.LoginDto;
import com.bridgelabz.fundoonotes.user.dto.UserDto;
import com.bridgelabz.fundoonotes.user.model.User;
import com.bridgelabz.fundoonotes.user.service.UserService;
import com.bridgelabz.fundoonotes.user.service.UserServiceImp;

import ch.qos.logback.core.status.Status;

@RestController
@RequestMapping(value = "/user")
//@CrossOrigin(allowedHeaders = "*" ,origins = "*")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class UserController {
	@Autowired
	Environment envirnment;
	@Autowired
	UserService userServiceImpl;

	@PostMapping("/register")
	public ResponseEntity<ResponseToken> register(@RequestBody @Valid UserDto userDto)
			throws UserException, MessagingException, UnsupportedEncodingException {
		System.out.println("Inside register");
		ResponseToken response = userServiceImpl.onRegister(userDto);
		return new ResponseEntity<ResponseToken>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/fundoostart")
	public String fundoo() {
		return "Hello Fundoo";
	}

	@GetMapping(value = "/{token}/valid")
	public ResponseEntity<Response> emailValidation(@PathVariable String token) throws UserException {
		Response response = userServiceImpl.validateEmailId(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	@PostMapping(value = "/login")
	public ResponseEntity<ResponseToken> onLogin(@RequestBody LoginDto loginDto)
			throws UserException, UnsupportedEncodingException {
		ResponseToken response = userServiceImpl.onLogin(loginDto);
		return new ResponseEntity<ResponseToken>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/forgetpassword")
	public ResponseEntity<?> forgetPassword(@RequestBody String emailId)
			throws UserException, UnsupportedEncodingException, MessagingException {
		System.out.println("email Id :" + emailId);
		Response status = userServiceImpl.forgetPassword(emailId);
		return new ResponseEntity<Response>(status, HttpStatus.OK);
	}

	@PutMapping(value = "/reset")
	public ResponseEntity<?> resetPaswords(@RequestParam String token, @RequestParam("password") String password) {
		System.out.println(token);
		Response response = userServiceImpl.resetPaswords(token, password);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/userInfo")
	public ResponseEntity<User> getUserInfo(@RequestParam String emailId) {
		User user = userServiceImpl.getUserInfo(emailId);
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	@PostMapping(value = "/uploadProfilepic")
	public ResponseEntity<Response> uploadProfile(@RequestHeader String token, @RequestParam MultipartFile file) {
		Response response = userServiceImpl.uploadImage(token, file);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getUploadpic/{token}")
	public ResponseEntity<Resource> getUploadpic(@PathVariable String token) {
		Resource resource = userServiceImpl.getUploadedImage(token);
		return new ResponseEntity<Resource>(resource, HttpStatus.OK);
	}
	

}
