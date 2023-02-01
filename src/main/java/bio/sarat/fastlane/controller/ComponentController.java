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

import bio.sarat.fastlane.dto.ComponentVersionResponse;
import bio.sarat.fastlane.dto.ComponentVersionsResponse;
import bio.sarat.fastlane.dto.CreateComponentVersionRequest;
import bio.sarat.fastlane.dto.UpdateComponentRequest;
import bio.sarat.fastlane.dto.UpdateComponentVersionRequest;
import bio.sarat.fastlane.model.Component;
import bio.sarat.fastlane.model.ComponentVersion;
import bio.sarat.fastlane.repository.ComponentRepository;
import bio.sarat.fastlane.repository.ComponentVersionRepository;

@RestController
@RequestMapping("/api/v1/components")
@CrossOrigin(origins = "http://localhost:3006")
public class ComponentController {
  
  @Autowired
  private ComponentRepository componentRepository;

  @Autowired
  private ComponentVersionRepository componentVersionRegistry;

  @GetMapping
  public List<Component> getAllComponents() {
    return componentRepository.findAll();
  }

  @GetMapping("/{id}")
  public Component getComponentById(@PathVariable Integer id) {
    return componentRepository.findById(id).get();
  }

  @PutMapping("/{id}")
  public void updateComponent(@PathVariable Integer id, @RequestBody UpdateComponentRequest updateComponentRequest) {
    Component currentComponent = componentRepository.findById(id).get();
    currentComponent.setDescription(updateComponentRequest.getDescription());
    currentComponent.setMetadata(updateComponentRequest.getMetadata());
    componentRepository.deleteById(id);
  }

  @DeleteMapping("/{id}")
  public void deleteComponent(@PathVariable Integer id) {
    componentRepository.deleteById(id);
  }

  @GetMapping("/{id}/versions")
  public ComponentVersionsResponse getComponentVersionsById(@PathVariable Integer id) {
    return new ComponentVersionsResponse(
      componentRepository.findById(id).get(),
      componentVersionRegistry.findByComponentId(id)
    );
  }

  @GetMapping("/{id}/versions/{version}")
  public ComponentVersionResponse getComponentVersionByIdAndVersion(@PathVariable Integer id, @PathVariable Integer version) {
    return new ComponentVersionResponse(
      componentRepository.findById(id).get(),
      componentVersionRegistry.findByComponentIdAndVersion(id, version)
    );
  }

  @PostMapping("/{id}/versions")
  public ComponentVersionResponse createComponentVersion(@PathVariable Integer id, @RequestBody CreateComponentVersionRequest createComponentVersionRequest) {
    ComponentVersion latestComponentVersion = componentVersionRegistry.findTopByComponentIdOrderByVersionDesc(id);
    Integer nextVersion = latestComponentVersion == null ? 1 : latestComponentVersion.getVersion() + 1;
    
    ComponentVersion componentVersion = new ComponentVersion();

    componentVersion.setInputSchema(createComponentVersionRequest.getInputSchema());
    componentVersion.setMetadata(createComponentVersionRequest.getMetadata());
    componentVersion.setComponent(componentRepository.findById(id).get());
    componentVersion.setVersion(nextVersion);

    componentVersionRegistry.save(componentVersion);

    return getComponentVersionByIdAndVersion(id, nextVersion);
  }

  @PutMapping("/{id}/versions/{version}")
  public ComponentVersionResponse updateComponentVersion(@PathVariable Integer id, @PathVariable Integer version, @RequestBody UpdateComponentVersionRequest updateComponentVersionRequest) {
    ComponentVersion currentComponentVersion = componentVersionRegistry.findByComponentIdAndVersion(id, version);

    currentComponentVersion.setMetadata(updateComponentVersionRequest.getMetadata());
    currentComponentVersion.setInputSchema(updateComponentVersionRequest.getInputSchema());

    return new ComponentVersionResponse(
      componentRepository.findById(id).get(),
      componentVersionRegistry.save(currentComponentVersion)
    );
  }

  @DeleteMapping("/{id}/versions/{version}")
  public void deleteComponentVersion(@PathVariable Integer id, @PathVariable Integer version) {
    componentVersionRegistry.deleteByComponentIdAndVersion(id, version);
  }

}
