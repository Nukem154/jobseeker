package com.nukem.jobseeker.service.impl.robotaua;

import com.google.gson.Gson;
import com.nukem.jobseeker.constant.URL;
import com.nukem.jobseeker.model.dto.VacancyDto;
import com.nukem.jobseeker.service.JobFinder;
import com.nukem.jobseeker.util.VariablesJsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RobotaUaService implements JobFinder {

//    private final List<String> searchQueryFilterValues = List.of("keywords", "page");

    @Value("${robotaua.graphql.getPublishedVacanciesList}")
    private String graphQlQuery;

    private final RestTemplate restTemplate;
    private final RobotaUaJsonVacancyParser robotaUaJsonParser;
    private final VariablesJsonBuilder variablesJsonBuilder;


    @Override
    public List<VacancyDto> findVacancies(final Map<String, String> params) {
        try {
            final JSONArray jsonArray = new JSONObject(loadDataFromRobotaUa(params))
                    .getJSONObject("data")
                    .getJSONObject("publishedVacancies")
                    .getJSONArray("items");
            return robotaUaJsonParser.fromJsonArray(jsonArray);
        } catch (JSONException e) {
            return Collections.emptyList();
        }
    }

    private String loadDataFromRobotaUa(final Map<String, String> params) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAcceptLanguageAsLocales(Collections.singletonList(new Locale("uk")));
        final HttpEntity<String> request = new HttpEntity<>(buildJsonWithGraphQLQuery(params).toString(), headers);
        try {
            return restTemplate.postForObject(URL.DRACULA_ROBOTA_UA_URL + URL.DRACULA_GET_VACANCIES_QUERY, request, String.class);
        } catch (RestClientException e) {
            log.error("Couldn't retrieve data from {}: {}", URL.DRACULA_ROBOTA_UA_URL, e.getMessage());
            return Strings.EMPTY;
        }
    }

    @SneakyThrows
    private JSONObject buildJsonWithGraphQLQuery(final Map<String, String> params) {
        final String variablesJson = new Gson().toJson(variablesJsonBuilder.buildFromParams(params));
        final JSONObject json = new JSONObject();
        json.put("variables", new JSONObject(variablesJson));
        json.put("query", graphQlQuery);
        return json;
    }
}
