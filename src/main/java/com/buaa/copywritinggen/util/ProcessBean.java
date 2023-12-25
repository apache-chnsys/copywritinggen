package com.buaa.copywritinggen.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProcessBean {

    private static Process proc;// 执行py文件

    static {
        try {
            proc = Runtime.getRuntime().exec("C:\\Users\\admin\\AppData\\Local\\Programs\\Python\\Python39\\python " +
                    "C:\\Users\\admin\\PycharmProjects\\project_test\\main.py");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
