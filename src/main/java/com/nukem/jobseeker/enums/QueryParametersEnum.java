package com.nukem.jobseeker.enums;

public enum QueryParametersEnum {
    KEYWORDS, PAGE, EMPLOYMENT;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
    enum WorkUaQueryParameters {
        PAGE
    }
}
