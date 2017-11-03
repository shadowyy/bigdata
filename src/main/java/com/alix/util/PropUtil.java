package com.alix.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * @author yuyue
 * @version 2017-11-3 0003 15:06
 */
public class PropUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropUtil.class);

    public static Properties load(String fileName) {
        Properties props = new Properties();
        try {
            props.load(PropUtil.class.getClassLoader().getResourceAsStream(fileName));
        } catch (IOException e) {
            LOGGER.error("");
        }
        return props;
    }
}
