package keiba.enums;

import java.util.Random;

public enum RangeType {
    SHORT("短距離",1400),
    MAIL("マイル",1600),
    MEDIUM("中距離",2000),
    LONG("長距離",2600);

    private final String display;
    private final int range;

    RangeType(String display, int range) {
        this.display = display;
        this.range = range;
    }

    public int getRange() {
        return range;
    }

    public static RangeType getRandomRangeType() {
        return values()[new Random().nextInt(values().length)];
    }

    public static RangeType get(int i) {
        return values()[i];
    }

    @Override
    public String toString() {
        return this.display;
    }
}
