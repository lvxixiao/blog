package lvxixiao.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class Tools {
	//MD5加密的盐
	public static int salt = 0xfe;
	
	/*
	 * 将markdown第一段截取为summary
	 * md文件的换行符是\n
	 */
	public static String getSummary(String content) {
		StringBuilder summary = new StringBuilder();
		boolean flag = false;
		String pattern = "^#+.*";
		String[] lines = content.split("\n");
		for (String line : lines) {
			boolean match = Pattern.matches(pattern, line);
			if(match) {
				flag = !flag;
				if(!flag)
					break;
			}
			if(flag && !match) {
				if(!line.isEmpty()) {
					summary.append(line);
					if(summary.length() > 250) {
						return summary.substring(0, 250)+"...";
					}
				}
			}
		}
		return summary.toString();
	}
		
	public static String MD5(String password) {
		byte[] name = null;
		try {
			name = password.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] bs = null;
		try {
			bs = MessageDigest.getInstance("MD5").digest(name);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        StringBuilder sb = new StringBuilder(40);
        for(byte x:bs) {
        	//0xff为盐，此处不加盐。
            if((x & salt)>>4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
	}
	//获取ip地址
	public static String getIpAddr(HttpServletRequest request) {     
        String ip = request.getHeader("x-forwarded-for");     
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
            ip = request.getHeader("Proxy-Client-IP");     
        }     
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
            ip = request.getHeader("WL-Proxy-Client-IP");     
        }     
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
            ip = request.getRemoteAddr();     
            if(ip.equals("127.0.0.1")){       
                //根据网卡取本机配置的IP       
                InetAddress inet=null;       
                try {       
                    inet = InetAddress.getLocalHost();       
                } catch (Exception e) {       
                    e.printStackTrace();       
                }       
                ip= inet.getHostAddress();       
            }    
        }     
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割    
        if(ip != null && ip.length() > 15){      
            if(ip.indexOf(",")>0){       
                ip = ip.substring(0,ip.indexOf(","));       
            }       
        }       
        return ip;     
	} 
}
