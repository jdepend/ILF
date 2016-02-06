package com.qeweb.framework.common.map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * google map 代理
 *
 */
public class HttpRequestProxy {
    /**
     * <pre>
     * 发送带参数的POST的HTTP请求
     * </pre>
     *
     * @param reqUrl HTTP请求URL
     * @param parameters 参数映射表
     * @return HTTP响应的字符串
     */
	public static String doPost(String reqUrl, String sb, String recvEncoding){
        HttpURLConnection url_con = null;
        String responseContent = null;
        try {
            StringBuilder params = new StringBuilder();
            if (params.length() > 0)
                params = params.deleteCharAt(params.length() - 1);

            URL url = new URL(reqUrl);
            url_con = (HttpURLConnection) url.openConnection();
            url_con.setRequestMethod("POST");
            url_con.setRequestProperty("Content-type","application/json");
            url_con.setRequestProperty("Connection",   "close");
            url_con.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729) ");
            url_con.setRequestProperty("www.google.com","www.google.com");
            url_con.setDoOutput(true);
            byte[] b = sb.toString().getBytes();
            url_con.getOutputStream().write(b, 0, b.length);
            url_con.getOutputStream().flush();
            url_con.getOutputStream().close();

            InputStream in = url_con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
            String tempLine = rd.readLine();
            StringBuffer tempStr = new StringBuffer();
            while (tempLine != null) {
                tempStr.append(tempLine);
                tempLine = rd.readLine();
            }
            responseContent = tempStr.toString();
            rd.close();
            in.close();
        }
        catch (IOException e){
            //logger.error("网络故障", e);
        }
        finally{
            if (url_con != null)
                url_con.disconnect();
        }
        return responseContent;
    }
}
