package dw.model.aggregate.response;

import dw.model.aggregate.AggregateParameters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AggregateResponse {
    private AggregateParameters aggregateParameters;
    private List<List<AggregateResultEntry>> aggregates;
}
