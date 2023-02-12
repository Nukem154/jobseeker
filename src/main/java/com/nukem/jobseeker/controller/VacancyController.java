package com.nukem.jobseeker.controller;

import com.nukem.jobseeker.model.City;
import com.nukem.jobseeker.model.dto.VacancyDto;
import com.nukem.jobseeker.repository.CityRepository;
import com.nukem.jobseeker.service.impl.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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



    /*
        String content = Files.readString(Path.of("D:\\Java Projects\\jobSeeker\\src\\main\\resources\\robotaCities.txt"), StandardCharsets.UTF_8);

        List<Root> list = new Gson().fromJson(content, new TypeToken<List<Root>>(){}.getType());
        list.forEach(System.out::println);
        System.out.println(list.size());
        List<City> cities = list.stream().map(
                o -> {
                    City city = new City();
                    city.setRobotaUaId(o.id);
                    city.setUa(o.ua);
                    city.setEn(o.en);
                    city.setRu(o.ru);
                    return city;
                }).collect(Collectors.toList());
        FileWriter writer = new FileWriter("src\\main\\resources\\cities.txt");
        for(City city: cities) {
            writer.write(city.toJson() + System.lineSeparator());
        }
        writer.close();
*/


//    @ToString
//    class LocativeName {
//        public String ua;
//        public String ru;
//
//    }
//
//    @ToString
//    class RegionName {
//        public String ua;
//        public String ru;
//    }
//
//    @ToString
//    class Root {
//        public int centerId;
//        public Object centerName;
//        public RegionName regionName;
//        public int vacancyCount;
//        public LocativeName locativeName;
//        public int countryId;
//        public int id;
//        public String ru;
//        public String ua;
//        public String en;
//    }
}
