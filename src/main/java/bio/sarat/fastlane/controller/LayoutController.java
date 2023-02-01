package bio.sarat.fastlane.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.json.JSONObject;
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

import bio.sarat.fastlane.dto.CreateLayoutRequest;
import bio.sarat.fastlane.dto.PreviewLayoutRequest;
import bio.sarat.fastlane.dto.UpdateLayoutRequest;
import bio.sarat.fastlane.dto.Widget;
import bio.sarat.fastlane.model.Component;
import bio.sarat.fastlane.model.ComponentInstance;
import bio.sarat.fastlane.model.Layout;
import bio.sarat.fastlane.repository.ApplicationRepository;
import bio.sarat.fastlane.repository.ComponentRepository;
import bio.sarat.fastlane.repository.LayoutRepository;
import bio.sarat.fastlane.repository.TagRepository;
import bio.sarat.fastlane.util.cursor.Cursor;

@RestController
@RequestMapping("/api/v1/layouts")
@CrossOrigin(origins = "http://localhost:3006")
public class LayoutController {

  @Autowired
  private TagRepository tagRepository;

  @Autowired
  private LayoutRepository layoutRepository;

  @Autowired
  private ApplicationRepository applicationRepository;

  @Autowired
  private ComponentRepository componentRepository;

  @GetMapping
  public List<Layout> getAllLayouts() {
    return layoutRepository.findAll();
  }

  @GetMapping("/{id}")
  public Layout getLayoutById(@PathVariable Integer id) {
    return layoutRepository.findById(id).get();
  }

  @PostMapping
  public Layout createLayout(@RequestBody CreateLayoutRequest createLayoutRequest) {
    Layout layout = new Layout();
    layout.setName(createLayoutRequest.getName());
    layout.setDescription(createLayoutRequest.getDescription());
    layout.setMetadata(createLayoutRequest.getMetadata());
    layout.setApplications(
      applicationRepository.findAllById(createLayoutRequest.getApplicationIds())
    );
    layout.setCohortIds(createLayoutRequest.getCohortIds());
    layout.setDeviceOperatingSystems(createLayoutRequest.getDeviceOperatingSystems());
    layout.setDeviceOrientations(createLayoutRequest.getDeviceOrientations());
    layout.setTags(
      tagRepository.findAllById(createLayoutRequest.getTagIds())
    );
    layout.setComponentInstances(createLayoutRequest.getComponentInstances());
    return layoutRepository.save(layout);
  }

  @PostMapping("/preview")
  public List<Widget> previewLayout(@RequestBody PreviewLayoutRequest previewLayoutRequest) {
    List<String> ids = previewLayoutRequest.getComponentInstances().stream().map(ComponentInstance::getId).collect(Collectors.toList());

    List<Integer> componentIds = previewLayoutRequest.getComponentInstances()
      .stream()
      .map(ComponentInstance::getComponentId).distinct().collect(Collectors.toList());

    Map<Integer, Component> components = componentRepository.findAllById(componentIds)
      .stream()
      .collect(Collectors.toMap(Component::getId, Function.identity()));

    Map<String, ComponentInstance> componentInstances = previewLayoutRequest.getComponentInstances()
      .stream()
      .collect(Collectors.toMap(ComponentInstance::getId, ci -> ci));

    Map<String, Integer> counts = ids
      .stream()
      .collect(Collectors.toMap(Function.identity(), id -> Optional.ofNullable(previewLayoutRequest.getCounts().get(id)).orElse(1)));

    Map<String, Integer> priorities = ids
      .stream()
      .collect(Collectors.toMap(Function.identity(), id -> Optional.ofNullable(componentInstances.get(id).getPriority()).orElse(0)));

    Map<String, Integer> priorityDecrements = ids
      .stream()
      .collect(Collectors.toMap(Function.identity(), id -> componentInstances.get(id).getAutoDecrementPriority() ? componentInstances.get(id).getAutoDecrementPriorityBy() : 0));

    Cursor cursor = new Cursor(counts, priorities, priorityDecrements, ids, 0);

    List<Widget> widgets = new ArrayList<>();

    cursor.forEach((entry) -> {
      Widget widget = new Widget();
      String instanceId = entry.getId();
      ComponentInstance componentInstance = componentInstances.get(instanceId);
      widget.setId(UUID.randomUUID().toString());
      Component component = components.get(componentInstance.getComponentId());
      widget.setType(component.getType());
      widget.setComponentName(component.getName());
      widget.setComponentVersion(componentInstance.getComponentVersion());
      widget.setWorkspace(component.getWorkspace().getName());
      widget.setData(componentInstance.getData());
      widget.setSortSequence(entry.getIndex());
      if (component.getIsRepeatable() != null && component.getIsRepeatable()) {
        Integer index = counts.get(instanceId) - cursor.getCounts().get(instanceId);
        JSONObject params = new JSONObject();
        params.put("index", index);
        widget.setParams(params.toString(0));
      }
      widgets.add(widget);
    });
    
    return widgets;
  }

  @PutMapping("/{id}")
  public Layout updateLayout(@PathVariable("id") Integer id, @RequestBody UpdateLayoutRequest updateLayoutRequest) {
    Layout layout = layoutRepository.findById(id).get();
    layout.setDescription(updateLayoutRequest.getDescription());
    layout.setMetadata(updateLayoutRequest.getMetadata());
    layout.setApplications(
      applicationRepository.findAllById(updateLayoutRequest.getApplicationIds())
    );
    layout.setCohortIds(updateLayoutRequest.getCohortIds());
    layout.setDeviceOperatingSystems(updateLayoutRequest.getDeviceOperatingSystems());
    layout.setDeviceOrientations(updateLayoutRequest.getDeviceOrientations());
    layout.setTags(
      tagRepository.findAllById(updateLayoutRequest.getTagIds())
    );
    layout.setComponentInstances(updateLayoutRequest.getComponentInstances());
    return layoutRepository.save(layout);
  }

  @DeleteMapping("/{id}")
  public void deleteLayout(@PathVariable Integer id) {
    layoutRepository.deleteById(id);
  }
}
