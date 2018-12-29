package com.hangyasi.sporteo.service;

import com.hangyasi.sporteo.dto.Person;
import com.hangyasi.sporteo.repository.PersonRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

  private PersonRepository personRepository;

  @Autowired
  public PersonService(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  public Person findById(Long id) {
    return personRepository.findById(id).get();
  }

  public Person findByEmail(String email) {
    return personRepository.findByEmail(email);
  }

  public Person findByConfirmationToken(String confirmationToken) {
    return personRepository.findByConfirmationToken(confirmationToken);
  }

  public List<Person> findAll() {
    return personRepository.findAll();
  }

  public void save(Person person) {
    personRepository.save(person);
  }

  public void deleteById(Long id) {
    personRepository.deleteById(id);
  }

}
