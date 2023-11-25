package keiba.model;

import keiba.enums.HorseType;

public class Horse {
    private final HorseType horse;
    private final double odds;
    private final int number;

    public Horse(HorseType horse, double odds, int number) {
        this.horse = horse;
        this.odds = odds;
        this.number = number;
    }

    @Override
    public String toString() {
        return this.horse == HorseType.CUSTOM ? this.horse.toString() + this.getDisplayNumber() : this.horse.toString();
    }

    public double getOdds() {
        return this.odds;
    }

    public int getNumber() {
        return this.number;
    }

    public int getDisplayNumber() {
        return this.number + 1;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Horse h)) return false;
        return h.number == this.number && h.horse  == this.horse;
    }
}
