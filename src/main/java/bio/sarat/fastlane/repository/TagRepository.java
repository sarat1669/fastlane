package bio.sarat.fastlane.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bio.sarat.fastlane.model.Tag;

public interface TagRepository  extends JpaRepository<Tag, Integer> {
  
}
