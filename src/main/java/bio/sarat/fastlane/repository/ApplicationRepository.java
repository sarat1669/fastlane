package bio.sarat.fastlane.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bio.sarat.fastlane.model.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
  
}
