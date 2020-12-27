package uz.personal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.personal.domain.auth._User;
import uz.personal.dto.auth.CustomUserDetails;
import uz.personal.mapper.auth.UserMapper;
import uz.personal.repository.auth.IUserRepository;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final IUserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserDetailsService(IUserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        _User user = userRepository.findByUsername(username);
        if (user == null)
            throw new RuntimeException(String.format("User with username '%s' not found", username));
        if (user.isLocked())
            throw new RuntimeException(String.format("User with username '%s' is locked", username));

        return new CustomUserDetails(user, userMapper.toDto(user));
    }
}
