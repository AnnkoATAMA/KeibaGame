package keiba.enums;

public enum ColorCode {
    RED("\u001b[00;31m"),
    GREEN("\u001b[00;32m"),
    YELLOW("\u001b[00;33m"),
    BLUE("\u001b[00;34m"),
    PINK("\u001b[00;35m"),
    CYAN("\u001b[00;36m"),
    GRAY("\u001b[00;37m"),
    WHITE("\u001b[00;40m"),
    RED_BG("\u001b[00;41m"),
    GREEN_BG("\u001b[00;42m"),
    YELLOW_BG("\u001b[00;43m"),
    BLUE_BG("\u001b[00;44m"),
    PINK_BG("\u001b[00;45m"),
    CYAN_BG("\u001b[00;46m"),
    GRAY_BG("\u001b[00;47m"),
    REVERSE("\u001b[7m"),
    END("\u001b[00m");

    private final String code;

    ColorCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }
}