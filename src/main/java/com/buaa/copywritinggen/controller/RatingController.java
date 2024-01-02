package com.buaa.copywritinggen.controller;

import com.buaa.copywritinggen.VO.RatingQuery;
import com.buaa.copywritinggen.VO.ResponseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author jiangxintian
 * @Date 2024/1/2 17:09
 * @PackageName:com.buaa.copywritinggen.controller
 * @ClassName: RatingController
 * @Description: 如有bug，吞粪自尽
 */
@RestController
@RequestMapping("/Rating")
public class RatingController {

    @Value("${rating.directory}")
    private String FILE_PATH;

//    private static final String FILE_PATH = "/Users/jiangxintian/IdeaProjects/copywritinggen/src/main/resources/ratings.txt";

    @PostMapping("/saveRating")
    public ResponseResult<String> saveRating(@RequestBody RatingQuery ratingQuery) {
        List<RatingQuery> ratings = new ArrayList<>();

        // 读取现有的评分数据
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] ratingData = line.split(",");
                if (ratingData.length == 3) {
                    RatingQuery rating = new RatingQuery(ratingData[0], Double.parseDouble(ratingData[1]), ratingData[2]);
                    ratings.add(rating);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.error("失败");
        }

        // 检查是否存在相同用户ID的评分
        boolean ratingExists = false;
        for (RatingQuery rating : ratings) {
            if (rating.getUserId().equals(ratingQuery.getUserId())) {
                ratingExists = true;
                ratings.remove(rating);
                break;
            }
        }

        // 添加新的评分数据
        ratings.add(new RatingQuery(ratingQuery.getUserId(), ratingQuery.getRating(), ratingQuery.getComment()));

        // 保存评分数据到文件
        try (FileWriter fileWriter = new FileWriter(FILE_PATH, false)) {
            for (RatingQuery rating : ratings) {
                String ratingData = rating.getUserId() + "," + rating.getRating() + "," + rating.getComment() + "\n";
                fileWriter.write(ratingData);
            }
            return ResponseResult.success("成功");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.error("失败");
        }
    }

    // 评分保存接口省略...

    @GetMapping("/getRatings")
    public ResponseResult<List<RatingQuery>> getRatings() {
        List<RatingQuery> ratings = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] ratingData = line.split(",");
                if (ratingData.length == 3) {
                    RatingQuery rating = new RatingQuery(ratingData[0], Double.parseDouble(ratingData[1]), ratingData[2]);
                    ratings.add(rating);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseResult.success("成功", ratings);
    }
}
