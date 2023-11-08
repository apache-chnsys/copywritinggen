package com.buaa.copywritinggen.service;

import org.python.util.PythonInterpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author jiangxintian
 * @Date 2023/11/7 20:17
 * @PackageName:com.buaa.copywritinggen.service
 * @ClassName: JavaRunPython
 * @Description: 如有bug，吞粪自尽
 */
public class JavaRunPython {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Process proc;
        try {
            proc = Runtime.getRuntime().exec("/Library/Frameworks/Python.framework/Versions/3.9/bin/python3.9 /Users/jiangxintian/PycharmProjects/pythontest1/copywritinggen/test.py");// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            BufferedReader error = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String errorLine = null;
            while ((errorLine = error.readLine()) != null) {
                System.out.println(errorLine);
            }
            error.close();
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

}
