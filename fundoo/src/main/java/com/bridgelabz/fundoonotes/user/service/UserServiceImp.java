package com.bridgelabz.fundoonotes.user.service;

import java.io.UnsupportedEncodingException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.ResponseToken;
import com.bridgelabz.fundoonotes.user.dto.LoginDto;
import com.bridgelabz.fundoonotes.user.dto.UserDto;
import com.bridgelabz.fundoonotes.user.model.User;
import com.bridgelabz.
fundoonotes.user.repository.UserRepository;
import com.bridgelabz.fundoonotes.util.StatusHelper;
import com.bridgelabz.fundoonotes.util.UserToken;
@Service
@PropertySource("classpath:fundoo.properties")
public class UserServiceImp implements UserService {

	private final Path filelocation=Paths.get("/home/admin1/Desktop/bridgelab");
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ResponseToken responseStatus;
	
	@Autowired
	private Response response;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private MymailService myMail;
	
	UserToken userToken=new UserToken();
	StatusHelper status=new StatusHelper();
	
	@Override
	public ResponseToken onRegister(UserDto userDto) throws UserException, MessagingException, UnsupportedEncodingException 
	{

//		String emailId=userDto.getEmailId();
//		System.out.println("show my email id"+emailId);
//		User user=modelmapper.map(userDto,User.class);
//		
//		System.out.println(user.toString());
//		Optional<User> nowPresent=userRepository.findByEmailId(user.getEmailId());
//		
//		if(nowPresent.isPresent())
//		{
//			throw new UserException(environment.getProperty("user_registration_error"));
//		}
//		user.setEmailId(userDto.getEmailId());
//		user.setUserName(userDto.getUserName());
//		user.setRegisteredDate(LocalDate.now());
//		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//		user=userRepository.save(user);
//		Long userId = user.getUserId();
//		System.out.println(userId);
//		
//		System.out.println(emailId);
//		
//
//		myMail.sendMail(emailId, "confirmation mail", myMail.getUrl(userId)+"/valid");
//		
//		responseStatus=StatusHelper.statusInfo("successful register", 100);
//		return responseStatus;
		String emailId=userDto.getEmailId();
		System.out.println("show my email id"+emailId);
		User user=modelmapper.map(userDto,User.class);
		
		System.out.println(user.toString());
		Optional<User> nowPresent=userRepository.findByEmailId(user.getEmailId());
		
		if(nowPresent.isPresent())
		{
			throw new UserException(environment.getProperty("user_registration_error"));
		}
		user.setEmailId(userDto.getEmailId());
		user.setUserName(userDto.getUserName());
		user.setRegisteredDate(LocalDate.now());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user=userRepository.save(user);
		Long userId = user.getUserId();
		System.out.println(userId);
		
		System.out.println(emailId);
		

		myMail.sendMail(emailId, "confirmation mail", myMail.getUrl(userId)+"/valid");
		
		responseStatus=StatusHelper.statusInfo("successful register", 100);
		return responseStatus;
	}

	@Override
	public ResponseToken onLogin(LoginDto loginDto) throws UnsupportedEncodingException{
	ResponseToken response=new ResponseToken();
		System.out.println("email id--> "+ loginDto.getEmailId());
		if(userRepository.findByEmailId(loginDto.getEmailId()).isPresent())
		{
			
			Optional<User> user=userRepository.findByEmailId(loginDto.getEmailId());
			return authentication(user,loginDto.getPassword());
		}
		
		throw new UserException(401,environment.getProperty("user.login.error"));
		
	
		
	}

	@Override
	public Response validateEmailId(String token) throws UserException {
		
		Long id=userToken.tokenVerify(token);
		User user=userRepository.findById(id).orElseThrow(()->new UserException(404,environment.getProperty("user.validation.emailId")));
		user.setVarified(true);
		userRepository.save(user);
		response=StatusHelper.statusResponseInfo(environment.getProperty("user.validation"),200);
		return response;
	}

	@Override
	public Response forgetPassword(String emailId)
			throws UserException, UnsupportedEncodingException, MessagingException {
		Optional<User> alreadyPresent=userRepository.findByEmailId(emailId);
		System.out.println(emailId);
		if(!alreadyPresent.isPresent())
		{
			throw new UserException(401,environment.getProperty("user.forget.password"));
		}
		Long id=alreadyPresent.get().getUserId();
		myMail.sendMail(emailId, "send  password reset mail", myMail.getUrl(id));
		response=StatusHelper.statusResponseInfo(environment.getProperty("user.forget.Link"),200);
		return response;
	}

	@Override
	public Response resetPaswords(String token, String password) throws UserException {
		Long id=userToken.tokenVerify(token);
		User user=userRepository.findById(id).orElseThrow(()->new UserException(404,environment.getProperty("user.resetPassword")));
		password=passwordEncoder.encode(password);
		user.setPassword(password);
		userRepository.save(user);
		response=StatusHelper.statusResponseInfo(environment.getProperty("newPassword.reset.successful"),200);
		return response;
	}

	@Override
	public ResponseToken authentication(Optional<User> user, String password) throws UnsupportedEncodingException
			 {
		
		ResponseToken response=new ResponseToken();
		if(user.get().isVarified())
		{
			System.out.println("fundoo---->");
			boolean status=passwordEncoder.matches(password,user.get().getPassword());
			if(status==true)
			{
			String token=userToken.generateToken(user.get().getUserId());
			response.setToken(token);
			response.setStatusCode(200);
			response.setStatusMessage(environment.getProperty("user.login"));
			return response;
			
			}
			throw new UserException(401,environment.getProperty("user.login.password"));
		}
		System.out.println("hello------>");
		throw new UserException(401,environment.getProperty("user.login.register"));
	}

	@Override
	public User getUserInfo(String emailId) {
		Optional<User> user=userRepository.findByEmailId(emailId);
		if(!user.isPresent())
		{
			throw new UserException(404,environment.getProperty("user not exist"));
		}
		User userInfo=user.get();
		return userInfo;
	}

	@Override
	public Response uploadImage(String token, MultipartFile file) {
		Long id=userToken.tokenVerify(token);
		Optional<User> user=userRepository.findByUserId(id);
		if(!user.isPresent())
		{
			throw new UserException(404,environment.getProperty("user in valid"));
		}
		UUID uuid=UUID.randomUUID();
        String uniqueUserId=uuid.toString();
        try {
        	Files.copy(file.getInputStream(), filelocation.resolve(uniqueUserId), StandardCopyOption.REPLACE_EXISTING);
           // Files.copy(source, target, options)
        	
        	user.get().setProfilepic(uniqueUserId);
            userRepository.save(user.get());
        }
        catch (Exception e) {
			
		}
        response=StatusHelper.statusResponseInfo(environment.getProperty("profilepic.uploaded.successfully"),400);
		return response;
	}

	@Override
	public Resource  getUploadedImage(String token) {
		Long id=userToken.tokenVerify(token);
		Optional<User> user=userRepository.findByUserId(id);
		if(!user.isPresent())
		{
			throw new UserException(404,environment.getProperty("user invalid"));
		}
		Path imagePath=filelocation.resolve(user.get().getProfilepic());
		try {
			Resource resource=new UrlResource(((java.nio.file.Path) imagePath).toUri());
			if(resource.exists()||resource.isReadable())
			{
				return resource;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	


	
		
	
	
}
