package bio.sarat.fastlane.dto;

import bio.sarat.fastlane.model.Component.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Widget {

  private String id;

  private Type type;

  private String componentName;

  private Integer componentVersion;

  private String workspace;

  private String data;

  private String params;

  private Integer sortSequence;

}
