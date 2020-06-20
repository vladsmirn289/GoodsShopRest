package com.shop.GoodsShop.Utils;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class FileUtilTest {
    @Test
    public void shouldConvertFileToBytes() {
        FileUtil fileUtil = new FileUtil();
        File file = new File("src/test/resources/images/background.png");
        byte[] bytes = fileUtil.fileToBytes(file);

        assertThat(bytes).isNotEmpty();
    }

    @Test
    public void shouldReturnEmptyArrayOfBytes() {
        FileUtil fileUtil = new FileUtil();
        byte[] bytes = fileUtil.fileToBytes(null);

        assertThat(bytes).isEmpty();
    }
}
