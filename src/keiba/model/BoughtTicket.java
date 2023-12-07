package keiba.model;

import keiba.enums.TicketType;
import java.util.List;

public class BoughtTicket {
    private final TicketType ticketType;
    private final List<Horse> selectedHorses;
    private final int bet;

    public BoughtTicket(TicketType ticketType, List<Horse> selectedHorses, int bet) {
        this.ticketType = ticketType;
        this.selectedHorses = selectedHorses;
        this.bet = bet;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public int getBet() {
        return bet;
    }

    public List<Horse> getSelectedHorses() {
        return selectedHorses;
    }


    public static BoughtTicket of(TicketType ticketType, List<Horse> selectedHorses, int bet) {
        return new BoughtTicket(ticketType, selectedHorses, bet);
    }
}
