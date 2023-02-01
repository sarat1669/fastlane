package bio.sarat.fastlane.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import bio.sarat.fastlane.model.ComponentVersion;

public interface ComponentVersionRepository extends JpaRepository<ComponentVersion, Integer> {
  List<ComponentVersion> findByComponentId(Integer componentId);

  ComponentVersion findByComponentIdAndVersion(Integer componentId, Integer version);

  ComponentVersion findTopByComponentIdOrderByVersionDesc(Integer componentId);

  void deleteByComponentIdAndVersion(Integer componentId, Integer version);
}
