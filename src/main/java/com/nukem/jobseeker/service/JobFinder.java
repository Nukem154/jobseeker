package com.nukem.jobseeker.service;

import com.nukem.jobseeker.model.dto.VacancyDto;

import java.util.List;
import java.util.Map;

public interface JobFinder {
    List<VacancyDto> findVacancies(Map<String, String> params);
}
