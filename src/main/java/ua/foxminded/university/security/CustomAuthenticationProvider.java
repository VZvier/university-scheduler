package ua.foxminded.university.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder encoder;

    private final CustomUserDetailsService customUserDetails;

    public CustomAuthenticationProvider(PasswordEncoder encoder, CustomUserDetailsService customUserDetails){
        this.encoder = encoder;
        this.customUserDetails = customUserDetails;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Unknown user " + username);
        }
        if (!encoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Wrong password!" + password);
        }
        log.info("UserDetails satisfy the conditions.");
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        log.info("Authentication is authenticated? - {}. \nName - {}, \nCredentials - {}, \nPrincipals - {}, \nDetails - {}, \nAuthorities - {}.", auth.isAuthenticated(),
                auth.getName(),
                auth.getCredentials(),
                auth.getPrincipal(),
                auth.getDetails(),
                auth.getAuthorities());
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}