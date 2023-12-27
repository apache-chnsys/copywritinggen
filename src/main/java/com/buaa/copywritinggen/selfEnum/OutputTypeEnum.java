package com.buaa.copywritinggen.selfEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author jiangxintian
 * @Date 2023/12/16 17:23
 * @PackageName:com.buaa.copywritinggen.test
 * @ClassName: InputTypeEnum
 * @Description: 如有bug，吞粪自尽
 */
@Getter
@AllArgsConstructor
public enum OutputTypeEnum {
    /**
     * 图片 1
     */
    PICTURE(1, "图片"),

    /**
     * 语音 2
     */
    AUDIO(2, "语音"),

    /**
     * 文字 3
     */
    TEXT(3, "语音");

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

        for (OutputTypeEnum copywritingEnum : OutputTypeEnum.values()) {
            if (copywritingEnum.getCode().equals(code)) {
                return copywritingEnum.getDesc();
            }
        }
        //todo 不返回空值
        return null;
    }

}
