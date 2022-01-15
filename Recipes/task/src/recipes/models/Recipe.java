package recipes.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @NotBlank
    String name;

    @NotBlank
    String description;

    @ElementCollection
    @NotEmpty
    List<String> ingredients;

    @ElementCollection
    @NotEmpty
    List<String> directions;

    @NotBlank
    String category;

    @LastModifiedDate
    @CreationTimestamp
    LocalDateTime date;

    @CreatedBy
    @JsonIgnore
    String createdBy;

}