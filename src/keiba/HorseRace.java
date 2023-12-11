package keiba;

import keiba.enums.*;
import keiba.model.BoughtTicket;
import keiba.model.Horse;
import keiba.model.RaceType;
import keiba.util.InputUtil;
import keiba.util.RomanNumber;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;


public class HorseRace {
    private static final int FIRST_MONEY = 1000;
    private static final Random random = new Random();
    private static final List<String> condition = new ArrayList<>() {{
        add("良　");
        add("稍重");
        add("重　");
        add("不良");
    }};
    private static final List<String> messages = new ArrayList<>() {{
        add("同着　　　");
        add("ハナ　　　");
        add("クビ　　　");
        add("アタマ　　");
        add("１／２　　");
        add("１　　　　");
        add("１　１／２");
        add("２　　　　");
        add("２　１／２");
        add("３　　　　");
        add("３　１／２");
        add("４　　　　");
        add("５　　　　");
        add("６　　　　");
        add("７　　　　");
        add("８　　　　");
        add("９　　　　");
        add("１０　　　");
        add("大差　　　");
    }};
    private RaceType raceType;
    private int horseAmount;
    private Horse[] horses;
    private double haveMoney;
    private final List<BoughtTicket> boughtTickets = new ArrayList<>();

    // hello
    private HorseRace(RaceType raceType, boolean useRealHorse, double money) {
        if (money <= 0) {
            System.out.println("所持金が尽きたので終了します。");
            System.exit(0);
            return;
        }

        this.raceType = raceType;
        this.horseAmount = random.nextInt(13) + 6;
        this.haveMoney = money;
        this.horses = new Horse[this.horseAmount];

 master
        System.out.println("所持金は" + haveMoney + "です。");

                // 掛け金の選択
                int betOut = InputUtil.getInt("いくら賭けますか", 1, (int) this.haveMoney);
                this.haveMoney -= betOut;
                System.out.println("現在の所持金は" + this.haveMoney + "です。");
                this.boughtTickets.add(BoughtTicket.of(selectedTicketType, selectedHorses, betOut));
                continueBuy = (!InputUtil.getAnswer("さらに馬券を購入しますか？"));
            }while (!continueBuy);
 master

        System.out.println("出走馬は、" + horseAmount + "頭です。");
        createHorses(useRealHorse);

        buyTickets();

        if (raceType == null) raceType = new RaceType(FieldType.getRandomFieldType(FieldType.NONE), RaceRank.getRandomRankType(), RangeType.getRandomRangeType());
        System.out.println("レース概要はこちら\n" + raceType + "\n入場します。");

        resultGame();

        if (!InputUtil.getAnswerByYesNo("ゲームを終わりますか？")) {
            HorseRaceBuilder.create().setSettingByInput().build();
        }
    }

 master
    private HorseRace(RaceType raceType, boolean useRealHorse) {
        this(raceType, useRealHorse, FIRST_MONEY);

    private void startGame() {
        System.out.println("所持金は" + haveMoney + "です。");
        selectRace();

        intHorse = random.nextInt(7) ;
        System.out.println("出走馬は、" + intHorse + "頭です。");
        horses = new Horse[intHorse];
 master
    }

    private void buyTickets() {
        TicketType selectedTicketType;
        boolean continueBuy;
        do {
            do {
                selectedTicketType = InputUtil.getEnumObject("買う馬券の種類を選択してください\n", TicketType.class);
            } while (!InputUtil.getAnswerByYesNo(selectedTicketType + "でよろしいですか？"));

            // かける馬の選択
            List<Horse> selectedHorses = selectBuyHorse(selectedTicketType);

            // 掛け金の選択
            int betOut = InputUtil.getInt("いくら賭けますか", 1, (int) this.haveMoney);
            this.haveMoney -= betOut;
            System.out.println("現在の所持金は" + this.haveMoney + "です。");
            this.boughtTickets.add(BoughtTicket.of(selectedTicketType, selectedHorses, betOut));
            continueBuy = InputUtil.getAnswerByYesNo("さらに馬券を購入しますか？");
            if (continueBuy) {
                selectedTicketType = InputUtil.getEnumObject("新しい馬券の種類を選択してください\n", TicketType.class);
                this.boughtTickets.add(BoughtTicket.of(selectedTicketType, selectedHorses, betOut));
            }
        }while (continueBuy);
    }

    private void createHorses(boolean useRealHorse) {
//        boolean selectRealHorse = InputUtil.getAnswerByYesNo("現実の競走馬を反映しますか？");
//            リアルの競走馬を反映したオッズ作成
        System.out.println("------- 賭ける馬のオッズ -------");
        HorseType[] horseTypes = useRealHorse ?
                HorseType.getRandomHorses(this.horseAmount, this.raceType.getFieldType()) :
                IntStream.range(0, this.horseAmount).mapToObj(i -> HorseType.CUSTOM).toArray(HorseType[]::new);

        double odds;
        for (int i = 0; i < horseAmount; i++) {
            if (i == 0) odds = 1.5;
            else odds = (Math.floor(random.nextDouble(105) * 10 + 16) / 10);

            Horse horse = new Horse(horseTypes[i], odds, i);

            System.out.println(String.format("%-2d", i + 1) + " : "
                    + String.format("%-5s", odds) + " | "
                    + horse);

            this.horses[i] = horse;
            System.out.println("-----------------------------");
        }
    }
    //TODO　oddsによって勝率を変える時は double の odds を int に直してその整数分ランダムして、1が出たら一着？みたいな感じ？

    private List<Horse> selectBuyHorse(TicketType ticketType) {
        List<Horse> buyHorse = new ArrayList<>();
            switch (ticketType) {
                case WIN, PLACE_SHOW -> {
                    do {
                        buyHorse.clear();
                        buyHorse.add(this.horses[InputUtil.getInt("賭ける馬を選んでください（1-" + this.horseAmount + "）", 1, this.horseAmount) - 1]);
                    } while (!InputUtil.getAnswerByYesNo("この馬券でよろしいですか\n" + multiplyOdds(buyHorse)));
                }
                case TWO_HORSE_CONTINUOUS, TWO_ORDER_OF_ARRIVAL -> {
                    do {
                        buyHorse.clear();
                        for (int i = 0; i < 2; i++) {
                            buyHorse.add(this.horses[InputUtil.getInt((i + 1) + "頭目を選んでください（1-" + this.horseAmount + "）", 1, this.horseAmount) - 1]);
                        }
                    } while (!InputUtil.getAnswerByYesNo("この馬券でよろしいですか\n" + multiplyOdds(buyHorse)));
                }
                case THREE_HORSE_CONTINUOUS, THREE_ORDER_OF_ARRIVAL -> {
                    do {
                        buyHorse.clear();
                        for (int i = 0; i < 3; i++) {
                            buyHorse.add(this.horses[InputUtil.getInt((i + 1) + "頭目を選んでください（1-" + this.horseAmount + "）", 1, this.horseAmount) - 1]);
                        }
                    } while (!InputUtil.getAnswerByYesNo("この馬券でよろしいですか\n" + multiplyOdds(buyHorse)));
                }
            }

        return buyHorse;
    }
    
    private double multiplyOdds(List<Horse> horses) {
        BigDecimal result = new BigDecimal(1);
        for (Horse horse: horses) {
            result = result.multiply(BigDecimal.valueOf(horse.getOdds()));
        }
        return result.doubleValue();
    }


    private boolean isTicketWin(BoughtTicket ticket, List<Horse> result) {
        return switch (ticket.getTicketType()) {
 master
            case WIN -> result.get(0).equals(ticket.getSelectedHorses().get(0));
            case PLACE_SHOW -> ticket.getSelectedHorses().contains(result.get(0)) || ticket.getSelectedHorses().contains(result.get(1));

            case SINGLEWIN -> ticket.getSelectedHorses().contains(result.get(0));
            case MULTIPLEWINS -> ticket.getSelectedHorses().contains(result.get(0)) || ticket.getSelectedHorses().contains(result.get(1));
 master

            case TWO_HORSE_CONTINUOUS -> ticket.getSelectedHorses().get(0).getDisplayNumber() == result.get(0).getDisplayNumber()
                    && ticket.getSelectedHorses().get(1).getDisplayNumber() == result.get(1).getDisplayNumber()
                || (ticket.getSelectedHorses().get(0).getDisplayNumber() == result.get(1).getDisplayNumber()
                    && ticket.getSelectedHorses().get(1).getDisplayNumber() == result.get(0).getDisplayNumber());
            case TWO_ORDER_OF_ARRIVAL ->ticket.getSelectedHorses().get(0).getDisplayNumber() == result.get(0).getDisplayNumber()
                    && ticket.getSelectedHorses().get(1).getDisplayNumber() == result.get(1).getDisplayNumber()
                && (ticket.getSelectedHorses().get(0).getDisplayNumber() == result.get(1).getDisplayNumber()
                    && ticket.getSelectedHorses().get(1).getDisplayNumber() == result.get(0).getDisplayNumber());
            case THREE_HORSE_CONTINUOUS -> false;
            case THREE_ORDER_OF_ARRIVAL -> false;
        };
    }
 master

    private double resultMoney(BoughtTicket ticket, List<Horse> result) {
        if (isTicketWin(ticket, result)) {

            double odds = multiplyOdds(ticket.getSelectedHorses());
            double winnings = ticket.getBet() * odds;

            haveMoney += winnings;

            System.out.println("おめでとうございます！配当は" + winnings + "です！");
        } else {

            System.out.println("あ～まけた...");

    private double resultMoney(List<BoughtTicket> tickets, List<Horse> result) {
        for (BoughtTicket ticket : tickets) {
            if (isTicketWin(ticket, result)) {
                double odds = multiplyOdds(ticket.getSelectedHorses());
                double winnings = ticket.getBet() * odds;
                haveMoney += winnings;
                System.out.println("おめでとうございます！配当は" + winnings + "です！");
            } else {

                System.out.println("あ～まけた...");
            }
 master
        }


        return haveMoney;
    }

    private void resultGame() {
        List<Horse> result = Arrays.asList(this.horses.clone());

        // リストをシャッフルし、結果を作成
        Collections.shuffle(result);

        System.out.println("1着は" + (result.get(0).getDisplayNumber()) + "番です！！！");
 master
        haveMoney = resultMoney(boughtTickets.get(0), result);

        haveMoney = resultMoney(boughtTickets,result);
 master
        System.out.println("現在の所持金は" + haveMoney + "です。");
        System.out.println("＿＿＿＿＿＿＿＿＿＿＿");
        System.out.println("|小倉 " + ColorCode.YELLOW + random.nextInt(1,13) + ColorCode.END + "R" + " 　 " + ColorCode.RED_BG + " 確定 " + ColorCode.END + "|");
        System.out.println("| " + "(" + ColorCode.BLUE + "I" + ColorCode.END + ")" + ColorCode.YELLOW + String.format("%-2d", result.get(0).getDisplayNumber()) + " " + ColorCode.END + "　　　　　|");
        for (int i = 2; i < 6; i++) {
            System.out.println("| " + "(" + ColorCode.BLUE + RomanNumber.getRomanNumber(i) + ColorCode.END + ")" + ColorCode.YELLOW + String.format("%-2d", result.get(i - 1).getDisplayNumber()) + " " + messages.get(i) + ColorCode.END + "|");
        }

        System.out.println("|  " + FieldType.SHIBA + "　　 　　 " + "|");
        System.out.println("|  " + ColorCode.YELLOW + condition.get(random.nextInt(condition.size())) + ColorCode.END + String.format("%-10s", " ") + "|");
        System.out.println("| " + FieldType.DART + "　　　　　　" + "|");
        System.out.println("|  " + ColorCode.YELLOW + condition.get(random.nextInt(condition.size())) + ColorCode.END + String.format("%-10s", " ") + "|");
        System.out.println("￣￣￣￣￣￣￣￣￣￣￣");

    }

    public static class HorseRaceBuilder {
        private boolean realHorse = false;
        private RaceType raceType = null;

        private HorseRaceBuilder() {}

        public static HorseRaceBuilder create() {
            return new HorseRaceBuilder();
        }

        public HorseRaceBuilder setUseRealHorse(boolean use) {
            this.realHorse = use;

            return this;
        }

        public HorseRaceBuilder setRaceType(RaceType raceType) {
            this.raceType = raceType;

            return this;
        }

        public HorseRaceBuilder setSettingByInput() {
            if (InputUtil.getAnswerByYesNo("レースを選びますか？")) {
                final FieldType selectFiled = InputUtil.getEnumObject(FieldType.class, FieldType.NONE);
                final RaceRank selectRank = InputUtil.getEnumObject(RaceRank.class);
                final RangeType selectRange = InputUtil.getEnumObject(RangeType.class);

                System.out.println("レース概要はこちら\n" + selectFiled + "\n" + selectRank + "\n" + selectRange + "\n入場します。");
                this.raceType = new RaceType(selectFiled, selectRank, selectRange);
            }

            this.realHorse = InputUtil.getAnswerByYesNo("実際の競走馬を反映させますか？");

            return this;
        }

        public HorseRace build() {
            if (this.raceType == null) {
                this.raceType = new RaceType(FieldType.getRandomFieldType(FieldType.NONE), RaceRank.getRandomRankType(), RangeType.getRandomRangeType());
            }

            return new HorseRace(raceType, realHorse);
        }
    }
}
//TODO　リザルトマネーは馬券の数によってループする必要もあるし呼び出したとき今はget(0)で一番最初だけを参照してるから全部見るようにさせるで見て当たった馬券の数金額が増えるようにする
