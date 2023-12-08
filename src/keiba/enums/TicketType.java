package keiba.enums;

public enum TicketType {

    WIN("単勝"),
    PLACE_SHOW("複勝"),
    TWO_HORSE_CONTINUOUS("馬連"),
    TWO_ORDER_OF_ARRIVAL("馬単"),
    THREE_HORSE_CONTINUOUS("三連複"),
    THREE_ORDER_OF_ARRIVAL("三連単");

    private final String display;
    TicketType(String display) {
        this.display = display;
    }
    public String toString() {
        return this.display;
    }
}
