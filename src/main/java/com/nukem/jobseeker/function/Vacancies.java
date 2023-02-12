//package com.nukem.jobseeker.function;
//
//import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
//import com.nukem.jobseeker.model.dto.VacancyDto;
//import com.nukem.jobseeker.service.impl.VacancyService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//import java.util.function.Function;
//
//@Configuration
//public class Vacancies {
//
//    private final VacancyService vacancyService;
//
//    public Vacancies(VacancyService vacancyService) {
//        this.vacancyService = vacancyService;
//    }
//
//    @Bean
//    public Function<APIGatewayProxyRequestEvent, List<VacancyDto>> getVacancies() {
//        return (message) -> vacancyService.findVacanciesFromAllResources(message.getQueryStringParameters());
//    }
//}
