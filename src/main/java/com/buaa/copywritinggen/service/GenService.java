package com.buaa.copywritinggen.service;

import com.buaa.copywritinggen.selfEnum.CopywritingEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


@Service
public class GenService {

    @Value("${python.path}")
    private String pythonPath;

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Autowired
    private Speech2TextService speech2TextService;

    /**
     * 根据文字生成诗词
     * @param text
     * @return
     */
    public String genByText(String text, Integer type, Process proc){
        StringBuilder res = new StringBuilder("文案结果：");
        String image = null;
        try {
            StringBuilder stringBuilder
                    = new StringBuilder().append(pythonPath)
                    .append(" ")
                    .append("/Users/jiangxintian/PycharmProjects/pythontest1/copywritinggen/peom_evaluate2.py")
                    .append(" ")
                    .append(text)
                    .append(" ")
                    .append(CopywritingEnum.getDesc(type));
            proc = Runtime.getRuntime().exec(stringBuilder.toString());// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                res.append(line);
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
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        System.out.println(res.toString());
        return res.toString();
    }

    /**
     * 根据语音生成诗词
     * @param text
     * @return
     */
    public String genByAudio(String text, Integer type, Process proc, MultipartFile file){
        //将语音转换为文字，然后再调用文字生成
        try{
            speech2TextService.asr(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String res = genByText(text, type, proc);
        return res;
    }

    /**
     * 根据图片生成诗词
     * @param text
     * @return
     */
    public String genByPicture(String text, Integer type, Process proc, MultipartFile file){
        StringBuilder res = new StringBuilder("文案结果：");
        // 检查文件是否为空
        if (file.isEmpty()) {
            return "No image file received";
        }
        try {
            // 创建上传目录（如果不存在）
            File tmpFile = new File(uploadDirectory);
            if (!tmpFile.exists()) {
                tmpFile.mkdirs();
            }

            // 生成文件名
            String fileName = file.getOriginalFilename();

            // 构建目标文件路径
            Path targetPath = Path.of(tmpFile.getAbsolutePath(), fileName);

            // 将文件保存到目标路径
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            StringBuilder stringBuilder
                    = new StringBuilder().append(pythonPath)
                    .append(" ")
                    .append("/Users/jiangxintian/PycharmProjects/pythontest1/copywritinggen/peom_evaluate1.py")
                    .append(" ")
                    .append(text)
                    .append(" ")
                    .append(CopywritingEnum.getDesc(type))
                    .append(" ")
                    .append(targetPath.toString());
            proc = Runtime.getRuntime().exec(stringBuilder.toString());// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                res.append(line);
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
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        System.out.println(res.toString());
        return res.toString();
    }


    public static void main(String[] args) {
        StringBuilder res = new StringBuilder("文案结果：");
        Process proc;// 执行py文件
        try {
            StringBuilder stringBuilder
                    = new StringBuilder().append("/Library/Frameworks/Python.framework/Versions/3.9/bin/python3.9")
                    .append(" ")
                    .append("/Users/jiangxintian/PycharmProjects/pythontest1/copywritinggen/peom_evaluate2.py")
                    .append(" ")
                    .append("text")
                    .append(" ")
                    .append("type");
            proc = Runtime.getRuntime().exec(stringBuilder.toString());// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                res.append(line);
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
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        System.out.println(res.toString());
    }
}
