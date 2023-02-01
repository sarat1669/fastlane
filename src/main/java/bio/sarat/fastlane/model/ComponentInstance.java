package bio.sarat.fastlane.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComponentInstance implements Serializable {
  private String id;

  private String data;

  private Integer priority;

  private Integer componentId;

  private Integer componentVersion;

  private Integer autoDecrementPriorityBy = 1;
  
  private Boolean autoDecrementPriority = false;

  private String metadata;

}
