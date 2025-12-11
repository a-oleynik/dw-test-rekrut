package dw.model.aggregate.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class AggregateFunctionType {
    private String type;                          // "MIN", "MAX", "SUM", "AVG"
}
