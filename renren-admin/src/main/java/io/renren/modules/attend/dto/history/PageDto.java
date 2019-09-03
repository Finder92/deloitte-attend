package io.renren.modules.attend.dto.history;

import lombok.Data;

/**
 * @ClassName PageDto
 * @Description TODO
 * @Author Finder
 * @Date 2019/7/29 13:16
 **/
@Data
public class PageDto {
    private int count;
    private int current;
    private int size;
    private int total;

}
