package com.shop.GoodsShop.Utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    private final Set<String> extensions = new HashSet<>(Arrays.asList(
            "jpg", "bmp", "jpeg", "png" //another extension...
    ));

    public byte[] fileToBytes(File file) {
        logger.info("Converting file to bytes...");

        if (file == null) {
            logger.warn("File is null");
            logger.info("Fail converting");
            return new byte[0];
        }

        byte[] bytes;
        logger.debug("File name: " + file.getName());
        try {

            InputStream fileStream = new FileInputStream(file);
            bytes = IOUtils.toByteArray(fileStream);

        } catch (IOException e) {
            logger.error("Fail converting: " + e.toString());
            return new byte[0];
        }

        logger.info("Converting successful");
        return bytes;
    }

    public boolean hasInvalidExtension(String filename) {
        logger.info("Start checking to valid extension");
        String ext;
        try {
            logger.debug("Try to split filename");
            ext = filename.split(Pattern.quote("."))[1];
        } catch (Exception e) {
            logger.debug("Fail split");
            logger.error(e.toString());
            return true;
        }
        logger.debug("Split successful");

        return !extensions.contains(ext);
    }
}
