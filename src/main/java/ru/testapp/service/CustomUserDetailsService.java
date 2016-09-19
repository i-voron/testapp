package ru.testapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.testapp.dao.IUserDao;
import ru.testapp.entity.User;
import ru.testapp.utils.CustomFileReader;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService, AuthenticationProvider {
    @Autowired
    private CustomFileReader fileReader;

    @Autowired
    private IUserDao userDao;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user=userDao.getUser(s);

        List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetails userDetails=new org.springframework.security.core.userdetails.User(
                user.getLogin(),"",grantedAuths);
        return userDetails;
    }

    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
        String login = String.valueOf(auth.getPrincipal());
        String password = String.valueOf(auth.getCredentials());

        User user=userDao.getUser(login);

        if (user == null || !user.getLogin().equalsIgnoreCase(login)) {
            throw new BadCredentialsException("Нет такого пользователя");
        }

//        String hash=fileReader.findByLogin2(user.getId());
        String hash=fileReader.findByLogin(user.getId());

        Md5PasswordEncoder encoderMD5 = new Md5PasswordEncoder();

//        if (hash==null || !BCrypt.checkpw(password, hash)) {
        if (hash==null || !encoderMD5.isPasswordValid(hash,password,null)) {
            throw new BadCredentialsException("Неверный пароль");
        }
        List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));

        auth = new UsernamePasswordAuthenticationToken(login, hash, grantedAuths);
        auth.setDetails(user);

        return auth;
    }


    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
