package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.exceptions.InvalidCredentialsException;
import mk.finki.ukim.mk.lab.exceptions.PasswordMismatchException;
import mk.finki.ukim.mk.lab.exceptions.UserDoesNotExistException;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.repository.jpa.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findAllByUsername(username)
                .orElseThrow(() -> new UserDoesNotExistException(username));
    }

    @Override
    public void register(String username, String password, String repeatPassword) throws InvalidCredentialsException, PasswordMismatchException {
        if(username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
           throw new InvalidCredentialsException();
        }
        if(!password.equals(repeatPassword)){
            throw new PasswordMismatchException();
        }

        userRepository.save(new User(username, passwordEncoder.encode(password)));
    }
}
