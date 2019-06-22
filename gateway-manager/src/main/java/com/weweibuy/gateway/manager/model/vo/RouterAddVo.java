package com.weweibuy.gateway.manager.model.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author durenhao
 * @date 2019/5/19 15:13
 **/
@Data
public class RouterAddVo {

    @NotBlank(message = "routerId不能为空")
    @Length(max = 63, message = "routerId长度不能超过63位")
    private String routerId;

    @NotBlank(message = "systemId不能为空")
    @Length(max = 63, message = "systemId长度不能超过63位")
    private String systemId;

    @NotBlank(message = "systemName不能为空")
    @Length(max = 63, message = "systemName长度不能超过63位")
    private String systemName;

    @NotBlank(message = "uri不能为空")
    @Length(max = 63, message = "uri长度不能超过63位")
    private String uri;

    private Integer priority;

    private Byte status;

}
