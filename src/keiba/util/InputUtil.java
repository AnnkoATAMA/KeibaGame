package keiba.util;

import java.util.*;
import java.util.stream.IntStream;

public class InputUtil {
    private static final Scanner sc = new Scanner(System.in);

    public static int getInt(String message, int min, int max) {
        if (min > max) throw new IllegalArgumentException();
        try {
            System.out.print(message == null ? "数字を入力してください: " : message + ": ");
            int input = sc.nextInt();
            if (max < input || input < min) {
                System.out.println(input + "は範囲外です。");
                return getInt(message, min, max);
            }
            // scanner.nextLine()を次の行に移動
            sc.nextLine();
            return input;
        } catch (InputMismatchException e) {
            // 数字以外 || intの範囲外 が入力されたら再帰的に呼び出す。
            sc.nextLine();
            return getInt(message, min, max);
        }
    }

    public static int getInt() {
        return getInt(null, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static int getInt(String message) {
        return getInt(message, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static int getInt(String message, int min) {
        return getInt(message, min, Integer.MAX_VALUE);
    }

    public static boolean getAnswer(String message, Boolean defaultAnswer) {
        System.out.print((message == null ? "よろしいですか？" : message) + "(" + (defaultAnswer == null ? "y/n" : defaultAnswer ? "Y/n": "y/N") + ") :");
        String input = sc.nextLine();

        if (input.isEmpty() && defaultAnswer != null) return defaultAnswer;
        return input.equalsIgnoreCase("y") || (!input.equalsIgnoreCase("n") && getAnswer(message, defaultAnswer));
    }

    public static boolean getAnswer(String message) {
        return getAnswer(message, null);
    }

    @SafeVarargs
    public static <T extends Enum<?>> T getEnumObject(String message, Class<T> enums, T... ignore) {
        final StringBuilder sb = new StringBuilder();
        final List<T> objects = new ArrayList<>(List.of(enums.getEnumConstants()));
        if (ignore != null) objects.removeAll(List.of(ignore));

        IntStream.range(0, objects.size()).forEach(i -> sb.append((i + 1)).append(": ").append(objects.get(i)).append("\n"));
        return objects.get(getInt((message == null ? enums.getSimpleName() + "の種類を選択してください\n" : message)  + sb, 1, objects.size()) - 1);
    }

    @SafeVarargs
    public static <T extends Enum<?>> T getEnumObject(Class<T> enums, T... ignore) {
        return getEnumObject(null, enums, ignore);
    }
}
