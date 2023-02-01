package bio.sarat.fastlane.dto;

import bio.sarat.fastlane.model.Component;
import bio.sarat.fastlane.model.ComponentVersion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComponentVersionResponse {
  private Component component;

  private ComponentVersion version;
}
