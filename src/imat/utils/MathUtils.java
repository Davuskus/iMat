package imat.utils;

import java.text.DecimalFormat;

public final class MathUtils {

    public static double round(double value, int numDecimals) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#");
        for (int i = 0; i < numDecimals; i++) {
            if (i == 0) {
                stringBuilder.append(".");
            }
            stringBuilder.append("#");
        }
        DecimalFormat df = new DecimalFormat(stringBuilder.toString());
        return Double.valueOf(df.format(value).replace(',', '.'));
    }

    public static String asPriceTag(double value) {
        return asPriceTag(value, "kr");
    }

    public static String asPriceTag(double value, String unit) {
        return String.format("%.2f "+unit, MathUtils.round(value, 2));
    }

}
