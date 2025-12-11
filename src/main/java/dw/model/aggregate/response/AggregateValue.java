package dw.model.aggregate.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class AggregateValue {
    private AggregateFunction function;
    private Number value;   // here is 69
}
