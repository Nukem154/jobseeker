package com.nukem.jobseeker.model.dto.robota;

import lombok.Data;

@Data
public class FilterJson {
    private String keywords;
    private String[] scheduleIds;
    private String cityId;
}
