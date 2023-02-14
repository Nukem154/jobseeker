package com.nukem.jobseeker.service.impl;

import com.nukem.jobseeker.model.dto.VacancyDto;
import com.nukem.jobseeker.service.JobFinder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nukem.jobseeker.config.CacheConfig.VACANCIES;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final List<JobFinder> jobFinders;

    @Cacheable(VACANCIES)
    public List<VacancyDto> findVacanciesFromAllResources(final Map<String, String> params) {
        return jobFinders.stream()
                .flatMap(jobFinder -> jobFinder.findVacancies(params).stream())
                .sorted(Comparator.comparing(VacancyDto::getDate, Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

}
