package keiba;

import keiba.enums.*;
import keiba.model.BoughtTicket;
import keiba.model.Horse;
import keiba.model.RaceType;
import keiba.util.InputUtil;

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
    private int intHorse;
    private Horse[] horses;
    private double haveMoney;
    private int selected;

    private double betOut;
    private final List<BoughtTicket> boughtTickets = new ArrayList<>();

    // コンストラクタについて知ろう！
    public HorseRace() {
        haveMoney = FIRST_MONEY;
        do {
            startGame();
            selectOdds();


            // 買う馬券を選択
            TicketType selectedTicketType;
            do {
                selectedTicketType = InputUtil.getEnumObject("買う馬券の種類を選択してください", TicketType.class);
            } while (InputUtil.getAnswer(selectedTicketType + "でよろしいですか？"));
            // かける馬の選択
            List<Horse> selectedHorses = selectBuyHorse(selectedTicketType);

            // 掛け金の選択
            int betOut = InputUtil.getInt("いくら賭けますか", 1, (int) this.haveMoney);
            betMoney(betOut);

            // 買った馬券の種類、掛け金、選択した馬を保存
            this.boughtTickets.add(BoughtTicket.of(selectedTicketType, selectedHorses, betOut));


            resultGame();
            if (haveMoney <= 0) {
                System.out.println("ゲームを終了します。");
                break;
            }
            boolean gameEnd = InputUtil.getAnswer("ゲームを終わりますか？");

            if (gameEnd) {
                System.out.println("ゲームを終了します。");
                break;
            }
        } while (haveMoney > 0);
    }

    private void startGame() {
        System.out.println("所持金は" + haveMoney + "です。");
        selectRace();

        intHorse = random.nextInt(13) + 6;
        System.out.println("出走馬は、" + intHorse + "頭です。");
        horses = new Horse[intHorse];
    }

    private void selectRace() {
        boolean selectMode = InputUtil.getAnswer("レースを選びますか？");
        if (selectMode) {
//            選択型
            final FieldType selectFiled = InputUtil.getEnumObject(FieldType.class, FieldType.NONE);
            final RaceRank selectRank = InputUtil.getEnumObject(RaceRank.class);
            final RangeType selectRange = InputUtil.getEnumObject(RangeType.class);

            System.out.println("レース概要はこちら\n" + selectFiled + "\n" + selectRank + "\n" + selectRange + "\n入場します。");
            raceType = new RaceType(selectFiled, selectRank, selectRange);

        } else {
            // ランダムに生成する
            raceType = new RaceType(FieldType.getRandomFieldType(FieldType.NONE), RaceRank.getRandomRankType(), RangeType.getRandomRangeType());
            System.out.println("レース概要はこちら\n" + raceType + "\n入場します。");
        }
    }

    private void selectOdds() {
        boolean selectRealHorse = InputUtil.getAnswer("現実の競走馬を反映しますか？");
//            リアルの競走馬を反映したオッズ作成
        System.out.println("------- 賭ける馬のオッズ -------");
        HorseType[] horseTypes = selectRealHorse ?
                HorseType.getRandomHorses(this.intHorse, this.raceType.getFieldType()) :
                IntStream.range(0, this.intHorse).mapToObj(i -> HorseType.CUSTOM).toArray(HorseType[]::new);

        double odds;
        for (int i = 0; i < intHorse; i++) {
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
        // どの馬にかけますか
        int selectOdds = 0;
        selectOdds = InputUtil.getInt("賭ける馬を選んでください（1-" + this.intHorse + "）", 1, this.intHorse) - 1;

        List<Horse> buyHorse = new ArrayList<>();
        switch (ticketType) {
            case SINGLEWIN, MULTIPLEWINS -> {
                do {
                    buyHorse.add(this.horses[InputUtil.getInt("賭ける馬を選んでください（1-" + this.intHorse + "）", 1, this.intHorse) - 1]);
                } while (!InputUtil.getAnswer("この馬券でよろしいですか\n" + multiplyOdds(buyHorse)));
            }
            case TWO_HORSE_CONTINUOUS, TWO_ORDER_OF_ARRIVAL -> {
                do {
                    for (int i = 0; i < 2; i++) {
                        buyHorse.add(this.horses[InputUtil.getInt((i+1) + "頭目を選んでください（1-" + this.intHorse +"）", 1, this.intHorse) - 1]);
                    }
                } while (!InputUtil.getAnswer("この馬券でよろしいですか\n" + multiplyOdds(buyHorse)));
            }
            case THREE_HORSE_CONTINUOUS, THREE_ORDER_OF_ARRIVAL-> {
                do {
                    for (int i = 0; i < 3; i++) {
                        buyHorse.add(this.horses[InputUtil.getInt((i+1) + "頭目を選んでください（1-" + this.intHorse + "）", 1, this.intHorse) - 1]);
                    }
                } while (!InputUtil.getAnswer("この馬券でよろしいですか\n" + multiplyOdds(buyHorse)));
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

    private void betMoney(int money){
        this.haveMoney -= betOut;
        System.out.println("現在の所持金は" + this.haveMoney + "です。");
    }

    private boolean isTicketWin(BoughtTicket ticket, List<Horse> result) {
        return switch (ticket.getTicketType()) {
            case SINGLEWIN -> result.get(0).equals(ticket.getSelectedHorses().get(0));
            case MULTIPLEWINS -> false;
            case TWO_HORSE_CONTINUOUS -> false;
            case TWO_ORDER_OF_ARRIVAL -> false;
            case THREE_HORSE_CONTINUOUS -> false;
            case THREE_ORDER_OF_ARRIVAL -> false;
        };
    }

    // いくらかけるか
    private void resultGame() {

        double selectOdds = this.horses[this.selected].getOdds();
        List<Horse> result = Arrays.asList(this.horses.clone());

        Collections.shuffle(result);

        System.out.println("1着は" + (result.get(0).getDisplayNumber()) + "番です！！！");

        System.out.println("＿＿＿＿＿＿＿＿＿＿＿");
        System.out.println("|小倉 " + ColorCode.YELLOW + random.nextInt(1,13) + ColorCode.END + "R" + " 　 " + ColorCode.RED_BG + " 確定 " + ColorCode.END + "|");
        System.out.println("| " + "(" + ColorCode.BLUE + "1" + ColorCode.END + ")" + ColorCode.YELLOW + String.format("%-2d", result.get(0).getDisplayNumber()) + " " + ColorCode.END + "　　　　　|");
        for (int i = 2; i < 6; i++) {
            System.out.println("| " + "(" + ColorCode.BLUE + i + ColorCode.END + ")" + ColorCode.YELLOW + String.format("%-2d", result.get(i - 1).getDisplayNumber()) + " " + messages.get(i) + ColorCode.END + "|");
        }

        System.out.println("|  " + FieldType.SHIBA + "　　 　　 " + "|");
        System.out.println("|  " + ColorCode.YELLOW + condition.get(random.nextInt(condition.size())) + ColorCode.END + String.format("%-10s", " ") + "|");
        System.out.println("| " + FieldType.DART + "　　　　　　" + "|");
        System.out.println("|  " + ColorCode.YELLOW + condition.get(random.nextInt(condition.size())) + ColorCode.END + String.format("%-10s", " ") + "|");
        System.out.println("￣￣￣￣￣￣￣￣￣￣￣");

        if (this.horses[this.selected].equals(result.get(0))) {
            System.out.println("おめでとうございます！勝ちました！");

            double winMoney = selectOdds * betOut;
            this.haveMoney += winMoney;

            System.out.println("所持金は" + this.haveMoney + "です。");
        } else {
            System.out.println("あーあ。負けた。");
            System.out.println("所持金は" + this.haveMoney + "です。");
        }
    }
}

