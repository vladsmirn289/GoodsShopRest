package com.shop.GoodsShop.Utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
    Logger logger = LoggerFactory.getLogger(FileUtil.class);

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
}
