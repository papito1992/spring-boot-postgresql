package com.alpaka.security.services;

import com.alpaka.model.user.User;
import com.alpaka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username);

            return UserDetailsImpl.build(user);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
//                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        }
    }

}
