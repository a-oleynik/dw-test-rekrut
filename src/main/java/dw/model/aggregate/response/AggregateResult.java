package dw.model.aggregate.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonDeserialize(using = AggregateResultDeserializer.class)
public class AggregateResult {
    private ColumnMetadata metadata;
    private List<AggregateValue> values;

    public AggregateValue getFirstAggregateValue() {
        return values
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Aggregate Value is empty"));
    }
}
