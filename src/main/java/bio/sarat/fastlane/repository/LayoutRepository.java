package bio.sarat.fastlane.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bio.sarat.fastlane.model.Layout;


public interface LayoutRepository extends JpaRepository<Layout, Integer> {
  
}
