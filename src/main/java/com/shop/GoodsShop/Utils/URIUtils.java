package com.shop.GoodsShop.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class URIUtils {
    public static final Logger logger = LoggerFactory.getLogger(URIUtils.class);

    public static String toSamePage(String referer,
                                    RedirectAttributes redirectAttributes) {
        logger.info("Called toSamePage method called");
        String path;

        if (referer != null) {
            logger.info("Building uri path");
            UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
            components.getQueryParams()
                    .forEach(redirectAttributes::addAttribute);

            path = components.getPath();
            logger.info("Building successful");
        } else {
            logger.warn("Referer is null");
            path = "/";
        }

        return path;
    }
}
