package dw.model;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Session {
    private final String user;
    private final String password;
    private boolean overrideExistingSession;
}
