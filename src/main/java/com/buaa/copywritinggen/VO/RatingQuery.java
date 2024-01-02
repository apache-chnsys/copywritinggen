package com.buaa.copywritinggen.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author jiangxintian
 * @Date 2024/1/2 17:15
 * @PackageName:com.buaa.copywritinggen.VO
 * @ClassName: RatingQuery
 * @Description: 如有bug，吞粪自尽
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RatingQuery {
    private String userId;
    private double rating;
    private String comment;


}
