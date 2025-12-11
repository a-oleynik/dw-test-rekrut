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
class AttributeProperties {
    private boolean corrupted;
    private SearcherConfig searcher;
    private boolean technical;
    private boolean editable;
    private boolean caseSensitive;
    private DisplayConfig display;
    private boolean sourceObjectId;
    private boolean sourceFileId;
    private StorageConfig storage;
    private boolean enableViewRelatedObjects;
    private boolean required;
    private boolean immutable;
    private boolean inPlaceQuery;
    private List<String> nameMapping;
    private Map<String, Object> decorators;      // {}
    private List<AggregateFunctionType> aggregates; // MIN, MAX, SUM, AVG
    private boolean iconUrl;
    private String alignment;                      // "DEFAULT"
    private boolean calculated;
    private boolean hiddenValues;
    private boolean sourceFileRowNumber;
}
