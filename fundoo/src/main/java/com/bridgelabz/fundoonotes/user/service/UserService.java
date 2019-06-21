package com.bridgelabz.fundoonotes.user.service;



import java.io.UnsupportedEncodingException;
//import java.util.List;
//import java.util.Optional;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.ResponseToken;
import com.bridgelabz.fundoonotes.user.dto.LoginDto;
import com.bridgelabz.fundoonotes.user.dto.UserDto;
import com.bridgelabz.fundoonotes.user.model.User;
public interface UserService  {

	ResponseToken onRegister(UserDto userDto)throws UserException, MessagingException, UnsupportedEncodingException ;
	
	
	
	ResponseToken onLogin(LoginDto  loginDto)throws UserException, UnsupportedEncodingException;
    
   
    Response validateEmailId(String token) throws UserException;
    
   
    Response forgetPassword(String emailId) throws UserException, UnsupportedEncodingException, MessagingException;
   
    Response resetPaswords(String token,String password) throws UserException;
    
    
   
public User getUserInfo(String emailId);
public Response uploadImage(String emailID,MultipartFile file);
public Resource getUploadedImage(String token);

ResponseToken authentication(Optional<User> user, String password) throws UnsupportedEncodingException, UserException;





}

