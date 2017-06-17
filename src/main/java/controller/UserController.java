package controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import entities.User;
import entities.UserAuth;
import entities.UserResponse;
import repositories.UserAuthRepository;
import repositories.UserRepository;

@RestController   
@RequestMapping(path="/user") 
public class UserController {
	@Autowired 
	private UserRepository userRepository;
	@Autowired
	private UserAuthRepository userAuthRepository;
	
	@PostMapping(path="/add") 
	public @ResponseBody UserResponse addNewUser (@RequestBody User user ) {
		return signUpUser(user);
	}
	
	public UserResponse signUpUser(User user) {
		UserResponse response = new UserResponse();
		User existingUser = userRepository.findByEmail(user.getEmail());
		if(existingUser == null) {
			User createdUser = userRepository.save(user);
			userAccessToken(user);
			response.setStatus("201");
			response.setUserMessage("user cerated with email:"+ createdUser.getEmail());
		} else {
			response.setStatus("403");
			response.setUserMessage("User already exist with email :" + existingUser.getEmail());
			
		}
		return response;
	}
	
	public void userAccessToken(User user) { 
		UserAuth userAuth = new UserAuth();
	    final String accessToken = UUID.randomUUID().toString().replaceAll("-", "");
	    userAuth.setuId(user.getId());
	    userAuth.setAccessToken(accessToken);
	    userAuthRepository.save(userAuth);
	    
	}
	
	
}
