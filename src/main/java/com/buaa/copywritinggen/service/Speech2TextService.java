package com.buaa.copywritinggen.service;

import com.baidu.aip.speech.AipSpeech;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import org.json.simple.parser.JSONParser;
import java.io.IOException;

import static com.buaa.copywritinggen.util.SpeechUtil.*;

/**
 * @Author jiangxintian
 * @Date 2023/12/22 16:16
 * @PackageName:com.buaa.copywritinggen.service
 * @ClassName: Speech2TextService
 * @Description: 如有bug，吞粪自尽
 */
@Service
public class Speech2TextService {

    public String asr(byte[] data) throws IOException {
        // 初始化clien
        AipSpeech aipSpeech = init_client();
        // 使用client进行语音识别
        JSONObject jsonObject = asrByByte(data, aipSpeech);

        JSONArray resultArray = (JSONArray) jsonObject.get("result");
        String result = (String) resultArray.get(0);

        String errMsg = (String) jsonObject.get("err_msg");

        System.out.println("Result: " + result);
        System.out.println("Error Message: " + errMsg);
        return result;
    }
}
