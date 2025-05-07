package org.example.service.qr;

import boofcv.abst.fiducial.QrCodeDetector;
import boofcv.alg.fiducial.qrcode.QrCode;
import boofcv.factory.fiducial.FactoryFiducial;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.image.GrayU8;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.springframework.stereotype.Component;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_imgproc.*;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.javacv.Java2DFrameConverter;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

@Component
public class QrService {

    private final QrCodeDetector<GrayU8> detector = FactoryFiducial.qrcode(null, GrayU8.class);

    public String decodeQRCode(File qrCodeImage) throws Exception {
        BufferedImage bufferedImage = ImageIO.read(qrCodeImage);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Result result = new MultiFormatReader().decode(bitmap);
        System.out.println(result.getText());
        return result.getText();
    }

    public String decodeQrCode(File qrCodeImage) throws Exception {
        BufferedImage input = ImageIO.read(qrCodeImage);
        GrayU8 image = ConvertBufferedImage.convertFrom(input, (GrayU8)null);

        detector.process(image);

        List<QrCode> codes = detector.getDetections();

        for (QrCode qr : codes) {
            System.out.println("QR Data: " + qr.message);
            return qr.message;
        }
        return null;
    }

    public String decodeQRCode3(File qrImage) throws NotFoundException {
        // Загрузка изображения через OpenCV
        Mat mat = opencv_imgcodecs.imread(qrImage.getAbsolutePath());

        // Конвертация Mat → Frame → BufferedImage
        OpenCVFrameConverter.ToMat matConverter = new OpenCVFrameConverter.ToMat();
        Frame frame = matConverter.convert(mat);

        Java2DFrameConverter imageConverter = new Java2DFrameConverter();
        BufferedImage image = imageConverter.convert(frame);

        // Декодирование QR-кода с помощью ZXing
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Result result = new MultiFormatReader().decode(bitmap);
        System.out.println("QR Code Data: " + result.getText());
        return result.getText();
    }
}

