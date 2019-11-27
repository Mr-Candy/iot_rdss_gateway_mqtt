package com.rd.iot_rdss_gateway.util;

import java.io.UnsupportedEncodingException;

/**
 * @Description:
 * @Author 老薛
 * @Date 2019/6/24 10:13
 * @Version V1.0
 */
public class ASCIIUtil {
    private static String hexStr = "0123456789ABCDEF";
    private static String[] binaryArray =
            {"0000", "0001", "0010", "0011",
                    "0100", "0101", "0110", "0111",
                    "1000", "1001", "1010", "1011",
                    "1100", "1101", "1110", "1111"};

    public static String x16toString(String x16, String CharsetName) throws UnsupportedEncodingException {
        if (x16 == null || "".equals(x16.trim())) {
            return "";
        }
        String tempStr;
        byte[] b = new byte[x16.length() / 2];
        for (int i = 0; i < x16.length() / 2; i++) {
            tempStr = x16.substring(i * 2, i * 2 + 2);
            int temp = Integer.parseInt(tempStr, 16);
            b[i] = (byte) temp;
        }
        String restr = new String(b, CharsetName);
        return restr;
    }

    public static String inttoString(int num, String CharsetName) throws UnsupportedEncodingException {
        byte[] b = new byte[1];
        b[0] = (byte) num;
        String restr = new String(b, CharsetName);
        return restr;
    }

    /*
    有符号(4位)十六进制转十进制
     */
    public static int parseHex4(String hex) {
        if (hex.length() != 8) {
            throw new NumberFormatException("Wrong length: " + hex.length() + ", must be 8.");
        }
        hex = hex.toUpperCase();
        //符号位判断
        int fuhao = Integer.parseInt(hex.substring(0, 1), 16);
        if (fuhao > 7) {
            fuhao = fuhao - 8;
            String strHex = Integer.toHexString(fuhao);
            StringBuffer buffer = new StringBuffer(hex);
            buffer.replace(0, 1, strHex);
            return Integer.parseInt(buffer.toString(), 16) * -1;
        } else {
            return Integer.parseInt(hex, 16);
        }
    }

    /*
    有符号(2位)十六进制转十进制
     */
    public static int parseHex2(String hex) {
        if (hex.length() != 4) {
            throw new NumberFormatException("Wrong length: " + hex.length() + ", must be 4.");
        }
        hex = hex.toUpperCase();
        //符号位判断
        int fuhao = Integer.parseInt(hex.substring(0, 1), 16);
        if (fuhao > 7) {
            fuhao = fuhao - 8;
            String strHex = Integer.toHexString(fuhao);
            StringBuffer buffer = new StringBuffer(hex);
            buffer.replace(0, 1, strHex);
            return Integer.parseInt(buffer.toString(), 16) * -1;
        } else {
            return Integer.parseInt(hex, 16);
        }
    }

    /*
   无符号十六进制转十进制
    */
    public static int parseHex(String hex) {
        hex = hex.toUpperCase();
        return Integer.parseInt(hex, 16);
    }

    /*
    十进制经纬度转度
     */
    public static double parseWeizhi(int num) {
        int i = 1;
        if (num == 0) {
            return 0;
        } else if (num < 0) {
            num *= -1;
            i = -1;
        }
        double xiaoShu = num % 1000;
        double second = xiaoShu / 1000 + (num - xiaoShu) / 1000 % 60;
        double fen = ((double) num / 1000 - second) % 3600 / 60;
        double du = ((double) num / 1000 - second - fen * 60) / 3600;
//        System.out.println(du + "度" + fen + "分" + second + "秒");
        double parDu = du + fen / 60 + second / 3600;
        return parDu * i;
    }

    /**
     * @param bArray
     * @return 二进制数组转换为二进制字符串
     */
    public static String bytes2BinStr(byte[] bArray) {

        String outStr = "";
        int pos = 0;
        for (byte b : bArray) {
            //高四位
            pos = (b & 0xF0) >> 4;
            outStr += binaryArray[pos];
            //低四位
            pos = b & 0x0F;
            outStr += binaryArray[pos];
        }
        return outStr;
    }

    /**
     * @param hexString
     * @return 将十六进制转换为二进制字节数组   16-2
     */
    public static byte[] hexStr2BinArr(String hexString) {
        //hexString的长度对2取整，作为bytes的长度
        int len = hexString.length() / 2;
        byte[] bytes = new byte[len];
        byte high = 0;//字节高四位
        byte low = 0;//字节低四位
        for (int i = 0; i < len; i++) {
            //右移四位得到高位
            high = (byte) ((hexStr.indexOf(hexString.charAt(2 * i))) << 4);
            low = (byte) hexStr.indexOf(hexString.charAt(2 * i + 1));
            bytes[i] = (byte) (high | low);//高地位做或运算
        }
        return bytes;
    }

    /**
     * @param hexString
     * @return 将十六进制转换为二进制字符串
     */
    public static String hexStr2BinStr(String hexString) {
        return bytes2BinStr(hexStr2BinArr(hexString));
    }

    /**
     * hex转byte数组
     * @param hex
     * @return
     */
    public static byte[] hexToByte(String hex){
        int m = 0, n = 0;
        int byteLen = hex.length() / 2; // 每两个字符描述一个字节
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[i] = Byte.valueOf((byte)intVal);
        }
        return ret;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
      /*  String x16 = "23";
        System.out.println(x16toString("23", "US-ASCII"));
        String data = "1925FD00";
        int var = parseHex4(data);
        System.out.println(var);
        double weizhi = parseWeizhi(var);
        System.out.println(weizhi);
        System.out.println(parseHex2("07D0"));*/
//        System.out.println(hexStr2BinStr("30"));
        System.out.println(Integer.parseInt("23",16));
//        System.out.println(Integer.parseInt("001",2));
       /* String st = "245458585800330624e66005c50a000000f8";
        for (int i = 0; i <st.length()/2 ; i++) {
            System.out.print(x16toString(st.substring(i*2,(i+1)*2), "US-ASCII"));
        }*/
    }
}
