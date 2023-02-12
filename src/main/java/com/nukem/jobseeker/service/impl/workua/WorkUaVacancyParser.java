package com.nukem.jobseeker.service.impl.workua;

import com.nukem.jobseeker.constant.URL;
import com.nukem.jobseeker.service.VacancyParser;
import com.nukem.jobseeker.util.DateUtils;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class WorkUaVacancyParser extends VacancyParser<Element> {

    @Override
    protected String getPlaceholderLogo() {
        return "https://media.discordapp.net/attachments/938097860547846164/1070019133040771102/work.png";
    }

    @Override
    protected String getVacancyDate(final Element element) {
        return DateUtils.parseDate(element.select("span[class=text-muted small]").text());
    }

    @Override
    protected String getVacancyTitle(final Element element) {
        return element.select("a[title]").text();
    }

    @Override
    protected String getVacancyDescription(final Element element) {
        return element.select("p[class=overflow text-muted add-top-sm cut-bottom]").text();
    }

    @Override
    protected String getVacancyLink(final Element element) {
        return URL.WORK_UA_URL + element.select("a").attr("href");
    }

    @Override
    protected String getCompanyLogo(final Element element) {
        return element.select("img").attr("src");
    }

    @Override
    protected String getLocation(final Element element) {
        return element.select("div.add-top-xs > span:not([class])").last().text();
    }

    @Override
    protected String getCompanyName(final Element element) {
        return element.select("div[class=add-top-xs]").select("b").text();
    }
}
