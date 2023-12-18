package com.buaa.copywritinggen.controller;

import com.buaa.copywritinggen.VO.ResponseResult;
import com.buaa.copywritinggen.VO.StrGenQry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.Base64.*;
import java.util.List;

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
}
