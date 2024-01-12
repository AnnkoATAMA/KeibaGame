package keiba.enums;

public enum TicketType {

    WIN("単勝", 1),
    PLACE_SHOW("複勝", 1),
    TWO_HORSE_CONTINUOUS("馬連", 2),
    TWO_ORDER_OF_ARRIVAL("馬単", 2),
    THREE_HORSE_CONTINUOUS("三連複", 3),
    THREE_ORDER_OF_ARRIVAL("三連単", 3);

    private final String display;
    private final int horse;
    TicketType(String display, int horse) {
        this.display = display;
        this.horse = horse;
    }
    public String toString() {
        return this.display;
    }

    public int getHorse() {
        return this.horse;
    }
}
