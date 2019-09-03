package io.renren.modules.attend.dto.history;
import lombok.Data;
/**
 * @ClassName PhotosBean
 * @Description TODO
 * @Author Finder
 * @Date 2019/7/29 13:22
 **/
@Data
public class PhotosDto {
    private int company_id;
    private int id;
    private double quality;
    private int subject_id;
    private String url;
    private int version;
}
