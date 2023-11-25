package keiba.model;

import keiba.enums.FieldType;
import keiba.enums.RaceRank;
import keiba.enums.RangeType;

public class RaceType {
    private final FieldType fieldType;
    private final RaceRank raceRank;
    private final RangeType rangeType;

    public RaceType(FieldType fieldType, RaceRank raceRank, RangeType rangeType) {
        this.fieldType = fieldType;
        this.raceRank = raceRank;
        this.rangeType = rangeType;
    }

    public FieldType getFieldType() {
        return this.fieldType;
    }

    public RaceRank getRaceRank() {
        return this.raceRank;
    }

    public RangeType getRangeType() {
        return this.rangeType;
    }

    @Override
    public String toString() {
        return "field:" + this.fieldType + "\nrank:" + this.raceRank + "\nrange:" + this.rangeType;
    }
}
