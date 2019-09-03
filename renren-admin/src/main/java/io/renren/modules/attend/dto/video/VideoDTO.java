package io.renren.modules.attend.dto.video;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @ClassName VideoDTO
 * @Description TODO
 * @Author Finder
 * @Date 2019/7/30 17:57
 **/
@Data
public class VideoDTO {
    @JsonProperty("data")
    private DataDTO data;
    @JsonProperty("screen")
    private ScreenDTO screen;
    @JsonProperty("person")
    private PersonDTOX person;
    private String error;
    private boolean open_door;
    private String type;
}
