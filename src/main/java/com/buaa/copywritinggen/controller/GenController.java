package com.buaa.copywritinggen.controller;

import com.alibaba.fastjson.JSONObject;
import com.buaa.copywritinggen.VO.GenQuery;
import com.buaa.copywritinggen.VO.ResponseResult;
import com.buaa.copywritinggen.VO.StrGenQry;
import com.buaa.copywritinggen.selfEnum.CopywritingEnum;
import com.buaa.copywritinggen.selfEnum.InputTypeEnum;
import com.buaa.copywritinggen.selfEnum.OutputTypeEnum;
import com.buaa.copywritinggen.service.GenService;
import com.buaa.copywritinggen.service.Speech2TextService;
import com.buaa.copywritinggen.util.HttpClientUtils;
import com.buaa.copywritinggen.util.SpeechUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.Base64.Encoder;

/**
 * @Author jiangxintian
 * @Date 2023/11/7 11:56
 * @PackageName:com.buaa.copywritinggen.controller
 * @ClassName: testController
 * @Description: 如有bug，吞粪自尽
 */
@Tag(name = "/Gen")
@RestController
@RequestMapping("/Gen")
public class GenController {

    @Autowired
    private GenService genService;

    @Autowired
    private Speech2TextService speech2TextService;


    @Operation(summary = "使用文字生成")
    @PostMapping("/test1")
    public ResponseResult<String> findPage(@RequestBody StrGenQry query) {
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
            Encoder encoder = Base64.getEncoder();
            image = encoder.encodeToString(imageByte);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
            return ResponseResult.success("成功", "如梦令\n" +
                    "大雪飞来\n" +
                    "人未醉，心先醉\n" +
                    "坐间一觉，觉后千回");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(e);
        } catch (Exception e) {
            return ResponseResult.error("接口调用失败");
        }
        System.out.println(res.toString());
        return ResponseResult.success("成功", res.toString(),image);
    }

    @Operation(summary = "整合生成接口")
    @PostMapping("/multiGen")
    public ResponseResult<String> multiGen(GenQuery genQuery, @RequestParam(value = "audio",required = false) MultipartFile audio) {
        ResponseResult responseResult = new ResponseResult();
        StringBuilder res = new StringBuilder("文案结果：");
        System.out.println("---"+genQuery.toString());
        //根据flag判断
        switch (genQuery.getFlag()){
            case "1":{
                //既没有图片也没有音频
                try {
                    responseResult = genWord(genQuery, res);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(e);
                }
                break;
            }
            case "3":{
                //有音频没有图片
               String ret = genService.asrByAudioToText(audio);
               System.out.println("Audio:"+ret);
               genQuery.setUserText(ret);
                try {
                    responseResult = genWord(genQuery,res);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(e);
                }
            }
            case "2":{
                //有图片没有音频
                try {
                    String keyword = genImageStr(genQuery);
                    genQuery.setUserText(keyword);
                    responseResult = genWord(genQuery,res);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            default:{
                try {
                    //图片声音都存在
                    String ret = genService.asrByAudioToText(audio);
                    System.out.println("Audio:"+ret);
                    String keyword = genImageStr(genQuery);
                    System.out.println("image:"+keyword);
                    String text = ret + "以及" + keyword;
                    genQuery.setUserText(text);
                    responseResult = genWord(genQuery,res);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return responseResult;
    }

    @Operation(summary = "文字生成语音")
    @PostMapping("/textForAudio")
    public ResponseResult<String> textForAudio(@RequestBody GenQuery genQuery) {
        byte[] bytes = SpeechUtil.synByByte(genQuery.getText(), SpeechUtil.init_client());
        String base64Str = Base64.getEncoder().encodeToString(bytes);
        return ResponseResult.success("成功", base64Str);
    }

    private static String genImageStr(GenQuery genQuery) throws IOException {
        JSONObject req = new JSONObject();
        req.put("image", genQuery.getImage().split(",")[1]);
        //System.out.println("req:"+req.toString());
        JSONObject jsonObject = HttpClientUtils.doPost("http://localhost:5000/image_keyword",req);
        System.out.println("imageJsonRet:"+jsonObject.toString());
        String keyword = (String) jsonObject.get("image_keyword");
        return keyword;
    }

    private static ResponseResult genWord(GenQuery genQuery, StringBuilder res) throws IOException {
        JSONObject req = new JSONObject();
        req.put("query", genQuery.getUserText() + CopywritingEnum.getDesc(Integer.parseInt(genQuery.getGenType())));
        System.out.println("req:"+req.toString());
        JSONObject jsonObject = HttpClientUtils.doPost("http://localhost:5000/generate_poem", req);
        res.append(jsonObject.get("poem"));

        System.out.println("check:"+genQuery.isCheck());
        String imageBase64 = "";
        if(genQuery.isCheck()){
            System.out.println("genPic");
            System.out.println(jsonObject);
            JSONObject jsonObjectPic= HttpClientUtils.doPost("http://localhost:5000/generate_picture", jsonObject);

            imageBase64 = (String) jsonObjectPic.get("picture");
            System.out.println("PicRet64:"+imageBase64);
            return ResponseResult.success("成功", res.toString(),imageBase64);
        }

        return ResponseResult.success("成功", res.toString());
    }


    @Operation(summary = "正式的生成接口")
    @PostMapping("/proByText")
    public ResponseResult<String> genProByText(@RequestBody StrGenQry query) {
        // 根据输入类型调用不同的生成方法
        String res = null;
        // 根据文字生成
        if(OutputTypeEnum.TEXT.getCode().equals(query.getOutputType())) {
            res = genService.genByTextToText(query.getUserText(), query.getGenType(), proc);
        }else if(OutputTypeEnum.PICTURE.getCode().equals(query.getOutputType())) {
            res = genService.genByTextToText(query.getUserText(), query.getGenType(), proc);
        }else {
            res = genService.genByTextToText(query.getUserText(), query.getGenType(), proc);
        }
        // 根据文字生成图片
        return ResponseResult.success("成功", res);
    }

    @Operation(summary = "正式的生成接口")
    @PostMapping("/proByAudio")
    public ResponseResult<String> genProByAudio(@RequestBody StrGenQry query, @RequestParam("file") MultipartFile file) {
        // 根据输入类型调用不同的生成方法
        String res = null;
        // 根据图片生成
        if(InputTypeEnum.PICTURE.getCode().equals(query.getInputType())) {
            res = genService.genByPictureToText(query.getUserText(), query.getGenType(), proc, file);
        }
        else {
            // 根据语音生成
            res = genService.genByAudioToText(query.getUserText(), query.getGenType(), proc, file);
        }
        return ResponseResult.success("成功", res);
    }

    private static Process proc;
//
//    static {
//        try {
////            proc = Runtime.getRuntime().exec("C:\\Users\\admin\\AppData\\Local\\Programs\\Python\\Python39\\python " +
////                    "C:\\Users\\admin\\PycharmProjects\\project_test\\main.py");
//            proc = Runtime.getRuntime().exec
//                    ("/Library/Frameworks/Python.framework/Versions/3.9/bin/python3.9" +
//                            " /Users/jiangxintian/PycharmProjects/pythontest1/copywritinggen/test.py");// 执行py文件
//            //用输入输出流来截取结果
//            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//            String line = null;
//            while ((line = in.readLine()) != null) {
//                System.out.println(line);
//            }
//            in.close();
//            proc.waitFor();
//        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    private static PythonInterpreter interpreter = new PythonInterpreter();// 执行py文件
//    static {
//        // 设置Python路径
//        Properties properties = new Properties();
//        // 修改系统python环境变量
//        // properties.setProperty("python.path", "/Library/Frameworks/Python.framework/Versions/3.9/bin/python3");
//        properties.setProperty("python.path", "/usr/local/bin/python3.9");
//        properties.setProperty("python.home", "/usr/local/bin/python3.9");
//
//        PySystemState sys = Py.getSystemState();
//        System.out.println("系统路径");
//        System.out.println(sys.path);
//        sys.path.clear();
////        sys.path.add("/Library/Frameworks/Python.framework/Versions/3.9/Lib");
//        System.out.println(sys.path);
//        // 初始化Python解释器
//        interpreter.initialize(properties, null, new String[0]);
//        try {
//            interpreter.exec("import sys");
//            interpreter.exec("print(sys.prefix)");
//            interpreter.exec("print(sys.version)");
//            interpreter.exec("path = \"/Library/Frameworks/Python.framework/Versions/3.9/Lib\"");
//            interpreter.exec("sys.path.append(path)");
//            interpreter.exec("print sys.path");
//            interpreter.exec("from transformers import AutoTokenizer, AutoModel");
//            interpreter.exec("import torch");
//            interpreter.exec("model_name_or_path = '../learning/chatglm_6b'");
//            interpreter.exec("from transformers import AutoTokenizer, AutoModel");
//            interpreter.exec("import os");
//            interpreter.exec("os.environ[\"CUDA_VISIBLE_DEVICES\"] = \"1,3\"");
//            interpreter.exec("model_old = AutoModel.from_pretrained(model_name_or_path,load_in_8bit=False,trust_remote_code = True)");
//            interpreter.exec("peft_loaded = PeftModel.from_pretrained(model_old,ckpt_path).cuda()");
//            interpreter.exec("model_new = peft_loaded.merge_and_unload()");
//            interpreter.exec("tokenizer = AutoTokenizer.from_pretrained( model_name_or_path, trust_remote_code=True)");
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println(e);
//        }
    }

