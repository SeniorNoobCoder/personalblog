package com.sdhsie.base.qrCode;

import jp.sourceforge.qrcode.data.QRCodeImage;

import java.awt.image.BufferedImage;

/**
 * Created by zcx on 2017/2/17.
 */
public class TwoDimensionCodeImage implements QRCodeImage {
    BufferedImage bufImg;

    public TwoDimensionCodeImage(BufferedImage bufImg) {
        this.bufImg = bufImg;
    }

    public int getHeight() {
        return bufImg.getHeight();
    }

    public int getPixel(int x, int y) {
        return bufImg.getRGB(x, y);
    }

    public int getWidth() {
        return bufImg.getWidth();
    }
}
