package io.renren.modules.attend.dto.subject;

import lombok.Data;

/**
 * @ClassName PhotosDTO
 * @Description TODO
 * @Author Finder
 * @Date 2019/8/11 17:31
 **/
@Data
public class PhotosDTO {
    private int company_id;
    private int id;
    private double quality;
    private int subject_id;
    private String url;
    private int version;
}
