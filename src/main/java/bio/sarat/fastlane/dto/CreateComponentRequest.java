package bio.sarat.fastlane.dto;

import bio.sarat.fastlane.model.Component.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateComponentRequest {

  private String name;

  private String description;

  private Type type;

  private Boolean isRepeatable;

  private String metadata;

}
