package bio.sarat.fastlane.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bio.sarat.fastlane.dto.CreateTagRequest;
import bio.sarat.fastlane.model.Tag;
import bio.sarat.fastlane.repository.TagRepository;

@RestController
@RequestMapping("/api/v1/tags")
@CrossOrigin(origins = "http://localhost:3006")
public class TagController {

  @Autowired
  private TagRepository tagRepository;

  @GetMapping
  public List<Tag> getAllTags() {
    return tagRepository.findAll();
  }

  @GetMapping("/{id}")
  public Tag getTagById(@PathVariable Integer id) {
    return tagRepository.findById(id).get();
  }

  @PostMapping
  public Tag createTag(@RequestBody CreateTagRequest createTagRequest) {
    Tag tag = new Tag();
    tag.setValue(createTagRequest.getValue());
    return tagRepository.save(tag);
  }

  @DeleteMapping("/{id}")
  public void deleteTag(@PathVariable Integer id) {
    tagRepository.deleteById(id);
  }
}
