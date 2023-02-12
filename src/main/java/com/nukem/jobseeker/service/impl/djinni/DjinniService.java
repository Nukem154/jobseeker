package com.nukem.jobseeker.service.impl.djinni;

import com.nukem.jobseeker.model.dto.VacancyDto;
import com.nukem.jobseeker.service.JobFinder;
import com.nukem.jobseeker.util.QueryUtil;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nukem.jobseeker.constant.URL.*;

@Service
@RequiredArgsConstructor
public class DjinniService implements JobFinder {

    private final Logger logger = LoggerFactory.getLogger(DjinniService.class);

    private final List<String> searchQueryFilterValues = List.of("keywords", "page");
    private final DjinniVacancyParser djinniParser;

    @Override
    public List<VacancyDto> findVacancies(final Map<String, String> params) {
        try {
            final Document document = Jsoup.connect(buildUrl(params)).get();
            if (!params.isEmpty() && document.baseUri().equals(DJINNI_URL + SLASH)) {
                return Collections.emptyList();
            }
            return document.select("li[class=list-jobs__item list__item]")
                    .stream()
                    .map(djinniParser::parse)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Couldn't retrieve data from Djinni.co: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private String buildUrl(final Map<String, String> params) {
        return DJINNI_JOBS_URL + QueryUtil.buildSearchQuery(params, searchQueryFilterValues);
    }

/*
        private String buildParamQuery(final MultiValueMap<String, String> params) {
        return params.entrySet().stream()
                .map(concatValues())
                .reduce((p1, p2) -> p1 + "&" + p2)
                .orElse("");
    }

    private Function<Map.Entry<String, List<String>>, String> concatValues() {
        return p -> {
            final StringBuilder query = new StringBuilder();
            p.getValue().forEach(v -> query.append(urlEncodeUTF8(p.getKey())).append("=").append(urlEncodeUTF8(v)).append("&"));
            query.deleteCharAt(query.length() - 1);
            return query.toString();
        };
    }
*/
}
