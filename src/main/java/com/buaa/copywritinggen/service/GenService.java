package com.buaa.copywritinggen.service;

import com.buaa.copywritinggen.selfEnum.CopywritingEnum;

import com.buaa.copywritinggen.util.SpeechUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public String genByTextToText(String text, Integer type, Process proc){
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
     * 根据文字生成诗词图片
     * @param text
     * @return
     */
    public ResponseEntity<Resource> genByTextToPicture(String text, Integer type, Process proc){
        StringBuilder res = new StringBuilder("文案结果：");
        String image = null;
        String resPath = uploadDirectory + "/res" + System.currentTimeMillis() + ".png";
        try {
            StringBuilder stringBuilder
                    = new StringBuilder().append(pythonPath)
                    .append(" ")
                    .append("/Users/jiangxintian/PycharmProjects/pythontest1/copywritinggen/peom_evaluate3.py")
                    .append(" ")
                    .append(text)
                    .append(" ")
                    .append(CopywritingEnum.getDesc(type))
                    .append(" ")
                    .append(resPath);
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
        // 获取文件路径
        Path filePath = Paths.get(resPath);

        try {
            // 创建文件资源
            Resource resource = new UrlResource(filePath.toUri());

            // 检查文件是否存在并可读
            if (resource.exists() && resource.isReadable()) {
                // 设置响应头
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=filename.ext");

                // 返回文件资源作为响应
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println(res.toString());
        // 文件不存在或不可读
        return ResponseEntity.notFound().build();
    }

    /**
     * 根据文字生成诗词图片
     * @param text
     * @return
     */
    public ResponseEntity<Resource> genByTextToAudio(String text, Integer type, Process proc){
        StringBuilder res = new StringBuilder("文案结果：");
        String image = null;
        String resPath = uploadDirectory + "/res" + System.currentTimeMillis() + ".mp3";
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
        byte[] bytes = SpeechUtil.synByByte(res.toString(), SpeechUtil.init_client(), resPath);
        // 获取文件路径
        Path filePath = Paths.get(resPath);
        try {
            // 创建文件资源
            Resource resource = new UrlResource(filePath.toUri());

            // 检查文件是否存在并可读
            if (resource.exists() && resource.isReadable()) {
                // 设置响应头
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=filename.ext");

                // 返回文件资源作为响应
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println(res.toString());
        // 文件不存在或不可读
        return ResponseEntity.notFound().build();
    }

    /**
     * 根据语音生成诗词
     * @param text
     * @return
     */
    public String genByAudioToText(String text, Integer type, Process proc, MultipartFile file){
        //将语音转换为文字，然后再调用文字生成
        try{
            speech2TextService.asr(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String res = genByTextToText(text, type, proc);
        return res;
    }

    /**
     * 根据语音识别文字
     * @param file
     * @return
     */
    public String asrByAudioToText(MultipartFile file){
        String asr = null;
        //将语音转换为文字，然后再调用文字生成
        try{
            validateWavFile(file);
            Path tempDir = Files.createTempDirectory("temp_audio");
            Path filePath = tempDir.resolve("recording.wav");
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            // 进行采样率转换
            byte[] convertedAudioData = convertAudioFormat(filePath, 16000);

            asr = speech2TextService.asr(convertedAudioData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return asr;
    }

    private boolean validateWavFile(MultipartFile file) throws IOException, UnsupportedAudioFileException {
        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(file.getBytes()))) {
            AudioFormat audioFormat = audioInputStream.getFormat();
            // 验证WAV文件是否为PCM编码
            if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
                return false;
            }
            // 其他验证逻辑可以根据需要添加
        }
        return true;
    }

    private byte[] convertAudioFormat(Path audioFilePath, int targetSampleRate) throws IOException, UnsupportedAudioFileException {
        // 读取原始 WAV 文件
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFilePath.toFile());

        // 获取原始音频流的格式
        AudioFormat sourceFormat = audioInputStream.getFormat();

        // 创建目标音频格式（更改采样率）
        AudioFormat targetFormat = new AudioFormat(
                sourceFormat.getEncoding(),
                targetSampleRate,
                sourceFormat.getSampleSizeInBits(),
                sourceFormat.getChannels(),
                sourceFormat.getFrameSize(),
                targetSampleRate,
                sourceFormat.isBigEndian());

        // 进行采样率转换
        AudioInputStream convertedAudioStream = AudioSystem.getAudioInputStream(targetFormat, audioInputStream);

        // 从转换后的音频流中读取数据
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = convertedAudioStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        // 关闭音频流
        convertedAudioStream.close();
        audioInputStream.close();

        return outputStream.toByteArray();
    }

    /**
     * 根据图片生成诗词
     * @param text
     * @return
     */
    public String genByPictureToText(String text, Integer type, Process proc, MultipartFile file){
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
