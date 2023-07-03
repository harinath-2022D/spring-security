package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userInfo = userInfoRepository.findByName(username);

//        simple way

//        UserInfo userInfo1 = userInfo.get();
//        if(!userInfo.isPresent()){
//            throw  new UsernameNotFoundException("user not found");
//        }
//        return new UserInfoUserDetails(userInfo1);

        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(()->new UsernameNotFoundException("user not found"+username));
    }
}
