package imat.utils;

import java.text.DecimalFormat;

public final class MathUtils {

    public static double round(double value, int numDecimals) {
        // TODO Check if this actually rounds or just removes decimals
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

}
