package com.nukem.jobseeker.util;

import com.nukem.jobseeker.model.City;
import com.nukem.jobseeker.model.dto.robota.VariablesJson;
import com.nukem.jobseeker.repository.CityRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/** PLEASE, REFACTOR ME!!! */

@Component
@RequiredArgsConstructor
public class VariablesJsonBuilder {

    private final CityRepository cityRepository;

    @Data
    @AllArgsConstructor
    private static class EmploymentType {
        private String type;
        private String robotaUaId;
    }

    private static final List<VariablesJsonBuilder.EmploymentType> employmentTypes =
            List.of(new VariablesJsonBuilder.EmploymentType("remote", "3"),
                    new VariablesJsonBuilder.EmploymentType("office", "9"));


    @SneakyThrows
    public VariablesJson buildFromParams(final Map<String, String> params) {
        final VariablesJson variables = new VariablesJson();
        variables.getFilter().setKeywords(params.get("keywords"));
        variables.getFilter().setScheduleIds(getScheduleIds(params));
        variables.getPagination().setPage(getPage(params));
        setCityId(params, variables);

        return variables;
    }

    private int getPage(Map<String, String> params) {
        if (!Strings.isBlank(params.get("page"))) {
            int page = Integer.parseInt(params.get("page"));
            page = page < 2 ? 0 : page - 1;
            return page;
        }
        return 0;
    }

    private String[] getScheduleIds(Map<String, String> params) {
        if (params.get("employment") == null) return null;
        return employmentTypes.stream()
                .filter(employmentType -> StringUtils.containsIgnoreCase(params.get("location"), employmentType.type))
                .map(VariablesJsonBuilder.EmploymentType::getRobotaUaId)
                .toArray(String[]::new);
    }

    private void setCityId(Map<String, String> params, VariablesJson variables) {
        if (!Strings.isBlank(params.get("location"))) {
            final String robotaCityId = cityRepository.findAll().stream().filter(city -> city.getEn().toLowerCase().equalsIgnoreCase(params.get("location")))
                    .findFirst()
                    .map(City::getRobotaUaId)
                    .orElse(null);
            variables.getFilter().setCityId(robotaCityId);
        }
    }

}