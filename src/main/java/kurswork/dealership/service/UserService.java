package kurswork.dealership.service;

import java.util.List;

import kurswork.dealership.dto.UserDto;
import kurswork.dealership.entity.User;

public interface UserService {

	 User saveUser(UserDto userDto);

	    User findByEmail(String email);

	    List<UserDto> findAllUsers();
}
