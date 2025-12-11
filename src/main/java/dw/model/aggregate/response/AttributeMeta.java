package dw.model.aggregate.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class AttributeMeta {
    private int id;
    private int classId;
    private String name;
    private String dataType;
    private String description;
    private AttributeProperties properties;
    private boolean secured;
}
