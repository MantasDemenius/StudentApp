package com.app.ktu.common;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class QRCodeGeneration {

  public static byte[] generateQRCodeImage(String barcodeText) {
    try {
      QRCodeWriter barcodeWriter = new QRCodeWriter();
      String QR_CODE_LINK = "ktustudentapp://attendlecture/";
      BitMatrix bitMatrix =
        barcodeWriter.encode(QR_CODE_LINK + barcodeText, BarcodeFormat.QR_CODE, 200, 200);
      ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
      MatrixToImageWriter.writeToStream(bitMatrix, "png", pngOutputStream);
      return pngOutputStream.toByteArray();
    } catch (IOException | WriterException e) {
      e.printStackTrace();
    }
    return null;
  }

}
