package com.hangyasi.sporteo.endpoint;

import static java.util.Objects.nonNull;

import com.hangyasi.sporteo.dto.ConfirmationForm;
import com.hangyasi.sporteo.dto.Person;
import com.hangyasi.sporteo.service.EmailService;
import com.hangyasi.sporteo.service.PersonService;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

  private PasswordEncoder bCryptPasswordEncoder;
  private PersonService personService;
  private EmailService emailService;

  @Autowired
  public RegisterController(PasswordEncoder bCryptPasswordEncoder, PersonService personService, EmailService emailService) {
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.personService = personService;
    this.emailService = emailService;
  }

  @PostMapping(value = "/register")
  public ResponseEntity<String> register(@RequestBody @Valid Person person) {
    Person existing = personService.findByEmail(person.getEmail());
    if(nonNull(existing)) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists!!");
    } else {
      person.setEnabled(false);
      person.setConfirmationToken(UUID.randomUUID().toString());
      personService.save(person);
      emailService.sendConfirmationMail(person.getEmail(), person.getConfirmationToken());
      return ResponseEntity.ok().body("User created!");
    }
  }

  @GetMapping(value = "/confirm")
  public ResponseEntity<String> validateRegistration(@RequestParam String token) {
    Person person = personService.findByConfirmationToken(token);
    if (!nonNull(person)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The confirmation token is invalid!!");
    } else {
      return ResponseEntity.ok("The confirmation token is valid!");
    }
  }

  @PostMapping(value = "/confirm")
  public ResponseEntity<String> confirmRegistration(@RequestBody ConfirmationForm form) {
    Zxcvbn passwordCheck = new Zxcvbn();
    Strength strength = passwordCheck.measure(form.getPassword());
    if (strength.getScore() < 3) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password is too weak!!");
    }
    Person person = personService.findByConfirmationToken(form.getToken());
    person.setPassword(bCryptPasswordEncoder.encode(form.getPassword()));
    person.setEnabled(true);
    personService.save(person);
    return ResponseEntity.ok("Confirmation process finished!");
  }
}
