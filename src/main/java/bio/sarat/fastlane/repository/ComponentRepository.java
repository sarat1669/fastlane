package bio.sarat.fastlane.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import bio.sarat.fastlane.model.Component;

public interface ComponentRepository extends JpaRepository<Component, Integer> {

  List<Component> findAllByWorkspaceId(Integer workspaceId);
  
}
