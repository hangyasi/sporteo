package com.hangyasi.sporteo.endpoint;

import com.hangyasi.sporteo.dto.Person;
import com.hangyasi.sporteo.repository.PersonRepository;
import com.hangyasi.sporteo.service.PersonService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("persons")
public class PersonEndpoint {

  private Logger logger = LoggerFactory.getLogger(PersonEndpoint.class);

  @Autowired
  private PersonService personService;

  @GetMapping("")
  public List<Person> getPersons() {

    logger.info("List all persons");
    return personService.findAll();
  }

  @PostMapping("")
  public void savePerson(@RequestBody Person person) {
    logger.info("Save person: " + person.toString());
    personService.save(person);
  }

  @GetMapping("/{id}")
  public Person getPerson(@PathVariable("id") Long id) {
    logger.info("Requested person's id: " + id);
    return personService.findById(id);
  }

  @PutMapping("/{id}")
  public void updatePerson(@PathVariable("id") Long id, @RequestBody Person person) {
    logger.info("Updated person's id: " + id);
    Person oldPerson = personService.findById(id);
    BeanUtils.copyProperties(person, oldPerson);
    personService.save(oldPerson);
  }

  @DeleteMapping("/{id}")
  public void deletePerson(@PathVariable("id") Long id) {
    logger.info("Deleted person's id: " + id);
    personService.deleteById(id);
  }

}
