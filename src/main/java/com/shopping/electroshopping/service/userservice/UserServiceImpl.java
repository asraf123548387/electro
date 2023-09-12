package com.shopping.electroshopping.service.userservice;

import com.shopping.electroshopping.dto.UserSignUpDto;
import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.model.role.Role;
import com.shopping.electroshopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository customerRepository) {
        this.userRepository = customerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     User user=userRepository.findByEmail(username);
       if (user==null)
       {
           throw new UsernameNotFoundException("invalid username or password");

       }
       if(user.isBlocked())
       {
           throw new DisabledException("User is blocked");
       }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


    @Override
    public User save(UserSignUpDto signUpDto) {
        User customer=new User(signUpDto.getUserName(),signUpDto.getEmail(), passwordEncoder.encode(signUpDto.getPassword()),signUpDto.getPhoneNumber(),Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(customer);
    }

    @Override
    public void blockUser(Long id) {
        Optional<User> optionalUser=userRepository.findById(id);
        if(optionalUser.isPresent())
        {
            User user=optionalUser.get();
            user.setBlocked(true);
            userRepository.save(user);
        }
    }
    public void unblockUser(Long id){
        Optional<User> optionalUser=userRepository.findById(id);
        if(optionalUser.isPresent())
        {
            User user =optionalUser.get();
            user.setBlocked(false);
            userRepository.save(user);
        }
    }




    public List<User> getCustomerByName(String email) {
        return userRepository.findByName(email);
    }
}
