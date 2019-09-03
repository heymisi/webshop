/*
package kmihaly.mywebshop.security;

import kmihaly.mywebshop.domain.model.user.User;
import kmihaly.mywebshop.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public MyUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUserName(username).orElseThrow(() -> new IllegalArgumentException("User: " + username + " not found."));

        return new MyUserDetails(user);
    }
}
*/
