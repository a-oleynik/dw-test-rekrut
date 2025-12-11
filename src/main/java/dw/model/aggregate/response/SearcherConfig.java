package dw.model.aggregate.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class SearcherConfig {
    private String type;                          // "NUMERIC"
    private Map<String, Object> config;           // empty object {}
}
