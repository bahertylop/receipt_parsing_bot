package org.example.util;

import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_imgproc.CLAHE;

public class KannyDetectorUtil {

    public static void kannyUtil(String receiptUrl, String fileId, String extension) {

        String inputPath = receiptUrl;
        String outputPath = "D:/android_developing/IdiaProjects/receipt_parsing_bot/src/main/resources/kanny/photo_" + fileId + "." + extension;;

        // Загрузить изображение в градациях серого
        Mat image = opencv_imgcodecs.imread(inputPath, opencv_imgcodecs.IMREAD_GRAYSCALE);

        if (image.empty()) {
            System.err.println("Не удалось загрузить изображение");
            return;
        }

        Mat edges = new Mat();

        // размытие для подавления шума
        opencv_imgproc.GaussianBlur(image, image, new Size(5, 5), 1.5);

        // детектор Канни
        opencv_imgproc.Canny(image, edges, 50, 150);

        opencv_imgcodecs.imwrite(outputPath, edges);

        System.out.println("Границы сохранены в " + outputPath);

        detectROI(outputPath, fileId, extension);
    }

    public static void detectROI(String kannyUrl, String fileId, String extension) {
        // изображение с границами (результат Canny)
        Mat edges = opencv_imgcodecs.imread(kannyUrl, opencv_imgcodecs.IMREAD_GRAYSCALE);

        // контуры
        MatVector contours = new MatVector();
        Mat hierarchy = new Mat();
        opencv_imgproc.findContours(edges.clone(), contours, hierarchy, opencv_imgproc.RETR_EXTERNAL, opencv_imgproc.CHAIN_APPROX_SIMPLE);

        // самый большой прямоугольный контур
        Rect bestRect = null;
        double maxArea = 0;

        System.out.println("Количество контуров" + contours.size());
        for (int i = 0; i < contours.size(); i++) {
            Mat contour = contours.get(i);
            Rect rect = opencv_imgproc.boundingRect(contour);

            double area = rect.width() * rect.height();

            if (area > maxArea) {
                maxArea = area;
                bestRect = rect;
            }
        }

        if (bestRect != null) {
            // оригинальное изображение (до Canny!)
            Mat original = opencv_imgcodecs.imread("D:/android_developing/IdiaProjects/receipt_parsing_bot/src/main/resources/photos/photo_" + fileId + "." + extension);

            // отрезание ROI
            Mat roi = new Mat(original, bestRect);

            String outputUrl = "D:/android_developing/IdiaProjects/receipt_parsing_bot/src/main/resources/roi/photo_" + fileId + "." + extension;
            opencv_imgcodecs.imwrite(outputUrl, roi);
            System.out.println("ROI сохранён как " + fileId + "." + extension);
            adaptiveThreshold(outputUrl, fileId, extension);
        } else {
            System.out.println("Контуры не найдены");
        }
    }

    public static void adaptiveThreshold(String roiUrl, String fileId, String extension) {
        Mat image = opencv_imgcodecs.imread(roiUrl, opencv_imgcodecs.IMREAD_GRAYSCALE);

        if (image.empty()) {
            System.err.println("Не удалось загрузить изображение");
            return;
        }

        // размытие для уменьшения шума
        opencv_imgproc.GaussianBlur(image, image, new Size(5, 5), 0);

        // Адаптивная бинаризация (выделяет текст)
        Mat binary = new Mat();
        opencv_imgproc.adaptiveThreshold(
                image,
                binary,
                255,
                opencv_imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                opencv_imgproc.THRESH_BINARY,
                5,  // размер окна
                3    // смещение
        );

//        opencv_imgproc.GaussianBlur(image, image, new Size(5, 5), 0);

//        Mat kernel = opencv_imgproc.getStructuringElement(opencv_imgproc.MORPH_RECT, new Size(1, 1));
//        opencv_imgproc.morphologyEx(binary, binary, opencv_imgproc.MORPH_CLOSE, kernel);

        opencv_imgproc.resize(binary, binary, new Size(binary.cols() * 4, binary.rows() * 4));
        
        opencv_imgcodecs.imwrite("D:/android_developing/IdiaProjects/receipt_parsing_bot/src/main/resources/adaptiveThreshold/photo_" + fileId + "." + extension, binary);
        System.out.println("Изображение очищено и сохранено как photo_" + fileId + "." + extension);
    }
}

