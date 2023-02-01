package bio.sarat.fastlane.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateComponentVersionRequest {
  
  private String inputSchema;
  
  private String metadata;

}
