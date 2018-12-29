package com.hangyasi.sporteo.security;

import com.hangyasi.sporteo.dto.Person;
import com.hangyasi.sporteo.service.PersonService;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private PersonService personService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Person person = personService.findByEmail(email);
    if(Objects.isNull(person)) {
      throw new UsernameNotFoundException("User not exists!!");
    }

    List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
    return new User(person.getEmail(), person.getPassword(), authorities);
  }
}
