package com.nukem.jobseeker.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nukem.jobseeker.constant.URL.QUESTION_MARK;
import static com.nukem.jobseeker.util.Encoder.urlEncodeUTF8;

public class QueryUtil {
    public static String buildSearchQuery(final Map<String, String> params, final List<String> filterValues) {
        return filterParams(params, filterValues).entrySet()
                .stream()
                .map(p -> urlEncodeUTF8(p.getKey()) + "=" + urlEncodeUTF8(p.getValue()))
                .reduce((p1, p2) -> p1 + "&" + p2)
                .map(query -> QUESTION_MARK + query)
                .orElse("");
    }

    public static HashMap<String, String> filterParams(final Map<String, String> params, final List<String> filterValues) {
        final HashMap<String, String> filteredParams = new HashMap<>();
        filterValues.forEach(key -> {
            if (params.get(key) != null) {
                filteredParams.put(key, params.get(key));
            }
        });
        return filteredParams;
    }
}
