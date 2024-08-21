package kr.ac.kookmin.wink.planlist.global.security;

import kr.ac.kookmin.wink.planlist.global.exception.CustomException;
import kr.ac.kookmin.wink.planlist.global.exception.GlobalErrorCode;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new CustomException(GlobalErrorCode.SECURITY_USER_NOT_FOUND));

            return new SecurityUser(user, email);
        } catch(UsernameNotFoundException exception) {
            throw new CustomException(GlobalErrorCode.SECURITY_USER_NOT_FOUND, exception);
        }
    }
}
