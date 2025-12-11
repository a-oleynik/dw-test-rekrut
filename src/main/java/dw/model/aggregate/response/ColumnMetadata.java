package dw.model.aggregate.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ColumnMetadata {
    private int id;
    private int classId;
    private String name;
    private String dataType;
    private String description;
    private Map<String, Object> properties;
    private boolean secured;
}
