package utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Map;

public class httpUtils {
    private static URL url;
    private static String target = "http://82.157.77.166:80";

    public static String httpPost(String path, JSONObject params, String encode) throws Exception {
        String real_url = target.concat(path);
        StringBuffer str_buffer = new StringBuffer();
        try{
            //创建http client
            url = new URL(real_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("content-type", "application/json;charset=utf-8");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // 定义输入输出流
            OutputStream out = connection.getOutputStream();
            System.out.println("params" + params.toString());
            out.write(params.toString().getBytes());

            //发送请求
            int code = connection.getResponseCode();
            if(code == 200){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String read_str;
                while((read_str=reader.readLine())!=null){
                    read_str = new String(read_str.getBytes(), "utf-8");
                    str_buffer.append(read_str);
                }
                reader.close();
                connection.disconnect();
            }
            System.out.println("return original result:" + str_buffer.toString());
            return str_buffer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
}
