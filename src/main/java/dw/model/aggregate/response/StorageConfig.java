package dw.model.aggregate.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class StorageConfig {
    private String STORAGE_GROUP;
    private boolean STORAGE_FILL_NULLS;
    private boolean STORAGE_USE_ORIGS;
    private boolean STORAGE_USE_UPDATE_BY_INSERT;
    private boolean STORAGE_USE_IMPORT_ID;
    private String STORAGE_MODE;                  // "SEPARATED"
}
