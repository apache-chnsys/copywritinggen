package com.buaa.copywritinggen.selfEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @Author jiangxintian
 * @Date 2023/12/16 17:23
 * @PackageName:com.buaa.copywritinggen.test
 * @ClassName: CopywritingEnum
 * @Description: 如有bug，吞粪自尽
 */
@Getter
@AllArgsConstructor
public enum CopywritingEnum {
    /**
     * 绝句 1
     */
    QUATRAIN_1(1, "绝句"),

    /**
     * 律诗 2
     */
    METRICAL(2, "绝句"),

    /**
     * 元曲 3
     */
    YUAN_DYNASTY(3, "元曲"),

    /**
     * 风格绝句 4
     */
    QUATRAIN_2(4, "风格绝句"),

    /**
     * 词 5
     */
    SPEECH(5, "词"),

    /**
     * 对联 6
     */
    ANTITHETICAL(6, "对联"),

    /**
     * 朋友圈文案 7
     */
    FRIENDS_COPYWRITING(7, "朋友圈文案"),

    /**
     * 朋友圈标题 8
     */
    TITLE(8, "朋友圈标题"),

    /**
     * 小红书文案 9
     */
    XIAOHONGSHU(9, "小红书文案");

    /**
     * 标识码
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;

    /**
     * 获取名称
     *
     * @param code
     * @return
     */
    public static String getDesc(Integer code) {

        for (CopywritingEnum copywritingEnum : CopywritingEnum.values()) {
            if (copywritingEnum.getCode().equals(code)) {
                return copywritingEnum.getDesc();
            }
        }
        //todo 不返回空值
        return null;
    }

}
