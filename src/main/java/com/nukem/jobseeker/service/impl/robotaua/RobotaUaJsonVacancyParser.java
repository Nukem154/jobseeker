package com.nukem.jobseeker.service.impl.robotaua;

import com.nukem.jobseeker.model.dto.VacancyDto;
import com.nukem.jobseeker.service.VacancyParser;
import com.nukem.jobseeker.util.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.nukem.jobseeker.constant.URL.*;

@Component
public class RobotaUaJsonVacancyParser extends VacancyParser<JSONObject> {

    public List<VacancyDto> fromJsonArray(final JSONArray jsonArray) {
        final List<VacancyDto> vacancies = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            vacancies.add(parse(jsonArray.optJSONObject(i)));
        }
        return vacancies;
    }

    @Override
    protected String getPlaceholderLogo() {
        return "https://media.discordapp.net/attachments/938097860547846164/1069323499287613501/rabotaua.png";
    }

    @Override
    protected String getVacancyDate(JSONObject json) {
        return DateUtils.parseDate(json.optString("sortDateText"));
    }

    @Override
    protected String getVacancyTitle(JSONObject json) {
        return json.optString("title");
    }

    @Override
    protected String getVacancyDescription(JSONObject json) {
        return json.optString("description");
    }

    @Override
    protected String getVacancyLink(JSONObject json) {
        return ROBOTA_UA_URL + COMPANY + getCompanyId(json) + SLASH + VACANCY + json.optString("id");
    }

    @Override
    protected String getCompanyLogo(JSONObject json) {
        return getValueFromObject(json.optJSONObject("company"), "logoUrl");
    }

    @Override
    protected String getLocation(JSONObject json) {
        return getValueFromObject(json.optJSONObject("city"), "name");
    }

    @Override
    protected String getCompanyName(JSONObject json) {
        return getValueFromObject(json.optJSONObject("company"), "name");
    }

    private String getValueFromObject(final JSONObject json, final String value) {
        if (json != null)
            return json.optString(value);
        return "";
    }

    private String getCompanyId(final JSONObject json) {
        return json.optJSONObject("company") == null ? "0" : json.optJSONObject("company").optString("id");
    }
}
