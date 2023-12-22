package com.buaa.copywritinggen.util;

import com.baidu.aip.speech.AipSpeech;
import org.json.JSONObject;

/**
 * @Author jiangxintian
 * @Date 2023/12/22 16:46
 * @PackageName:com.buaa.copywritinggen.util
 * @ClassName: SpeechUtil
 * @Description: 如有bug，吞粪自尽
 */
public class SpeechUtil {

    //设置APPID/AK/SK
    public static final String APP_ID = "44698736";
    public static final String API_KEY = "VEkNm9hEG6qSGPaVIU5sm2t4";
    public static final String SECRET_KEY = "lY4Gxi176GbyHi0XuUQ2MTFksTblhw7c";

    public static AipSpeech init_client(){
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
        return client;
    }

    public static void asrByFile(String path, AipSpeech client){
        // 调用接口
        JSONObject res = client.asr(path, "wav", 16000, null);
        System.out.println(res.toString(2));
    }

    public static JSONObject asrByByte(byte[] data, AipSpeech client){
        // 调用接口
        JSONObject asrRes2 = client.asr(data, "wav", 16000, null);
        System.out.println(asrRes2);
        return asrRes2;
    }
}
