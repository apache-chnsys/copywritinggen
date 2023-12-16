package com.buaa.copywritinggen.VO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 事前分流实验请求对象
 * @author sunzhen10
 * @date 2022/7/21 15:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StrGenQry {

    /**
     * 生成类型
     */
    @NotNull
    private String genType;


    /**
     * 用户文字
     */
    private Integer userText;
}
