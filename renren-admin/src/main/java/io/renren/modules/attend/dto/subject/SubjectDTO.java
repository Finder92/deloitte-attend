package io.renren.modules.attend.dto.subject;

import lombok.Data;

/**
 * @ClassName SubjectDTO
 * @Description TODO
 * @Author Finder
 * @Date 2019/8/11 17:20
 **/
@Data
public class SubjectDTO {
    private int code;
    private SubjectDataDTO data;
    private PageDTO page;

}
