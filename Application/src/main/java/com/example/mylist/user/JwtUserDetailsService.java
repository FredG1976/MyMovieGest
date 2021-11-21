package com.example.mylist.user;


import com.example.mylist.domain.usermylist.UserMl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	UserMlService userMlService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserMl> userMlOptional = userMlService.findByLogin(username);
		if(userMlOptional.isPresent()){
			ArrayList<SimpleGrantedAuthority> authorities =new ArrayList<>();
			authorities.add( new SimpleGrantedAuthority("ROLE_USER"));
			UserMl userMl = userMlOptional.get();
				if(userMl.getAdmin()){authorities.add( new SimpleGrantedAuthority("ROLE_ADMIN"));}
				User userMlToUser =  new User(userMl.getLogin(),userMl.getPassword(),authorities);
				return (userMlToUser) ;
		}
		else {
			throw new UsernameNotFoundException("User not found with login: " + username);
		}
	}
}