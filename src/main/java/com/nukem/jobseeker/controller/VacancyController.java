package com.nukem.jobseeker.controller;

import com.nukem.jobseeker.model.City;
import com.nukem.jobseeker.model.dto.VacancyDto;
import com.nukem.jobseeker.repository.CityRepository;
import com.nukem.jobseeker.service.impl.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;
    private final CityRepository cityRepository;


    @GetMapping("/vacancies")
    public List<VacancyDto> getVacancies(@RequestParam(required = false) Map<String, String> params) {
        return vacancyService.findVacanciesFromAllResources(params);
    }

    @GetMapping("/cities")
    public List<City> getCities() {
        return cityRepository.findAll();
    }

}
