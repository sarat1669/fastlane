package bio.sarat.fastlane.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bio.sarat.fastlane.model.Workspace;

public interface WorkspaceRepository extends JpaRepository<Workspace, Integer>{
  
}
