package com.nukem.jobseeker.service.impl.workua;

import com.nukem.jobseeker.constant.URL;
import com.nukem.jobseeker.model.dto.VacancyDto;
import com.nukem.jobseeker.service.JobFinder;
import com.nukem.jobseeker.util.QueryUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nukem.jobseeker.util.Encoder.urlEncodeUTF8;

@Service
@RequiredArgsConstructor
public class WorkUaService implements JobFinder {

    private final Logger logger = LoggerFactory.getLogger(WorkUaService.class);

    private final List<String> searchQueryFilterValues = List.of("page");
    private final WorkUaVacancyParser workUaParser;

    @Override
    public List<VacancyDto> findVacancies(final Map<String, String> params) {
        try {
            return Jsoup.connect(buildUrl(params)).get()
                    .select("div.job-link").stream()
                    .map(workUaParser::parse)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Couldn't retrieve data from Work.ua: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public String buildUrl(final Map<String, String> params) {
        final StringBuilder path = new StringBuilder();
        final String location = params.get("location");
        if (!Strings.isBlank(location)) {
            if (!location.equalsIgnoreCase("ukraine"))
                path.append("-").append(urlEncodeUTF8(location));
        }
        final String keywords = params.get("keywords");
        if (!Strings.isBlank(keywords)) {
            path.append("-").append(urlEncodeUTF8(keywords));
        }
        final String searchQuery = Strings.isBlank(keywords)
                ? "?ss=1" + QueryUtil.buildSearchQuery(params, searchQueryFilterValues)
                : QueryUtil.buildSearchQuery(params, searchQueryFilterValues);
        return URL.WORK_UA_URL + String.format("/jobs%s/", path) + searchQuery;
    }
}
