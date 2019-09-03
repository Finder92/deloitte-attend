package io.renren.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @Author:Finder
 * @Description:   
 * @Date:Created in 10:30 2019/3/25
 */
public class IpUtil {
    /**
    *@Description 只考虑了Windows和Linux
    *@Author:Finder
    *@Param: 
    *@return: 
    *@Date: 10:51 2019/3/25
    */
    public static String getLocalIP() throws UnknownHostException, SocketException {
        if (isWindowsOS()) {
            return InetAddress.getLocalHost().getHostAddress();
        } else {
            return getLinuxLocalIp();
        }
    }
    /**
    *@Description
    *@Author:Finder
    *@Param: 
    *@return: 
    *@Date: 10:51 2019/3/25
    */
    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }
        return isWindowsOS;
    }
    /**
    *@Description 获取linux系统ip
    *@Author:Finder
    *@Param:
    *@return:
    *@Date: 10:54 2019/3/25
    */
    private static String getLinuxLocalIp() throws SocketException {
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                String name = intf.getName();
                if (!name.contains("docker") && !name.contains("lo")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String ipaddress = inetAddress.getHostAddress().toString();
                            if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")) {
                                ip = ipaddress;
                                System.out.println(ipaddress);
                            }
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            System.out.println("获取ip地址异常");
            ip = "127.0.0.1";
            ex.printStackTrace();
        }
        System.out.println("IP:"+ip);
        return ip;
    }

    public static void main(String[] args) throws Exception {
        /*System.out.println(InetAddress .getLocalHost());*/
        System.out.println(IpUtil.getLocalIP());

        /*StringBuilder sb = new StringBuilder();
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();//获取本地所有网络接口
            while (en.hasMoreElements()) {//遍历枚举中的每一个元素
                NetworkInterface ni = (NetworkInterface) en.nextElement();
                Enumeration<InetAddress> enumInetAddr = ni.getInetAddresses();
                while (enumInetAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) enumInetAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()
                            && inetAddress.isSiteLocalAddress()) {
                        sb.append("name:" + inetAddress.getHostName().toString() + "\n");
                        sb.append("ip:" + inetAddress.getHostAddress().toString() + "\n");
                        System.out.println(sb.toString());
                    }
                }
            }
        }catch (SocketException e)   {
            System.out.println(sb.toString());
        }*/
    }
}
