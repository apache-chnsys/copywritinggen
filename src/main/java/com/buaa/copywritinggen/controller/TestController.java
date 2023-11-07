package com.buaa.copywritinggen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Operation(summary = "This method is used to get the clients.")
    @PostMapping("/test1")
    public String findPage() {
        return "hellp";
    }

    @Operation(summary = "This method is used to get the clients.")
    @GetMapping("/test2")
    public String getTest() {
        return "hello";
    }
}
