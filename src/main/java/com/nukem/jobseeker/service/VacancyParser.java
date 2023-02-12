package com.nukem.jobseeker.service;

import com.nukem.jobseeker.model.dto.VacancyDto;

/**
 * This class parses given element to {@link VacancyDto}
 *
 * @param <T>
 */
public abstract class VacancyParser<T> {

    public VacancyDto parse(T t) {
        final VacancyDto vacancyDto = VacancyDto.builder()
                .date(getVacancyDate(t))
                .title(getVacancyTitle(t))
                .description(getVacancyDescription(t))
                .link(getVacancyLink(t))
                .location(getLocation(t))
                .companyLogo(getCompanyLogo(t))
                .companyName(getCompanyName(t))
                .placeholderLogo(getPlaceholderLogo())
                .build();
        vacancyDto.generateId();
        return vacancyDto;
    }

    protected abstract String getPlaceholderLogo();

    protected abstract String getVacancyDate(T t);

    protected abstract String getVacancyTitle(T t);

    protected abstract String getVacancyDescription(T t);

    protected abstract String getVacancyLink(T t);

    protected abstract String getCompanyLogo(T t);

    protected abstract String getLocation(T t);

    protected abstract String getCompanyName(T t);
}
