package dw.model.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InnerContext {
    private InnerContextLevel context;
    private boolean readOnly;
    private String token;
}
