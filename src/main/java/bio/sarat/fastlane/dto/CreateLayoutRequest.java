package bio.sarat.fastlane.dto;

import java.util.List;

import bio.sarat.fastlane.model.ComponentInstance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLayoutRequest {

  private String name;

  private String description;

  private List<Integer> tagIds;

  private List<Integer> cohortIds;

  private List<String> deviceOperatingSystems;

  private List<String> deviceOrientations;

  private List<Integer> applicationIds;

  private List<ComponentInstance> componentInstances;

  private String metadata;
}
