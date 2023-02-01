package bio.sarat.fastlane.controller;

import java.util.List;

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

import bio.sarat.fastlane.dto.CreateApplicationRequest;
import bio.sarat.fastlane.dto.UpdateApplicationRequest;
import bio.sarat.fastlane.model.Application;
import bio.sarat.fastlane.repository.ApplicationRepository;

@RestController
@RequestMapping("/api/v1/applications")
@CrossOrigin(origins = "http://localhost:3006")
public class ApplicationController {

  @Autowired
  private ApplicationRepository applicationRepository;

  @GetMapping
  public List<Application> getAllApplications() {
    return applicationRepository.findAll();
  }

  @GetMapping("/{id}")
  public Application getApplicationById(@PathVariable Integer id) {
    return applicationRepository.findById(id).get();
  }

  @PostMapping()
  public Application createApplication(@RequestBody CreateApplicationRequest createApplicationRequest) {
    Application application = new Application();
    application.setName(createApplicationRequest.getName());
    application.setDescription(createApplicationRequest.getDescription());
    application.setMetadata(createApplicationRequest.getMetadata());
    return applicationRepository.save(application);
  }

  @PutMapping("/{id}")
  public Application updateApplication(@PathVariable("id") Integer id, @RequestBody UpdateApplicationRequest updateApplicationRequest) {
    Application application = applicationRepository.findById(id).get();
    application.setDescription(updateApplicationRequest.getDescription());
    application.setMetadata(updateApplicationRequest.getMetadata());
    return applicationRepository.save(application);
  }

  @DeleteMapping("/{id}")
  public void deleteApplication(@PathVariable Integer id) {
    applicationRepository.deleteById(id);
  }

}
