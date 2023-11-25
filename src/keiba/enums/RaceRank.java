package keiba.enums;

import java.util.Random;

public enum RaceRank {
    OPEN("オープン","OPEN"),
    G3("G3","G3"),
    G2("G2","G2"),
    G1("G1","G1");

    private final String display;

    RaceRank(String display,String rank) {
        this.display = display;
    }

    public static RaceRank getRandomRankType() {
        return values()[new Random().nextInt(values().length)];
    }

    public static RaceRank get(int i) {
        return values()[i];
    }

    @Override
    public String toString() {
        return this.display;
    }
}
