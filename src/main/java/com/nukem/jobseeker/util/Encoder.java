package com.nukem.jobseeker.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Encoder {
    public static String urlEncodeUTF8(final String s) {
        if(s == null)
            return "";
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}
