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
        I(1),
        II(2),
        III(3),
        IV(4),
        V(5),
        VI(6),
        VII(7),
        VIII(8),
        IX(9),
        X(10);

        private int number;

        Number(int number) {
            this.number = number;
        }
    }
}
