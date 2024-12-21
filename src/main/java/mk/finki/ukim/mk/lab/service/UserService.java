package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.repository.jpa.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

public interface UserService extends UserDetailsService {
    public void register(String username, String password, String repeatPassword);
}
