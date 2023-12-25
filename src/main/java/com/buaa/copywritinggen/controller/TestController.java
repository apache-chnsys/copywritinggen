package com.buaa.copywritinggen.controller;

import com.buaa.copywritinggen.VO.ResponseResult;
import com.buaa.copywritinggen.VO.StrGenQry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author jiangxintian
 * @Date 2023/11/7 11:56
 * @PackageName:com.buaa.copywritinggen.controller
 * @ClassName: testController
 * @Description: 如有bug，吞粪自尽
 */
@Tag(name = "/test")
@RestController
@RequestMapping("/test")
public class TestController {

    private static PythonInterpreter interpreter = new PythonInterpreter();// 执行py文件
    static {
        interpreter.exec("a=1");
    }
    @Operation(summary = "正式的生成接口")
    @PostMapping("/pro")
    public ResponseResult<String> genPro() {
        interpreter.exec("a=a+1");
        interpreter.exec("print(a)");
        PyObject result = interpreter.get("a");
        return ResponseResult.success("成功", result.toString());
    }

    @Operation(summary = "This method is used to get the clients.")
    @GetMapping("/test2")
    public String getTest() {
        return "hello";
    }
}
