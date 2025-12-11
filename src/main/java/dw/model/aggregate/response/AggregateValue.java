package dw.model.aggregate.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AggregateValue {
    private FunctionType function;
    private Object value;  // Can be Integer, Double, String, etc.

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FunctionType {
        private String type;  // "MAX", "MIN", "AVG", etc.
    }
}
