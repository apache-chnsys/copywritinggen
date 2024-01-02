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
        try (FileWriter fileWriter = new FileWriter(FILE_PATH, true)) {
            String ratingData = ratingQuery.getUserId() + "," + ratingQuery.getRating() + "," + ratingQuery.getComment() + "\n";
            fileWriter.write(ratingData);
            return ResponseResult.success("成功", null);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.success("失败", null);
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
