//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package iflytek.com.iniapp;

import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.util.SparseIntArray;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils {
    public static final String SPEC_CHAR = "[^a-zA-Z0-9һ-?]";
    public static final char COMMA = ',';
    public static final char BACKSLASH = '\\';
    private static final String BACKSLASH_STRING = "\\";
    private static final String DIVIDER = "\\|";
    public static final String COMMA_STRING = ",";
    public static final String COLON_STRING = ":";
    private static final String VERTICAL_STRING = "|";
    private static final String EQUAL_SYMBOL = "=";
    private static final String LEFT_BRACKET = "[";
    private static final String RIGHT_BRACKET = "]";
    private static final String SP = "sp";
    private static final String DP = "dp";
    private static final String DIP = "dip";
    private static final String PX = "px";
    private static final int RETURN_INVALID = -1;
    private static final String PERCENTAGE = "%p";

    private StringUtils() {
    }
//
//    public static float[] splitFloatWithPercentage(String str, String reg) {
//        String[] strValues = splitNoBackSlashString(str, reg);
//        if(strValues != null && strValues.length > 0) {
//            float[] values = new float[strValues.length];
//
//            for(int i = 0; i < values.length; ++i) {
//                values[i] = getFloatWithPecentage(strValues[i]);
//            }
//
//            return values;
//        } else {
//            return null;
//        }
//    }
//
//    public static float getFloatWithPecentage(String value) {
//        if(value != null && value.endsWith("%p")) {
//            float f = ConvertUtils.getFloat(value.substring(0, value.length() - 2));
//            return f;
//        } else {
//            return 0.0F;
//        }
//    }

    public static RectF splitRectF(String str) {
        float[] rectValues = splitFloat(str, ",");
        if(rectValues != null && rectValues.length == 4) {
            RectF rectF = new RectF(rectValues[0], rectValues[1], rectValues[2], rectValues[3]);
            return rectF;
        } else {
            return null;
        }
    }

    public static void stringMerge(StringBuffer sb, String left, String right) {
        sb.append(left);
        sb.append("=");
        sb.append(right);
        sb.append('\n');
    }

    public static void stringMerge(StringBuffer sb, String str) {
        sb.append("[");
        sb.append(str);
        sb.append("]");
        sb.append('\n');
    }

    public static boolean isEquals(String s1, String s2) {
        return s1 != null?s1.equals(s2):s2 == null;
    }

    public static int getNumberByString(String str) {
        if(TextUtils.isEmpty(str)) {
            return -1;
        } else {
            try {
                Pattern e = Pattern.compile("[^0-9]");
                Matcher m = e.matcher(str);
                return Integer.parseInt(m.replaceAll("").trim());
            } catch (Exception var3) {
                return -1;
            }
        }
    }

    public static float getSizeNumberByString(String str) {
        if(TextUtils.isEmpty(str)) {
            return -1.0F;
        } else {
            String size = "";
            long factor = 1L;
            if(str.contains("M")) {
                size = str.substring(0, str.indexOf("M")).trim();
            } else if(str.contains("m")) {
                size = str.substring(0, str.indexOf("m")).trim();
            } else if(str.contains("K")) {
                factor = 1000L;
                size = str.substring(0, str.indexOf("K")).trim();
            } else if(str.contains("k")) {
                factor = 1000L;
                size = str.substring(0, str.indexOf("k")).trim();
            } else if(str.contains("B")) {
                factor = 1000000L;
                size = str.substring(0, str.indexOf("B")).trim();
            } else if(str.contains("b")) {
                factor = 1000000L;
                size = str.substring(0, str.indexOf("b")).trim();
            }

            if(TextUtils.isEmpty(size)) {
                return -1.0F;
            } else {
                if(size.contains(".")) {
                    size = size.substring(0, size.indexOf("."));
                }

                if(TextUtils.isEmpty(size)) {
                    return -1.0F;
                } else {
                    try {
                        int e = Integer.parseInt(size);
                        return (float)((long)e / factor);
                    } catch (Exception var5) {
                        return -1.0F;
                    }
                }
            }
        }
    }

//    public static SparseIntArray stringToIntMap(String value) {
//        SparseIntArray array = new SparseIntArray();
//        if(value != null) {
//            String[] entryArray = value.split("\\|");
//            String[] arr$ = entryArray;
//            int len$ = entryArray.length;
//
//            for(int i$ = 0; i$ < len$; ++i$) {
//                String entryStr = arr$[i$];
//                int[] entry = splitInt(entryStr, ',');
//                if(entry.length == 2) {
//                    array.put(entry[0], entry[1]);
//                }
//            }
//        }
//
//        return array;
//    }

    public static String intMapToString(SparseIntArray array) {
        StringBuilder valueBuffer = new StringBuilder();
        if(array != null) {
            int size = array.size();

            int len;
            for(len = 0; len < size; ++len) {
                valueBuffer.append(array.keyAt(len));
                valueBuffer.append(",");
                valueBuffer.append(array.valueAt(len));
                valueBuffer.append("|");
            }

            len = valueBuffer.length();
            if(len > 0) {
                valueBuffer.delete(len - 1, len);
            }
        }

        return valueBuffer.toString();
    }

    public static byte[] splitByte(String value, char reg) {
        String[] strValues = splitString(value, reg);
        if(strValues != null && strValues.length > 0) {
            byte[] intValues = new byte[strValues.length];

            for(int i = 0; i < intValues.length; ++i) {
                intValues[i] = getByte(strValues[i]);
            }

            return intValues;
        } else {
            return new byte[0];
        }
    }

    public static byte getByte(String value) {
        try {
            if(value != null) {
                return Byte.decode(value).byteValue();
            }
        } catch (NumberFormatException var2) {
            ;
        }

        return (byte)0;
    }

    public static boolean compareEquals(String first, String second) {
        return first == null?second == null:first.equals(second);
    }

//    public static int[] splitIntWithPixel(String value, String reg) {
//        String[] strValues = splitNoBackSlashString(value, reg);
//        if(strValues != null && strValues.length > 0) {
//            int[] intValues = new int[strValues.length];
//
//            for(int i = 0; i < intValues.length; ++i) {
//                intValues[i] = getIntWithPixel(strValues[i]);
//            }
//
//            return intValues;
//        } else {
//            return null;
//        }
//    }
//
//    public static int getIntWithPixel(String value) {
//        int result = 0;
//        if(value.endsWith("sp")) {
//            result = ConvertUtils.getInt(value.substring(0, value.length() - 2));
//        } else if(value.endsWith("dp")) {
//            result = ConvertUtils.getInt(value.substring(0, value.length() - 2));
//        } else if(value.endsWith("dip")) {
//            result = ConvertUtils.getInt(value.substring(0, value.length() - 3));
//        } else if(value.endsWith("px")) {
//            result = ConvertUtils.getInt(value.substring(0, value.length() - 2));
//        }
//
//        return result;
//    }

    public static String getLinkString(URLSpan[] urlSpans) {
        String s02 = null;
        if(urlSpans != null) {
            URLSpan[] arr$ = urlSpans;
            int len$ = urlSpans.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                URLSpan text = arr$[i$];
                Uri uri = Uri.parse(text.getURL());
                String s01 = uri.toString();
                if(!s01.startsWith("http://") && !s01.startsWith("www.")) {
                    if(s01.startsWith("tel:")) {
                        s02 = s01.substring(4, s01.length());
                    } else if(s01.startsWith("mailto:")) {
                        s02 = s01.substring(7, s01.length());
                    }
                } else {
                    s02 = s01;
                }
            }
        }

        return s02;
    }

    public static URLSpan[] getUrls(CharSequence charSequence) {
        return charSequence == null?null:(charSequence instanceof Spanned?(URLSpan[])((Spanned)charSequence).getSpans(0, charSequence.length(), URLSpan.class):new URLSpan[0]);
    }

//    public static int similar(String s, String t) {
//        int n = s.length();
//        int m = t.length();
//        if(n == 0) {
//            return m;
//        } else if(m == 0) {
//            return n;
//        } else {
//            int[][] d = new int[n + 1][m + 1];
//
//            int i;
//            for(i = 0; i <= n; d[i][0] = i++) {
//                ;
//            }
//
//            int j;
//            for(j = 0; j <= m; d[0][j] = j++) {
//                ;
//            }
//
//            for(i = 1; i <= n; ++i) {
//                char s_i = s.charAt(i - 1);
//
//                for(j = 1; j <= m; ++j) {
//                    char t_j = t.charAt(j - 1);
//                    byte cost;
//                    if(s_i == t_j) {
//                        cost = 0;
//                    } else {
//                        cost = 1;
//                    }
//
//                    d[i][j] = CalculateUtils.minimum(new int[]{d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + cost});
//                }
//            }
//
//            return d[n][m];
//        }
//    }

    public static String[] splitNoBackSlashString(String value, String sep) {
        StringTokenizer token = new StringTokenizer(value, sep, false);
        int count = token.countTokens();
        if(count <= 0) {
            return new String[0];
        } else {
            String[] rst = new String[count];

            for(int i = 0; i < count; rst[i++] = token.nextToken()) {
                ;
            }

            return rst;
        }
    }

//    public static int[] splitInt(String value, char reg) {
//        String[] strValues = splitString(value, reg);
//        if(strValues != null && strValues.length > 0) {
//            int[] intValues = new int[strValues.length];
//
//            for(int i = 0; i < intValues.length; ++i) {
//                intValues[i] = ConvertUtils.getInt(strValues[i]);
//            }
//
//            return intValues;
//        } else {
//            return new int[0];
//        }
//    }

    public static String intArrayToString(char reg, int... array) {
        StringBuilder valueBuffer = new StringBuilder();
        if(array != null) {
            int size = array.length;

            for(int i = 0; i < size; ++i) {
                if(i > 0) {
                    valueBuffer.append(reg);
                }

                valueBuffer.append(array[i]);
            }
        }

        return valueBuffer.toString();
    }

    public static String intArrayToString(int[] value, String reg) {
        if(value == null) {
            return null;
        } else if(value.length == 0) {
            return "";
        } else {
            StringBuffer stringBuffer = new StringBuffer();

            for(int i = 0; i < value.length - 1; ++i) {
                stringBuffer.append(value[i]);
                stringBuffer.append(reg);
            }

            stringBuffer.append(value[value.length - 1]);
            return stringBuffer.toString();
        }
    }
//
//    public static Rect splitRect(String value) {
//        Rect[] rects = splitRects(value);
//        return rects != null && rects.length > 0?rects[0]:null;
//    }

//    public static Rect[] splitRects(String value) {
//        int[] rectValues = splitInt(value, ',');
//        if(rectValues != null && rectValues.length > 0 && rectValues.length % 4 == 0) {
//            Rect[] rects = new Rect[rectValues.length / 4];
//
//            for(int i = 0; i < rects.length; ++i) {
//                rects[i] = new Rect(rectValues[i * 4], rectValues[i * 4 + 1], rectValues[i * 4 + 2], rectValues[i * 4 + 3]);
//            }
//
//            return rects;
//        } else {
//            return null;
//        }
//    }

    public static String[] splitString(String value, String reg) {
        if(value != null && value.length() > 0) {
            String newReg = "\\";
            if(reg.equals(".") || reg.equals("|") || reg.equals("*") || reg.equals("+") || reg.equals("\\")) {
                reg = newReg + reg;
            }

            String[] valueArray = value.split(reg);
            ArrayList a = new ArrayList();
            int len = valueArray.length;

            for(int i = 0; i < len; ++i) {
                if(!valueArray[i].equals("\\") || i != len - 1 && valueArray[i + 1].length() != 0) {
                    a.add(valueArray[i]);
                } else {
                    a.add(reg);
                    ++i;
                }
            }

            return (String[])a.toArray(new String[0]);
        } else {
            return new String[0];
        }
    }

    public static float[] splitFloat(String value, String reg) {
        String[] strValues = splitNoBackSlashString(value, reg);
        if(strValues != null && strValues.length > 0) {
            float[] floatValues = new float[strValues.length];

            for(int i = 0; i < floatValues.length; ++i) {
                floatValues[i] = Float.parseFloat(strValues[i]);
            }

            return floatValues;
        } else {
            return null;
        }
    }

    public static String[] splitString(String value, char sep) {
        if(TextUtils.isEmpty(value)) {
            return new String[0];
        } else if(!value.contains("\\")) {
            return splitNoBackSlashString(value, String.valueOf(sep));
        } else {
            ArrayList rst = new ArrayList();
            StringBuilder sb = new StringBuilder();
            SegStatus s = SegStatus.Normal;

            for(int i = 0; i < value.length(); ++i) {
                char c = value.charAt(i);
                if(s == SegStatus.Normal) {
                    if(c != 92 && c != sep) {
                        sb.append(c);
                    } else if(c == 92) {
                        s = SegStatus.Escape;
                    } else if(sb.length() != 0) {
                        rst.add(sb.toString());
                        sb.delete(0, sb.length());
                    }
                } else if(s == SegStatus.Escape) {
                    if(c != 92 && c != sep) {
                        if(sep == 92) {
                            if(sb.length() != 0) {
                                rst.add(sb.toString());
                                sb.delete(0, sb.length());
                            }

                            sb.append(c);
                        } else {
                            sb.append('\\').append(c);
                        }
                    } else if(c == 92) {
                        sb.append('\\');
                    } else {
                        sb.append(sep);
                    }

                    s = SegStatus.Normal;
                }
            }

            if(sb.length() != 0) {
                rst.add(sb.toString());
            }

            return (String[])((String[])rst.toArray(new String[rst.size()]));
        }
    }

    public static String compose(String[] splits, char sep) {
        if(splits != null && splits.length != 0) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            String[] arr$ = splits;
            int len$ = splits.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String str = arr$[i$];
                int start = 0;
                boolean sepIndex = true;

                while(true) {
                    int var13 = str.indexOf(sep, start);
                    int regLen;
                    int regIndex;
                    if(var13 == -1) {
                        sb.append(str);
                        regLen = 0;

                        for(regIndex = str.length() - 1; regIndex >= 0 && str.charAt(regIndex) == 92; --regIndex) {
                            ++regLen;
                        }

                        if(regLen % 2 != 0) {
                            sb.append('\\');
                        }

                        if(i != splits.length - 1) {
                            sb.append(sep);
                        }

                        ++i;
                        break;
                    }

                    regLen = 0;

                    for(regIndex = var13 - 1; regIndex >= 0 && str.charAt(regIndex) == 92; --regIndex) {
                        ++regLen;
                    }

                    if(regLen % 2 == 0) {
                        String var14 = str.substring(0, var13);
                        String second = str.substring(var13);
                        str = var14 + '\\' + second;
                        start = var13 + 2;
                    } else {
                        start = var13 + 1;
                    }
                }
            }

            return sb.toString();
        } else {
            return null;
        }
    }

    public static String patternFilter(String value) {
        if(value == null) {
            return null;
        } else {
            String regEx = "[<>:;]";
            return patternFilter(value, regEx);
        }
    }

    public static String patternFilter(String value, String regEx) {
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(value);
        m.find();
        return m.replaceAll(" ");
    }

    public static boolean isChineseChar(char ch) {
        return ch >= 19968 && ch <= '?';
    }

    public static boolean isContainChinese(String str) {
        if(TextUtils.isEmpty(str)) {
            return false;
        } else {
            int len = str.length();

            for(int i = 0; i < len; ++i) {
                char c = str.charAt(i);
                if(isChineseChar(c)) {
                    return true;
                }
            }

            return false;
        }
    }

//    public static boolean isNumeric(String str) {
//        try {
//            Pattern e = Pattern.compile("[0-9]*");
//            return e.matcher(str).matches();
//        } catch (Exception var2) {
//            if(Logging.isDebugLogging()) {
//                Logging.d("StringUtil", "isNumeric Exception", var2);
//            }
//
//            return true;
//        }
//    }

//    public static boolean checkSpecialChar(String checkStr) {
//        Pattern pattern = Pattern.compile("[^a-zA-Z0-9һ-?]");
//        Matcher matcher = pattern.matcher(checkStr);
//        return matcher.find();
//    }

    public static boolean isEmpty(String string) {
        return TextUtils.isEmpty(string);
    }

    public static ArrayList<String> getQuickSymbols(boolean isChinese) {
        ArrayList symbols = new ArrayList(8);
        if(isChinese) {
            symbols.add("��");
            symbols.add("��");
            symbols.add("��");
            symbols.add("��");
            symbols.add("��");
            symbols.add("��");
            symbols.add("-");
            symbols.add("@");
        } else {
            symbols.add("!");
            symbols.add("?");
            symbols.add("@");
            symbols.add("_");
            symbols.add(":");
            symbols.add("\'");
            symbols.add("-");
            symbols.add("~");
        }

        return symbols;
    }

    public static String[] removeSameElements(String[] strs) {
        if(strs != null && strs.length > 0) {
            ArrayList sList = new ArrayList();

            for(int strsReturn = 0; strsReturn < strs.length; ++strsReturn) {
                if(!sList.contains(strs[strsReturn])) {
                    sList.add(strs[strsReturn]);
                }
            }

            String[] var4 = new String[sList.size()];

            for(int i = 0; i < sList.size(); ++i) {
                var4[i] = (String)sList.get(i);
            }

            return var4;
        } else {
            return null;
        }
    }

    public static String getMD5String(String resource) {
        String result = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(resource.getBytes());
            byte[] e = md.digest();
            StringBuilder buf = new StringBuilder();

            for(int offset = 0; offset < e.length; ++offset) {
                int i = e[offset];
                if(i < 0) {
                    i += 256;
                }

                if(i < 16) {
                    buf.append(0);
                }

                buf.append(Integer.toHexString(i));
            }

            result = buf.toString();
        } catch (NoSuchAlgorithmException var7) {
            ;
        } catch (NullPointerException var8) {
            ;
        }

        return result;
    }

    public static List<String> splitStringForList(String value, char sep) {
        if(TextUtils.isEmpty(value)) {
            return null;
        } else {
            ArrayList rst = new ArrayList();
            StringBuilder sb = new StringBuilder();
            SegStatus s = SegStatus.Normal;

            for(int i = 0; i < value.length(); ++i) {
                char c = value.charAt(i);
                if(s == SegStatus.Normal) {
                    if(c != 92 && c != sep) {
                        sb.append(c);
                    } else if(c == 92) {
                        s = SegStatus.Escape;
                    } else if(sb.length() != 0) {
                        rst.add(sb.toString());
                        sb.delete(0, sb.length());
                    }
                } else if(s == SegStatus.Escape) {
                    if(c != 92 && c != sep) {
                        if(sep == 92) {
                            if(sb.length() != 0) {
                                rst.add(sb.toString());
                                sb.delete(0, sb.length());
                            }

                            sb.append(c);
                        } else {
                            sb.append('\\').append(c);
                        }
                    } else if(c == 92) {
                        sb.append('\\');
                    } else {
                        sb.append(sep);
                    }

                    s = SegStatus.Normal;
                }
            }

            if(sb.length() != 0) {
                rst.add(sb.toString());
            }

            return rst;
        }
    }

    public static List<String> simpleSplit(String content, String regularExpression) {
        if(TextUtils.isEmpty(content)) {
            return null;
        } else {
            String[] result = content.split(regularExpression);
            if(result != null && result.length > 0) {
                ArrayList list = new ArrayList();

                for(int i = 0; i < result.length; ++i) {
                    list.add(result[i]);
                }

                return list;
            } else {
                return null;
            }
        }
    }

    public static String simpleJoin(Collection<?> list, String regularExpression) {
        if(list != null && !list.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            Iterator i$ = list.iterator();

            while(i$.hasNext()) {
                Object obj = i$.next();
                builder.append(obj.toString()).append(regularExpression);
            }

            builder.delete(builder.length() - regularExpression.length(), builder.length());
            return builder.toString();
        } else {
            return "";
        }
    }

//    public static <T> String simpleJoin(Collection<T> list, String regularExpression, ToStringListener<T> listener) {
//        if(list != null && !list.isEmpty()) {
//            StringBuilder builder = new StringBuilder();
//            Iterator i$ = list.iterator();
//
//            while(i$.hasNext()) {
//                Object obj = i$.next();
//                builder.append(listener.toString(obj)).append(regularExpression);
//            }
//
//            builder.delete(builder.length() - regularExpression.length(), builder.length());
//            return builder.toString();
//        } else {
//            return "";
//        }
//    }

    public static String simpleJoin(Map<?, ?> map, String betweenKeyValue, String betweenEntry) {
        if(map != null && !map.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            Iterator i$ = map.entrySet().iterator();

            while(i$.hasNext()) {
                Entry entry = (Entry)i$.next();
                builder.append(entry.getKey()).append(betweenKeyValue).append(entry.getValue()).append(betweenEntry);
            }

            builder.delete(builder.length() - betweenEntry.length(), builder.length());
            return builder.toString();
        } else {
            return "";
        }
    }
//
//    public static Map<String, Integer> simpleSplit(String content, String betweenKeyValue, String betweenEntry) {
//        if(TextUtils.isEmpty(content)) {
//            return null;
//        } else {
//            String[] result = content.split(betweenEntry);
//            if(result != null && result.length > 0) {
//                HashMap list = new HashMap();
//
//                for(int i = 0; i < result.length; ++i) {
//                    String[] keyValue = result[i].split(betweenKeyValue);
//                    if(keyValue != null && keyValue.length == 2) {
//                        list.put(keyValue[0], Integer.valueOf(ConvertUtils.getInt(keyValue[1])));
//                    }
//                }
//
//                return list;
//            } else {
//                return null;
//            }
//        }
//    }

    public static String getRandomUUid() {
        try {
            return UUID.randomUUID().toString();
        } catch (Exception var1) {
            return "";
        }
    }

    public interface ToStringListener<T> {
        String toString(T var1);
    }

    private static enum SegStatus {
        Normal,
        Escape;

        private SegStatus() {
        }
    }
}
