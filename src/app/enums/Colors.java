package app.enums;

public enum Colors {
    CELL_EMPTY("#00bcd4"),
    CELL_EXIT_HOLE("#cddc39"),
    CELL_TILE("#CCCCCC"),
    HORSE("#607d8b"),
    AVAILABLE_STATE("#00faff"),
    PATH("#d2b48c");

    private final String hexValue;

    Colors(String hexValue) {
        this.hexValue = hexValue;
    }

    public String getHexValue() {
        return hexValue;
    }
}
