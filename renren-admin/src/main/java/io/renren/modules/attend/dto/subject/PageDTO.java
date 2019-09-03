package io.renren.modules.attend.dto.subject;

import lombok.Data;

/**
 * @ClassName PageDTO
 * @Description TODO
 * @Author Finder
 * @Date 2019/8/11 17:43
 **/
@Data
public class PageDTO {
    private int count;
    private int current;
    private int size;
    private int total;
}
