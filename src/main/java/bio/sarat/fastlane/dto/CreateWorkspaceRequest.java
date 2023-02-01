package bio.sarat.fastlane.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateWorkspaceRequest {

  private String name;

  private String description;

  private String metadata;

}
