package com.buaa.copywritinggen.controller;

import com.buaa.copywritinggen.VO.ResponseResult;
import com.buaa.copywritinggen.VO.StrGenQry;
import com.buaa.copywritinggen.selfEnum.InputTypeEnum;
import com.buaa.copywritinggen.service.GenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.python.core.Py;
import org.python.core.PySystemState;
import org.python.indexer.ast.NPass;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.Base64.*;
import java.util.Properties;


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


    @Autowired
    private GenService genService;

    @Operation(summary = "正式的生成接口")
    @PostMapping("/proByText")
    public ResponseResult<String> genProByText(@RequestBody StrGenQry query) {
        // 根据输入类型调用不同的生成方法
        // 根据文字生成
        String res = genService.genByText(query.getUserText(), query.getGenType(), proc);
        return ResponseResult.success("成功", res);
    }

    @Operation(summary = "正式的生成接口")
    @PostMapping("/proByAudio")
    public ResponseResult<String> genProByAudio(@RequestBody StrGenQry query, @RequestParam("file") MultipartFile file) {
        // 根据输入类型调用不同的生成方法
        String res = null;
        // 根据图片生成
        if(InputTypeEnum.PICTURE.getCode().equals(query.getInputType())) {
            res = genService.genByAudio(query.getUserText(), query.getGenType(), proc, file);
        }
        else {
            // 根据语音生成
            res = genService.genByAudio(query.getUserText(), query.getGenType(), proc, file);
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

