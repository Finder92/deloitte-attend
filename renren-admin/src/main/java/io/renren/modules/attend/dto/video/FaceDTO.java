package io.renren.modules.attend.dto.video;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @ClassName FaceDTO
 * @Description TODO
 * @Author Finder
 * @Date 2019/7/30 18:15
 **/
@Data
public class FaceDTO {
    private String image;
    @JsonProperty("rect")
    private RectDTO rect;

}
