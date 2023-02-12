package com.nukem.jobseeker.model.dto.robota;


import com.google.gson.Gson;
import com.nukem.jobseeker.model.City;
import com.nukem.jobseeker.repository.CityRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.weaver.ast.Var;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * !!!Needs refactoring af!!!
 */

@Data
public class VariablesJson {
    private PaginationJson pagination = new PaginationJson();
    private FilterJson filter = new FilterJson();


    @RequiredArgsConstructor
    public static class Builder {

        @Data
        @AllArgsConstructor
        private static class EmploymentType {
            private String type;
            private String robotaUaId;
        }

        private static final List<EmploymentType> employmentTypes =
                List.of(new EmploymentType("remote", "3"),
                        new EmploymentType("office", "9"));

        @SneakyThrows
        public static VariablesJson buildFromParams(final Map<String, String> params) {
            final VariablesJson variables = new VariablesJson();
            variables.getFilter().setKeywords(params.get("keywords"));
            variables.getFilter().setScheduleIds(getScheduleIds(params));
            variables.getPagination().setPage(getPage(params));

            readTxt(params, variables);

            return variables;
        }

        private static int getPage(Map<String, String> params) {
            if (!Strings.isBlank(params.get("page"))) {
                int page = Integer.parseInt(params.get("page"));
                page = page < 2 ? 0 : page - 1;
                return page;
            }
            return 0;
        }

        private static String[] getScheduleIds(Map<String, String> params) {
            if (params.get("employment") == null) return null;
            return employmentTypes.stream()
                    .filter(employmentType -> StringUtils.containsIgnoreCase(params.get("location"), employmentType.type))
                    .map(EmploymentType::getRobotaUaId)
                    .toArray(String[]::new);
        }

        private static void readTxt(Map<String, String> params, VariablesJson variables) throws IOException {
            if (!Strings.isBlank(params.get("location"))) {
                String windows = "src\\main\\resources\\cities.txt";
                final BufferedReader br = new BufferedReader(new FileReader(windows));
                final List<City> list = new ArrayList<>();
                String st;
                while ((st = br.readLine()) != null) {
                    list.add(new Gson().fromJson(st, City.class));
                }
                final String robotaCityId = list.stream().filter(city -> city.getEn().toLowerCase().equalsIgnoreCase(params.get("location")))
                        .findFirst()
                        .map(City::getRobotaUaId)
                        .orElse(null);
                variables.getFilter().setCityId(robotaCityId);
            }
        }

    }
}


