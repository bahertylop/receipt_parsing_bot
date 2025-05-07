package org.example.service.qr;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

@Component
public class ReceiptClient {
    public void parse(File file) throws IOException {
        String boundary = "===" + System.currentTimeMillis() + "===";
        URL url = new URL("http://localhost:8000/parse-receipt/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (DataOutputStream request = new DataOutputStream(connection.getOutputStream())) {
            request.writeBytes("--" + boundary + "\r\n");
            request.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"check.jpg\"\r\n");
            request.writeBytes("Content-Type: image/jpeg\r\n\r\n");
            Files.copy(file.toPath(), request);
            request.writeBytes("\r\n--" + boundary + "--\r\n");
            request.flush();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println("Ответ от Python-сервера:");
        System.out.println(response.toString());
    }
}

