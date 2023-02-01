package bio.sarat.fastlane.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "layouts")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Layout {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "description")
  private String description;

  @OneToMany
  private List<Tag> tags;

  @Column(name = "cohortIds")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<Integer> cohortIds;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "deviceOperatingSystems")
  private List<String> deviceOperatingSystems;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "deviceOrientations")
  private List<String> deviceOrientations;

  @OneToMany
  private List<Application> applications;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "componentInstances")
  private List<ComponentInstance> componentInstances;
  
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "metadata")
  private String metadata;
  
  @Column(name = "created_by")
  private String createdBy;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime updatedAt;

}
