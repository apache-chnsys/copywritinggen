//package com.buaa.copywritinggen.controller;
//
//import com.buaa.copywritinggen.VO.ResponseResult;
//import com.buaa.copywritinggen.VO.StrGenQry;
//import com.buaa.copywritinggen.service.Speech2TextService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
///**
// * @Author jiangxintian
// * @Date 2023/11/7 11:56
// * @PackageName:com.buaa.copywritinggen.controller
// * @ClassName: testController
// * @Description: 如有bug，吞粪自尽
// */
//@Tag(name = "/Speech2Text")
//@RestController
//@RequestMapping("/Speech2Text")
//public class Speech2TextController {
//
//    @Autowired
//    private Speech2TextService speech2TextService;
//
//    @Operation(summary = "使用文字生成")
//    @PostMapping("/upload-audio")
//    public String uploadAudio(@RequestParam("file") MultipartFile file) {
//        // 处理语音文件逻辑
//        // 可以通过 file.getInputStream() 获取文件的输入流，进行进一步的处理
//        // 例如保存文件、解析语音内容等
//        try{
//            speech2TextService.asr(file.getBytes());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "文件上传成功";
//    }
//
//    @Operation(summary = "使用文字生成并且调用算法生成诗词")
//    @PostMapping("/SpeechGen")
//    public String SpeechGen(@RequestPart("file") MultipartFile file) {
//        // 处理语音文件逻辑
//        // 可以通过 file.getInputStream() 获取文件的输入流，进行进一步的处理
//        // 例如保存文件、解析语音内容等
//        try{
//            speech2TextService.asr(file.getBytes());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "文件上传成功";
//    }
//}
