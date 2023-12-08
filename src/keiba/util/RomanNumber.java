package keiba.util;

public class RomanNumber {
    public static String getRomanNumber(int i) {
        // 0以下もしくは11以上は未実装なのでエラーを返す
        if (i < 1 || 10 < i) throw new IllegalArgumentException();

        for (Number num: Number.values()) {
            if (num.number == i) return num.toString();
        }

        // 見つからない場合はNull
        return null;
    }

    public enum Number {
        I(1, "Ⅰ"),
        II(2, "Ⅱ"),
        III(3, "Ⅲ"),
        IV(4, "Ⅳ"),
        V(5, "Ⅴ"),
        VI(6, "Ⅵ"),
        VII(7, "Ⅶ"),
        VIII(8, "Ⅷ"),
        IX(9, "Ⅸ"),
        X(10, "Ⅹ");

        private final int number;
        private final String display;

        Number(int number, String display) {
            this.number = number;
            this.display = display;
        }

        @Override
        public String toString() {
            return this.display;
        }
    }
}
