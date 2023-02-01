package bio.sarat.fastlane.dto;

import java.util.List;
import java.util.Map;

import bio.sarat.fastlane.model.ComponentInstance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreviewLayoutRequest {

  private Map<String, Integer> counts;

  private List<ComponentInstance> componentInstances;

}
