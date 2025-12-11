package dw.model.aggregate.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class AggregateResultEntry {
    private int id;
    private int classId;
    private String name;
    private String dataType;
    private String description;
    private boolean secured;

    // Column properties - use Map for flexibility since it's deeply nested
    private Map<String, Object> properties;
    private AttributeMeta attribute;           // attribute description (id, name, properties...)
    private List<AggregateValue> values;       // list of aggregate values
}
