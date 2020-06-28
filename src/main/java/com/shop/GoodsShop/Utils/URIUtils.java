package com.shop.GoodsShop.Utils;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class URIUtils {
    public static String toSamePage(String referer,
                                    RedirectAttributes redirectAttributes) {
        String path;

        if (referer != null) {
            UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
            components.getQueryParams()
                    .forEach(redirectAttributes::addAttribute);

            path = components.getPath();
        } else {
            path = "/";
        }

        return path;
    }
}
