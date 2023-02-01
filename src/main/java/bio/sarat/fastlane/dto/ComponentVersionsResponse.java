package bio.sarat.fastlane.dto;

import java.util.List;

import bio.sarat.fastlane.model.Component;
import bio.sarat.fastlane.model.ComponentVersion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComponentVersionsResponse {
  private Component component;

  private List<ComponentVersion> versions;
}
