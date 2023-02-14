package com.nukem.jobseeker.service.impl.djinni;

import com.nukem.jobseeker.constant.URL;
import com.nukem.jobseeker.service.VacancyParser;
import com.nukem.jobseeker.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DjinniVacancyParser extends VacancyParser<Element> {

    @Override
    protected String getPlaceholderLogo() {
        return "https://media.discordapp.net/attachments/938097860547846164/1069323806478442626/df0f3da863174a633ea8c895b6f7366b.png";
    }

    @Override
    protected LocalDate getVacancyDate(final Element element) {
        return DateUtils.parseDate(element.select("div[class=text-date order-2 ml-md-auto pr-2 mb-2 mb-md-0 nowrap]").first().ownText());
    }

    @Override
    protected String getVacancyTitle(final Element element) {
        return element.select("div[class=list-jobs__title list__title order-1]").text();
    }

    @Override
    protected String getVacancyDescription(final Element element) {
        return element.select("div[class=list-jobs__description position-relative]").text();
    }

    @Override
    protected String getVacancyLink(final Element element) {
        return URL.DJINNI_URL + element.select("a[class=profile]").attr("href");
    }

    @Override
    protected String getCompanyLogo(final Element element) {
        if (element.select("div.list-jobs__details__info > a").size() > 1) {
            return StringUtils.substringBetween(element.select("div.userpic-image").attr("style"), "'");
        }
        return null;
    }

    @Override
    protected String getLocation(Element element) {
        return element.select("span.location-text").text();
    }

    @Override
    protected String getCompanyName(Element element) {
        if (element.select("div.list-jobs__details__info > a").size() > 1) {
            return element.select("div.list-jobs__details__info > a").first().text();
        }
        return null;
    }
}
