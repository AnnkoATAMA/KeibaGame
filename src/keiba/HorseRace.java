package keiba;

import keiba.enums.*;
import keiba.model.BoughtTicket;
import keiba.model.Horse;
import keiba.model.RaceType;
import keiba.util.InputUtil;
import keiba.enums.RomanNumber;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;


public class HorseRace {
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
    private final RaceType raceType;
    private final int horseAmount;
    private final Horse[] horses;
    private double haveMoney;
    private final List<BoughtTicket> boughtTickets = new ArrayList<>();


    private HorseRace(RaceType raceType, boolean useRealHorse, double money) {
        this.raceType = raceType;
        this.horseAmount = random.nextInt(13) + 6;
        this.haveMoney = money;
        this.horses = new Horse[this.horseAmount];

        System.out.println("所持金は" + haveMoney + "です。");

        if (raceType == null) {
            raceType = new RaceType(FieldType.getRandomFieldType(FieldType.NONE),
                    RaceRank.getRandomRankType(),
                    RangeType.getRandomRangeType());
        }

        System.out.println("レース概要はこちら\n" + raceType + "\n入場します。");

        System.out.println("出走馬は、" + horseAmount + "頭です。");
        createHorses(useRealHorse);

        buyTickets();

        resultGame();

        if (this.haveMoney <= 0) {
            System.out.println("所持金が尽きたので競馬場を出ます。");
            System.out.println("優しい企業がお金を貸してくれるようです。");
            boolean ans;
            ans =InputUtil.getAnswer("お金を借りますか？",false);
            if(ans) {
                haveMoney+=1000;
                System.out.println(ColorCode.RED + "1000貸してもらいました");
                HorseRaceBuilder.create()
                        .setSettingByInput()
                        .setMoney(this.haveMoney)
                        .build();
            } else {
                System.exit(0);
            }

        } else if (InputUtil.getAnswer("ゲームを続けますか？", true)) {
            HorseRaceBuilder.create()
                    .setSettingByInput()
                    .setMoney(this.haveMoney)
                    .build();
        }
    }

    private void buyTickets() {
        TicketType selectedTicketType;
        do {
            do {
                selectedTicketType = InputUtil.getEnumObject("買う馬券の種類を選択してください\n", TicketType.class);
            } while (!InputUtil.getAnswer(selectedTicketType + "でよろしいでしょうか", true));

            // かける馬の選択
            List<Horse> selectedHorses = selectBuyTicket(selectedTicketType);

            // 掛け金の選択
            int betOut = InputUtil.getInt("いくら賭けますか", 1, (int) this.haveMoney);
            this.haveMoney -= betOut;
            System.out.println("現在の所持金は" + this.haveMoney + "です。");
            this.boughtTickets.add(BoughtTicket.of(selectedTicketType, selectedHorses, betOut));
        } while (this.haveMoney > 0 && InputUtil.getAnswer("さらに馬券を購入しますか？",true));
    }

    private void createHorses(boolean useRealHorse) {

        // リアルの競走馬を反映したオッズ作成
        System.out.println("------- 賭ける馬のオッズ -------");
        HorseType[] horseTypes = useRealHorse ?
                HorseType.getRandomHorses(this.horseAmount, this.raceType.getFieldType()) :
                IntStream.range(0, this.horseAmount).mapToObj(i -> HorseType.CUSTOM).toArray(HorseType[]::new);
        //一定数を超えた場合odds抽選の最大値をアップする
        double odds = 400;
        if (horseAmount < 11) {
            odds = 250;
        } else if (horseAmount < 19) {
            odds = 300;
        }
        //randomOddsに代入してランダムにオッズを出す
        double randomOdds = odds;
        for (int i = 0; i < horseAmount; i++) {
            if(randomOdds < 2) {
                randomOdds += 100;
            }

            odds = (Math.floor(random.nextDouble(randomOdds) * 10) / 10);
            randomOdds -= odds;
            Horse horse = new Horse(horseTypes[i], odds, i);
            odds += 1;

            System.out.println(String.format("%-2d", i + 1) + " : "
                    + String.format("%-5s", odds) + " | "
                    + horse);
            this.horses[i] = horse;
            System.out.println("-----------------------------");
        }
    }


    private List<Horse> selectBuyTicket(TicketType ticketType) {
        List<Horse> buyHorse = new ArrayList<>();
            switch (ticketType) {
                case WIN, PLACE_SHOW -> {
                    do {
                        buyHorse.clear();
                        buyHorse.add(this.horses[InputUtil.getInt("賭ける馬を選んでください（1-" + this.horseAmount + "）", 1, this.horseAmount) - 1]);
                    } while (!InputUtil.getAnswer("この馬券でよろしいですか [" + multiplyOdds(buyHorse) + "]",true));
                }
                case TWO_HORSE_CONTINUOUS, TWO_ORDER_OF_ARRIVAL -> {
                    do {
                        buyHorse.clear();
                        for (int i = 0; i < 2; i++) {
                            buyHorse.add(this.horses[InputUtil.getInt((i + 1) + "頭目を選んでください（1-" + this.horseAmount + "）", 1, this.horseAmount) - 1]);
                        }
                    } while (!InputUtil.getAnswer("この馬券でよろしいですか [" + multiplyOdds(buyHorse) + "]", true));
                }
                case THREE_HORSE_CONTINUOUS, THREE_ORDER_OF_ARRIVAL -> {
                    do {
                        buyHorse.clear();
                        for (int i = 0; i < 3; i++) {
                            buyHorse.add(this.horses[InputUtil.getInt((i + 1) + "頭目を選んでください（1-" + this.horseAmount + "）", 1, this.horseAmount) - 1]);
                        }
                    } while (InputUtil.getAnswer("この馬券でよろしいですか [" + multiplyOdds(buyHorse) + "]", true));
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
            case WIN -> result.get(0).equals(ticket.getSelectedHorses().get(0));
            case PLACE_SHOW -> ticket.getSelectedHorses().contains(result.get(0)) || ticket.getSelectedHorses().contains(result.get(1));
            case TWO_HORSE_CONTINUOUS, THREE_HORSE_CONTINUOUS -> {
                boolean win = true;

                List<Horse> collect = result.subList(0, ticket.getTicketType().getHorse());
                for (int i = 0; i < ticket.getTicketType().getHorse(); i++) {
                    if (!win) break;

                    win = collect.contains(ticket.getSelectedHorses().get(i));
                }

                yield win;
            }
            case TWO_ORDER_OF_ARRIVAL, THREE_ORDER_OF_ARRIVAL -> IntStream.range(0,ticket.getTicketType().getHorse())
                    .filter(i -> result.get(i).equals(ticket.getSelectedHorses().get(i))).count() == ticket.getTicketType().getHorse();
        };
    }

    private double resultMoney(List<BoughtTicket> tickets, List<Horse> result) {
        for (BoughtTicket ticket : tickets) {

            if (isTicketWin(ticket, result)) {

                double odds = multiplyOdds(ticket.getSelectedHorses());
                double winnings = ticket.getBet() * odds;

                haveMoney += winnings;

                System.out.println("おめでとうございます！配当は" + winnings + "です！");
            } else {
                System.out.println("あーまけた");
            }
        }
            return haveMoney;
    }


    private void resultGame() {
        List<Horse> result = Arrays.asList(this.horses.clone());

        // リストをシャッフルし、結果を作成
        Collections.shuffle(result);

        System.out.println("1着は" + (result.get(0).getDisplayNumber()) + "番です！！！");
        haveMoney = resultMoney(boughtTickets,result);

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
        private double money = 1000;

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

        public HorseRaceBuilder setMoney(double money) {
            this.money = money;

            return this;
        }

        public HorseRaceBuilder setSettingByInput() {
            if (InputUtil.getAnswer("レースを選びますか？", false)) {
                final FieldType selectFiled = InputUtil.getEnumObject(FieldType.class, FieldType.NONE);
                final RaceRank selectRank = InputUtil.getEnumObject(RaceRank.class);
                final RangeType selectRange = InputUtil.getEnumObject(RangeType.class);

                System.out.println("レース概要はこちら\n" + selectFiled + "\n" + selectRank + "\n" + selectRange + "\n入場します。");
                this.raceType = new RaceType(selectFiled, selectRank, selectRange);
            }

            this.realHorse = InputUtil.getAnswer("実際の競走馬を反映させますか？", true);

            return this;
        }

        public HorseRace build() {
            if (this.raceType == null) {
                this.raceType = new RaceType(FieldType.getRandomFieldType(FieldType.NONE), RaceRank.getRandomRankType(), RangeType.getRandomRangeType());
            }

            return new HorseRace(raceType, realHorse, money);
        }
    }
}
