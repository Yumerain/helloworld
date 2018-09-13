package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRUtil {
	
	public static void writeQR(String text, String savePath) {
		BufferedImage image = writeQR(text);
		try {
			ImageIO.write(image, "png", new File(savePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage writeQR(String text) {
		int width = 100;
		int height = 100;
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN, 2);
		try {
			QRCodeWriter writer = new QRCodeWriter();
			BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
			return MatrixToImageWriter.toBufferedImage(matrix);
		} catch (WriterException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String readQR(String path) {
		try {
			BufferedImage image = ImageIO.read(new File(path));
			return readQR(image);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String readQR(BufferedImage qrImg) {
		try {
			LuminanceSource source = new BufferedImageLuminanceSource(qrImg);  
            Binarizer binarizer = new HybridBinarizer(source); 
			BinaryBitmap bitmap = new BinaryBitmap(binarizer);
			QRCodeReader reader = new QRCodeReader();
			Result result = reader.decode(bitmap);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
