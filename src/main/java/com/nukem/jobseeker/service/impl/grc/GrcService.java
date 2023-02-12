package com.nukem.jobseeker.service.impl.grc;

import com.nukem.jobseeker.model.dto.VacancyDto;
import com.nukem.jobseeker.service.JobFinder;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GrcService implements JobFinder {
    private final Logger logger = LoggerFactory.getLogger(GrcService.class);
    private final RestTemplate restTemplate;

    public static void main(String[] args) {
        GrcService grcService = new GrcService(new RestTemplate());
        System.out.println(grcService.loadDataFromGrcUa(null));
    }

    @Override
    public List<VacancyDto> findVacancies(final Map<String, String> params) {
        return new ArrayList<>();
    }

    private String loadDataFromGrcUa(final Map<String, String> params) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<String> request = new HttpEntity<>(null, headers);

        try {
            return restTemplate.postForObject("https://api.grc.ua/api/vacancy/search", request, String.class);
        } catch (RestClientException e) {
            logger.error("Couldn't retrieve data from grc.ua: {}", e.getMessage());
            return Strings.EMPTY;
        }
    }

}
