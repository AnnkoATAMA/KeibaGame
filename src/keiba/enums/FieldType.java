package keiba.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum FieldType {
    SHIBA("芝　　"),
    DART("ダ―ト"),
    NONE("無し");

    private final String display;

    FieldType(String display) {
        this.display = display;
    }

    public static FieldType getRandomFieldType() {
        return getRandomFieldType((FieldType) null);
    }

    public static FieldType getRandomFieldType(FieldType... ignore) {
        List<FieldType> fieldTypes = new ArrayList<>(Arrays.stream(values()).toList());
        if (ignore != null) fieldTypes.removeAll(Arrays.asList(ignore));

        return fieldTypes.get(new Random().nextInt(fieldTypes.size()));
    }

    public static FieldType get(int i) {
        return values()[i];
    }

    @Override
    public String toString() {
        return this.display;
    }
}
