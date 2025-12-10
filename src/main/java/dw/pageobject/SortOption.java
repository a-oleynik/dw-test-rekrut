package dw.pageobject;

import java.util.Arrays;
import java.util.Optional;

public enum SortOption {

    ASC("eSortAsc"), DESC("eSortDesc"), NONE("eSortNone");
    private final String ref;

    SortOption(String ref) {
        this.ref = ref;
    }

    public static Optional<SortOption> fromRef(String ref) {
        if (ref == null) return Optional.empty();
        return Arrays.stream(values())
                .filter(opt -> opt.ref.equals(ref))
                .findFirst();
    }

}
