package com.shopping.electroshopping.service.userservice;

import com.shopping.electroshopping.dto.UserAddressDto;
import com.shopping.electroshopping.dto.UserSignUpDto;
import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.model.role.Role;
import com.shopping.electroshopping.model.user.UserAddress;
import com.shopping.electroshopping.repository.UserAddressRepository;
import com.shopping.electroshopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
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


    @Autowired
    private UserAddressRepository userAddressRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User save(UserSignUpDto signUpDto) {
        User customer=new User(signUpDto.getUserName(),signUpDto.getEmail(),
                passwordEncoder.encode(signUpDto.getPassword()),signUpDto.getPhoneNumber(),
                signUpDto.getOtp(),Arrays.asList(new Role("ROLE_USER")));
//customer.setReferralCode(generateCode());
        return userRepository.save(customer);
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
public UserAddress addUserAddress(UserAddressDto userAddressDto)
{

    UserAddress userAddress = new UserAddress();
    userAddress.setStreetDetails(userAddressDto.getStreetDetails());
    userAddress.setCityName(userAddressDto.getCityName());
    userAddress.setAddressPhoneNumber(userAddressDto.getAddressPhoneNumber());
    userAddress.setState(userAddressDto.getState());
    userAddress.setPostalCode(userAddressDto.getPostalCode());


    Long userId = userAddressDto.getUser_id();
    if (userId == null) {
        throw new IllegalArgumentException("User ID cannot be null.");
    }

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
    userAddress.setUser(user);

    return userAddressRepository.save(userAddress);
}

    @Override
    public void editUserAddressInCheckOut(long id, UserAddressDto userAddressDto) {

        UserAddress userAddress=userAddressRepository.findById(id).orElse(null);
        if(userAddress!=null)
        {
            userAddress.setStreetDetails(userAddressDto.getStreetDetails());
            userAddress.setCityName(userAddressDto.getCityName());
            userAddress.setAddressPhoneNumber(userAddressDto.getAddressPhoneNumber());
            userAddress.setPostalCode(userAddressDto.getPostalCode());
            userAddress.setState(userAddress.getState());
            userAddressRepository.save(userAddress);
        }
    }



    public List<User> getCustomerByName(String email) {
        return userRepository.findByName(email);
    }


    @Transactional

    public void updateProfile(Long userId,UserSignUpDto updateProfile)
    {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setUserName(updateProfile.getUserName());
            user.setEmail(updateProfile.getEmail());
            user.setPhoneNumber(updateProfile.getPhoneNumber());
//            user.setPassword(passwordEncoder.encode(updateProfile.getPassword()));
            userRepository.save(user);
        }

    }

// user is already registred
public boolean isEmailAlreadyRegistered(String email) {
    // Use the UserRepository to check if the email is already registered
    return userRepository.existsByEmail(email);
}

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);

    }

    public boolean verifyOtpAndResetPassword(String otp, String newPassword) {
        User user = userRepository.findByOtp(otp);

        if (user != null) {
            // Update the user's password
            user.setPassword(passwordEncoder.encode(newPassword));

            // Clear the OTP (optional)
            user.setOtp(null);

            // Save the updated user entity to the database
            userRepository.save(user);

            // Password reset was successful
            return true;
        } else {
            // Invalid OTP or user not found
            return false;
        }
    }

    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            // Update the user's password
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

    }
    }


        public long getTotalNumberOfUsers() {
            return userRepository.countUsers();
        }

    public String generateCode() {
        int codeLength=6;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < codeLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            code.append(characters.charAt(randomIndex));
        }

        return code.toString();

    }

    public void toggleUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setBlocked(!user.isBlocked());
            userRepository.save(user);
        }
    }

}



