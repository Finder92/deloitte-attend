package io.renren.modules.attend.dto.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * @ClassName DataBean
 * @Description TODO
 * @Author Finder
 * @Date 2019/7/29 13:20
 **/
@Data
public class DataDto {
    private double age;
    private int company_id;
    private double confidence;
    private int event_type;
    private int fmp;
    private boolean fmp_error;
    private double gender;
    private int group;
    private int id;
    private String photo;
    private double quality;
    @JsonProperty("screen")
    private ScreenDto screen;
    @JsonProperty("subject")
    private SubjectDto subject;
    private int subject_id;
    private int timestamp;
    private String uuid;
}
