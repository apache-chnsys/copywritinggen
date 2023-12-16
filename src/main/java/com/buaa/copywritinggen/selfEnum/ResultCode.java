package com.buaa.copywritinggen.selfEnum;

/**
 * @Author jiangxintian
 * @Date 2023/12/16 17:50
 * @PackageName:com.buaa.copywritinggen.selfEnum
 * @ClassName: ResultCode
 * @Description: 如有bug，吞粪自尽
 */
public enum ResultCode {

    SUCCESS(200, "ok"),
    ERROR(500, "服务端错误"),
    NO_PERMISSION(403, "无权限"),
    NOT_LOGIN(401, "未登陆");

    Integer code;
    String msg;

    private ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
