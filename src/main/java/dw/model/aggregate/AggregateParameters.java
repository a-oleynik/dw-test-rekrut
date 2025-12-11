package dw.model.aggregate;

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
public class AggregateParameters {
    private Map<String, List<AggregateEntry>> attributeIdToAggregateMap;
    private ContextScope contextScope;
}
