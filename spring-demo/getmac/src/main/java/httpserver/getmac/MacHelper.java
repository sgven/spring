package httpserver.getmac;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class MacHelper {

    public static String getMacAddr() throws Exception {
        // 获取本机网卡interface
        NetworkInterface netInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        // 获取mac地址byte数组
        byte[] macAddr  = netInterface.getHardwareAddress();
        StringBuffer mac = new StringBuffer();
        mac.append(toHexString(macAddr[0]));
        for (int i=1;i < macAddr.length; i ++) {
            mac.append("-").append(toHexString(macAddr[i]));
        }
        return mac.toString();
    }

    private static String toHexString(int i) {
        String str = Integer.toHexString((int) (i & 0xff));
        if (str.length() == 1) {
            str = "0" + str;
        }
        return str;
    }
}
