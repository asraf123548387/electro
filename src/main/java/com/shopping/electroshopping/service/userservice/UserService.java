package com.shopping.electroshopping.service.userservice;

import com.shopping.electroshopping.dto.UserSignUpDto;
import com.shopping.electroshopping.model.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {
    User save(UserSignUpDto signUpDto);
    public void blockUser(Long id);
    public void unblockUser(Long id);


}
