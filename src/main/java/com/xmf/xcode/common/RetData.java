
package com.xmf.xcode.common;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName:RetData (返回数据对象模型)
 * Date:     2017年1月15日 下午3:37:22
 *
 * @author rufei.cn
 * @Version 1.0
 * @see
 */
@Data
@SuppressWarnings("all")
public class RetData<T> implements Serializable {

    /**
     * 状态码
     */
    private int code = ResultCodeMessage.PARMS_ERROR;
    /**
     * 消息
     */
    private String message = ResultCodeMessage.PARMS_ERROR_MESSAGE;
    /**
     * 数据类型
     */
    private T data;
}
