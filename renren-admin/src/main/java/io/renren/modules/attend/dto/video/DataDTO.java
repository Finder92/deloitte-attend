package io.renren.modules.attend.dto.video;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @ClassName DataDTO
 * @Description TODO
 * @Author Finder
 * @Date 2019/7/30 18:15
 **/
@Data
public class DataDTO {
    private String status;
    private int track;
    private String timestamp;
    @JsonProperty("face")
    private FaceDTO face;
    @JsonProperty("person")
    private PersonDTO person;
    private double quality;

}
