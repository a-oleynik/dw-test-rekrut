package dw.model.aggregate.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dw.model.aggregate.AggregateParameters;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AggregateResponse {
    private AggregateParameters aggregateParameters;
    private List<AggregateResult> aggregates;

    public AggregateResult getFirstAggregateResult() {
        return aggregates
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Aggregates list is empty"));
    }
}
