package com.zyq.demo.umenglib.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 字符串处理工具
 * @author Zyq
 * @date 2020/11/9　15:13.
 */
public class StringUtil {
    /**
     * 判断是否为null或空值
     *
     * @param str String
     * @return true or false
     */
    public static boolean isNullOrEmpty(String str) {
        if (TextUtils.isEmpty(str) || "null".equalsIgnoreCase(str)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为null或空值
     *
     * @param str String
     * @return true or false
     */
    public static boolean isNotEmpty(String str) {
        if (TextUtils.isEmpty(str) || "null".equalsIgnoreCase(str)) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否为null或空值
     */
    public static boolean isEmpty(String str) {
        if (TextUtils.isEmpty(str) || "null".equalsIgnoreCase(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否为null或空值或者"null","undefined"
     */
    public static boolean isEmptyURL(String str) {
        if (TextUtils.isEmpty(str) || "null".equalsIgnoreCase(str) || "undefined".equalsIgnoreCase(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断str1和str2是否相同
     *
     * @param str1 str1
     * @param str2 str2
     * @return true or false
     */
    public static boolean equals(String str1, String str2) {
        return str1 != null && str1.equals(str2);
    }

    /**
     * 判断str1和str2是否相同(不区分大小写)
     *
     * @param str1 str1
     * @param str2 str2
     * @return true or false
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 != null && str1.equalsIgnoreCase(str2);
    }

    /**
     * 判断字符串str1是否包含字符串str2
     *
     * @param str1 源字符串
     * @param str2 指定字符串
     * @return true源字符串包含指定字符串，false源字符串不包含指定字符串
     */
    public static boolean contains(String str1, String str2) {
        return str1 != null && str1.contains(str2);
    }

    /**
     * 判断字符串是否为空，为空则返回一个空值，不为空则返回原字符串
     *
     * @param str 待判断字符串
     * @return 判断后的字符串
     */
    public static String getString(String str) {
        if (str == null || "null".equalsIgnoreCase(str)) {
            return "";
        } else {
            return str;
        }
    }

    /**
     * 返回去掉空格的字符串
     *
     * @param str 待判断字符串
     * @return 判断后的字符串
     */
    public static String getNoTrimString(String str) {
        if (str == null || "null".equalsIgnoreCase(str)) {
            return "";
        } else {
            return str.replaceAll(" ", "");
        }
    }

    /**
     * 判断字符串是否为空，为空则返回一个默认0，不为空则返回原字符串
     *
     * @param str 待判断字符串
     * @return 判断后的字符串
     */
    public static String getStringZero(String str) {
        if (TextUtils.isEmpty(str) || "null".equals(str)) {
            return "0";
        } else {
            return str;
        }
    }

    /**
     * 判断字符串是否为空，为空则返回一个空值，不为空则返回原字符串
     *
     * @param str 待判断字符串
     * @return 判断后的字符串
     */
    public static String getStringElseHeng(String str) {
        if (isEmpty(str)) {
            return "--";
        } else {
            return str;
        }
    }


    /**
     * 判断字符串是否为空，为空则返回一个空值，不为空则返回原字符串
     *
     * @param str
     * 待判断字符串
     * @return 判断后的字符串
     */
    private static final String ZHANWU_STRING = "暂无";

    public static String getStringDefault(String str) {
        if (isEmpty(str)) {
            return ZHANWU_STRING;
        } else {
            return str;
        }
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            //去掉多余的0
            s = s.replaceAll("0+?$", "");
            //如最后一位是.则去掉
            s = s.replaceAll("[.]$", "");
        }
        return s;
    }


    /**
     * 格式化价格为两位小数
     * 小数全为0保留整数，否则保留两位小数
     */
    public static String getFormatPrice(String price) {
        DecimalFormat df1 = new DecimalFormat("0.00");
        DecimalFormat df2 = new DecimalFormat("0");
        double s = 0;
        try {
            s = Double.parseDouble(price);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (s % 1 > 0) {
            return df1.format(s);
        } else {
            return df2.format(s);
        }
    }

    /**
     * 格式化价格为两位小数
     * 小数全为0保留整数，否则保留两位小数
     */
    public static String getFormatPrice(Double price) {
        DecimalFormat df1 = new DecimalFormat("0.00");
        DecimalFormat df2 = new DecimalFormat("0");
        if (price % 1 > 0) {
            return df1.format(price);
        } else {
            return df2.format(price);
        }
    }

    /**
     * 格式化价格为两位小数
     * 小数全为0保留整数，否则保留两位小数
     */
    public static String getFormatPrice(float price) {
        DecimalFormat df1 = new DecimalFormat("0.00");
        DecimalFormat df2 = new DecimalFormat("0");
        if (price % 1 > 0) {
            return df1.format(price);
        } else {
            return df2.format(price);
        }
    }

    /**
     * 格式化价格为两位小数
     */
    public static String getFormatPrice(Object price, String formatString) {
        try {
            DecimalFormat df1 = new DecimalFormat(formatString);
            return df1.format(price);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 格式化毫秒到hh；mm：ss
     *
     * @param l
     * @return
     */
    public static String formatlongToTime(long l) {
        StringBuilder builder = new StringBuilder();
        if (l < 1000) {
            return "00：00：00";
        }
        DecimalFormat df = new DecimalFormat("00");
        long hh = l / 1000 / 60 / 60;
        long mm = l / 1000 / 60 % 60;
        long ss = l / 1000 % 60;
        if (hh > 0) {
            builder.append(df.format(hh));
            builder.append("：");
        }
        builder.append(df.format(mm));
        builder.append("：");
        builder.append(df.format(ss));
        return builder.toString();
    }

    /**
     * 格式化毫秒到hh；mm：ss
     *
     * @param second
     * @return
     */
    public static long[] formatlongToLongArray(long second) {
        long[] times = new long[4];
        long hh = second / 3600;
        long mm = (second % 3600) / 60;
        long ss = second % 60;
        times[0] = 0;
        times[1] = hh;
        times[2] = mm;
        times[3] = ss;
        return times;
    }

    /**
     * 格式化毫秒到hh；mm：ss
     *
     * @param ms
     * @return
     */
    public static long[] formatmsToLongArray(long ms) {
        long[] times = new long[4];
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        times[0] = day;
        times[1] = hour;
        times[2] = minute;
        times[3] = second;
        return times;
    }


    /**
     * 格式化毫秒到hh；mm：ss
     *
     * @param lString
     * @return
     */
    public static String formatlongToTime(String lString) {
        try {
            long l = Long.parseLong(lString);
            return formatlongToTime(l);
        } catch (Exception e) {
            return "00：00：00";
        }
    }

    /**
     * 手机号格式化为158****9690
     */
    public static String formatPhoneNum(String phonenum) {
        if (phonenum == null) {
            return phonenum;
        }
        if (phonenum.length() < 11) {
            return phonenum;
        }
        return phonenum.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 从string转换int
     * 解决异常，默认0
     *
     * @param intString
     * @return
     */
    public static int getIntValue(String intString) {
        int intValue = 0;
        if (TextUtils.isEmpty(intString)) {
            return intValue;
        }
        try {
            intValue = Integer.parseInt(intString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return intValue;
    }

    /**
     * 从string转换long
     * 解决异常，默认0
     *
     * @param intString
     * @return
     */
    public static long getLongValue(String intString) {
        long longValue = 0;
        if (TextUtils.isEmpty(intString)) {
            return longValue;
        }
        try {
            longValue = Long.parseLong(intString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return longValue;
    }

    /**
     * 从string转换double
     * 解决异常，默认0
     *
     * @param intString
     * @return
     */
    public static double getDoubleValue(String intString) {
        double doubleValue = 0;
        if (TextUtils.isEmpty(intString)) {
            return doubleValue;
        }
        try {
            doubleValue = Double.parseDouble(intString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return doubleValue;
    }

    /**
     * 从string转换float
     * 解决异常，默认0
     *
     * @param intString
     * @return
     */
    public static float getFloatValue(String intString) {
        float floatValue = 0;
        if (TextUtils.isEmpty(intString)) {
            return floatValue;
        }
        try {
            floatValue = Float.parseFloat(intString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return floatValue;
    }


    /**
     * 返回一个用0补位的字符串
     *
     * @param str        待补位对象
     * @param num        位数
     * @param isAddAtEnd 后补位
     * @return 判断后的字符串
     */
    public static String getEnoughZeroString(Object str, int num, boolean isAddAtEnd) {
        StringBuilder stringBuilder = new StringBuilder();
        if (num <= 0) {
            return stringBuilder.toString();
        }
        String oldString = getString(str.toString());
        int needNum = num - oldString.length();
        if (needNum <= 0) {
            return oldString;
        }
        for (int i = 0; i < needNum; i++) {
            stringBuilder.append(0);
        }
        if (isAddAtEnd) {
            return oldString + stringBuilder.toString();

        } else {
            return stringBuilder.toString() + oldString;
        }
    }

    /**
     * 去除空格
     *
     * @param s
     * @return
     */
    public static String wipeBlank(String s) {
        return getString(s).replace(" ", "");
    }

    /**
     * 正则表达式，仅保留数字
     *
     * @param str
     * @return
     */
    public static String onlyNumber(String str) {
        String REGEX = "[^(0-9)]";
        String numberStr = Pattern.compile(REGEX).matcher(str).replaceAll("").trim();
        return numberStr;
    }

    /**
     * 集合转字符串
     *
     * @param objectList 集合，注意集合中对象的toStirng方法要重写
     * @return 获取toString字符串
     */
    public static String listToString(List<Object> objectList) {
        if (objectList == null || objectList.size() < 0) {
            return "";
        }
        if (objectList.size() == 1) {
            return objectList.get(0).toString();
        }
        String originStr = "";
        //删除前后中括号
        try {
            originStr = objectList.toString();
            originStr = originStr.substring(1, originStr.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return originStr;
    }

    /**
     * 集合转字符串
     *
     * @param objectList 集合，注意集合中对象的toStirng方法要重写
     * @param sub        替换默认,的字符串
     * @return 获取toString字符串
     */
    public static String listToString(List objectList, String sub) {
        if (objectList == null || objectList.size() < 0) {
            return "";
        }
        if (objectList.size() == 1) {
            return objectList.get(0).toString();
        }
        String originStr = objectList.toString();
        try {
            originStr = objectList.toString();
            //删除前后中括号
            originStr = originStr.substring(1, originStr.length() - 1);
            //替换字符串
            if (sub != null) {
                originStr = originStr.replaceAll(",", sub);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return originStr;
    }

    /**
     * 复制文字到剪切板
     *
     * @param context
     * @param text
     */
    public static void textToClip(Context context, String text) {
        if (context == null) {
            return;
        }
        if (isEmpty(text)) {
            return;
        }
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText(null, text));
    }

    /**
     * 获取通讯录用户名称和电话号码
     *
     * @param uri
     * @param context
     * @return String[2] 0-姓名，1-手机号
     */
    public static String[] getPhoneContacts(Uri uri, Context context) {
        String[] contact = new String[2];
        try {
            //得到ContentResolver对象
            ContentResolver cr = context.getContentResolver();
            //取得电话本中开始一项的光标
            Cursor cursor = cr.query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                //取得联系人姓名
                int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                contact[0] = cursor.getString(nameFieldColumnIndex).trim();
                //取得电话号码
                String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
                if (phone != null) {
                    phone.moveToFirst();
                    contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).toString().replace("-", "");
                }
                phone.close();
                cursor.close();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contact;
    }

}
