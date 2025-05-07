package org.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class TelegramFileDownloader {
    public static void downloadFile(String fileUrl, String destinationPath) throws IOException {
        URL url = new URL(fileUrl);
        try (InputStream in = url.openStream()) {
            Files.copy(in, new java.io.File(destinationPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
