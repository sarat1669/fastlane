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

import bio.sarat.fastlane.dto.CreateComponentRequest;
import bio.sarat.fastlane.dto.CreateWorkspaceRequest;
import bio.sarat.fastlane.model.Component;
import bio.sarat.fastlane.model.Workspace;
import bio.sarat.fastlane.repository.ComponentRepository;
import bio.sarat.fastlane.repository.WorkspaceRepository;

@RestController
@RequestMapping("/api/v1/workspaces")
@CrossOrigin(origins = "http://localhost:3006")
public class WorkspaceController {

  @Autowired
  private WorkspaceRepository workspaceRepository;

  @Autowired
  private ComponentRepository componentRepository;

  @GetMapping
  public List<Workspace> getAllWorkspaces() {
    return workspaceRepository.findAll();
  }

  @GetMapping("/{id}")
  public Workspace getWorkspaceById(@PathVariable Integer id) {
    return workspaceRepository.findById(id).get();
  }

  @GetMapping("/{id}/components")
  public List<Component> getComponentsByWorkspaceId(@PathVariable Integer id) {
    return componentRepository.findAllByWorkspaceId(id);
  }

  @PostMapping("/{id}/components")
  public Component createWorkspaceComponent(@RequestBody CreateComponentRequest createComponentRequest, @PathVariable Integer id) {
    Component component = new Component();

    component.setName(createComponentRequest.getName());
    component.setDescription(createComponentRequest.getDescription());
    component.setType(createComponentRequest.getType());
    component.setIsRepeatable(createComponentRequest.getIsRepeatable());
    component.setMetadata(createComponentRequest.getMetadata());    
    component.setWorkspace(workspaceRepository.findById(id).get());

    return componentRepository.save(component);
  }

  @PostMapping
  public Workspace createWorkspace(@RequestBody CreateWorkspaceRequest createWorkspaceRequest) {
    Workspace workspace = new Workspace();

    workspace.setName(createWorkspaceRequest.getName());
    workspace.setDescription(createWorkspaceRequest.getDescription());
    workspace.setMetadata(createWorkspaceRequest.getMetadata());

    return workspaceRepository.save(workspace);
  }

  @PutMapping("/{id}")
  public Workspace updateWorkspace(@PathVariable("id") Integer id, @RequestBody Workspace updatedWorkspace) {
    Workspace workspace = workspaceRepository.findById(id).get();

    workspace.setDescription(updatedWorkspace.getDescription());
    workspace.setMetadata(updatedWorkspace.getMetadata());

    return workspaceRepository.save(workspace);
  }

  @DeleteMapping("/{id}")
  public void deleteWorkspace(@PathVariable Integer id) {
    workspaceRepository.deleteById(id);
  }

}
