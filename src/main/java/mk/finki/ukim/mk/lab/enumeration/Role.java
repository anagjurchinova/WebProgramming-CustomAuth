package mk.finki.ukim.mk.lab.enumeration;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;
    @Override
    public String getAuthority() {
        return null;
    }
}
