package keiba.enums;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum HorseType {
    CUSTOM("馬", FieldType.NONE),
    SERIFOS("セリフォス", FieldType.SHIBA),
    SCHNELL_MEISTER("シュネルマイスター", FieldType.SHIBA),
    NAMUR("ナミュール", FieldType.SHIBA),
    DANON_THE_KID("ダノンザキッド", FieldType.SHIBA),
    EQUINOX("イクイノックス",FieldType.SHIBA),
    PANTHALASSA("パンサラッサ", FieldType.DART),
    LIBERTY_ISLAND("リバティアイランド",FieldType.SHIBA),
    STARS_ON_EARTH("スターズオンアース",FieldType.SHIBA),
    DANON_BELUGA("ダノンベルーガ",FieldType.SHIBA),
    GENTILDONNA("ジェンティルドンナ",FieldType.SHIBA),
    GERALDINA("ジェラルディーナ",FieldType.SHIBA),
    DEEP_IMPACT("ディープインパクト",FieldType.SHIBA),
    TITLEHOLDER("タイトルホルダー",FieldType.SHIBA),
    VELA_AZUL("ヴェラアズール",FieldType.SHIBA),
    ALMOND_EYE("アーモンドアイ",FieldType.SHIBA),
    SWING("スウィング",FieldType.DART),
    JUSTIN_PALACE("ジャスティンパレス",FieldType.SHIBA),
    GRAN_ALEGRIA("グランアレグリア",FieldType.SHIBA),
    DURAMENTE("デュラメンテ",FieldType.SHIBA),
    CONTRAIL("コントレイル",FieldType.SHIBA),
    ORFEBRE("オルフェーヴル",FieldType.SHIBA),
    SHINRYOKUKA("シンリョクカ",FieldType.SHIBA),
    SODASHI("ソダシ",FieldType.SHIBA),
    GAIA_FORCE("ガイアフォース",FieldType.SHIBA),
    USHBA_TESORO("ウシュバテソーロ",FieldType.DART),
    LEMON_POP("レモンポップ",FieldType.DART),
    DURA_EREDE("デュラエレーデ",FieldType.SHIBA),
    IZU_JO_NO_KISEKI("イズジョーノキセキ",FieldType.SHIBA),
    JUST_A_WAY("ジャスタウェイ",FieldType.SHIBA),
    DARING_TACT("デアリングタクト",FieldType.SHIBA),
    THROUGH_SEVEN_SEAS("スルーセブンシーズ",FieldType.SHIBA),
    EFFORIA("エフフォーリア",FieldType.SHIBA),
    EPIPHANEIA("エピファネイア",FieldType.SHIBA),
    BUENA_VISTA("ブエナビスタ",FieldType.SHIBA),
    KIZUNA("キズナ",FieldType.SHIBA),
    STELLA_VELOCE("ステラヴェローチェ",FieldType.SHIBA),
    MAMA_COCHA("ママコチャ",FieldType.SHIBA),
    GEOGLYPH("ジオグリフ",FieldType.SHIBA),
    STAY_GOLD("ステイゴールド",FieldType.SHIBA),
    WIN_MARILYN("ウインマリリン",FieldType.SHIBA),
    DERMA_SOTOGAKE("デルマソトカゲ",FieldType.DART),
    NAKAYAMA_FESTA("ナカヤマフェスタ",FieldType.SHIBA),
    KISEKI("キセキ",FieldType.SHIBA),
    HEARTS_CRY("ハーツクライ",FieldType.SHIBA),
    SOUNDS_OF_EARTH("サウンズオブアース",FieldType.SHIBA),
    CHRONO_GENESIS("クロノジェネシス",FieldType.DART),
    RULERSHIP("ルーラーシップ",FieldType.SHIBA),
    PIXIE_KNIGHT("ピクシーナイト",FieldType.DART),
    AFRICAN_GOLD("アフリカンゴールド",FieldType.DART),
    COPANA_RICKEY("コパノリッキー",FieldType.DART),
    APAPANE("アパパネ",FieldType.SHIBA),
    NAMURA_CLAIR("ナムラクレア",FieldType.SHIBA),
    KUROFUNE("クロフネ",FieldType.SHIBA),
    ASK_VICTOR_MORE("アスクビクターモア",FieldType.SHIBA),
    AUTHORITY("オーソリティ",FieldType.SHIBA),
    CAFE_PHAROAH("カフェファラオ",FieldType.DART),
    SYMBOLI_RUDOLF("シンボリルドルフ",FieldType.SHIBA),
    ESPOIR_CITY("エスポワールシチー",FieldType.DART),
    T_O_KEYNES("テーオーケインズ",FieldType.DART),
    MEISHO_HARIO("メイショウハリオ",FieldType.DART),
    CROWN_PRIDE("クラウンプライド",FieldType.DART),
    AUVERGNE("オーヴェルニュ",FieldType.DART),
    SMART_FALCON("スマートファルコン",FieldType.DART),
    DANON_PHARAOH("ダノンファラオ",FieldType.DART),
    OMEGA_PERFUME("オメガパフューム",FieldType.DART),
    KANE_HEKILI("カネヒキリ",FieldType.DART);


    private final String display;
    private final FieldType type;


    HorseType(String display, FieldType type){
        this.display = display;
        this.type = type;
    }

    @Override
    public String toString() {
        return this.display;
    }

    /**
     * 指定されたFieldTypeの馬をcountだけ取得するメソッド
     * @param count 取得する配列要素の数
     * @param fieldType 取得する馬のフィールドタイプ
     * @return 指定されたFieldTypeの馬をランダムに格納した配列
     */
    public static HorseType[] getRandomHorses(int count, FieldType fieldType) {
        if (count >= values().length) throw new IllegalArgumentException();

        // 指定されたFieldTypeの馬だけを抽出
        LinkedList<HorseType> horseTypes = Arrays.stream(values()).filter(h -> h.type.equals(fieldType)).collect(Collectors.toCollection(LinkedList::new));
        Collections.shuffle(horseTypes);

        return IntStream.range(0, count).mapToObj(i -> horseTypes.poll()).toArray(HorseType[]::new);
    }
}
