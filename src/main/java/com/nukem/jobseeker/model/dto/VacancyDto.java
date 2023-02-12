package com.nukem.jobseeker.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VacancyDto {
    private Integer id;
    private String date;
    private String title;
    private String description;
    private String link;
    private String placeholderLogo;
    private String companyName;
    private String companyLogo;
    private String location;

    public void generateId() {
        id = hashCode();
    }
}
