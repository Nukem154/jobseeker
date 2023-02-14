package com.nukem.jobseeker.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class VacancyDto {
    private Integer id;
    private LocalDate date;
    private String title;
    private String description;
    private String link;
    private String placeholderLogo;
    private String companyName;
    private String companyLogo;
    private String location;
}
