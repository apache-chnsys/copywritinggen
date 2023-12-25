package com.buaa.copywritinggen.service;

import com.buaa.copywritinggen.VO.ResponseResult;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;

@Service
public class GenService {


    /**
     * 根据文字生成诗词
     * @param text
     * @return
     */
    public String genByText(String text){
        String keyWord = "请根据以下关键词：｛keyword｝生成｛type｝";
        Process proc;
        StringBuilder res = new StringBuilder("文案结果：");
        String image = null;
        try {
            proc = Runtime.getRuntime().exec("/Library/Frameworks/Python.framework/Versions/3.9/bin/python3.9 /Users/jiangxintian/PycharmProjects/pythontest1/copywritinggen/test.py");// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            res.append(line);
            BufferedReader error = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String errorLine = null;
            while ((errorLine = error.readLine()) != null) {
                System.out.println(errorLine);
            }
            error.close();
            in.close();
            proc.waitFor();

            //图片获取
            byte [] imageByte = new byte[100];
            Base64.Encoder encoder = Base64.getEncoder();
            image = encoder.encodeToString(imageByte);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(e);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        System.out.println(res.toString());
        return keyWord;
    }
}
