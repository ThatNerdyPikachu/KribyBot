package com.shadowninja108.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.lang3.StringUtils;

public class MemeUtil {
	public static boolean anyAreEmpty(String... strings) {
		for (int i = 0; i < strings.length; i++)
			if (StringUtils.isEmpty(strings[i]))
				return true;
		return false;
	}

	public static BufferedImage redraw(BufferedImage in) {
		BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics outg = out.getGraphics();
		outg.drawImage(in, 0, 0, null);
		outg.dispose();
		return out;
	}

	public static InputStream compress(BufferedImage in, float compression) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageOutputStream ios = ImageIO.createImageOutputStream(bos);
		ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
		writer.setOutput(ios);
		JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(Locale.getDefault());
		jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		jpegParams.setCompressionQuality(compression);
		writer.write(null, new IIOImage(in, null, null), jpegParams);
		InputStream is = new ByteArrayInputStream(bos.toByteArray());
		bos.close();
		return is;
	}

	public static Rectangle2D centerRect(Rectangle r1, Rectangle2D r2) {
		double xOffset = (r1.getWidth() / 2) - (r2.getWidth() / 2);
		double yOffset = (r1.getHeight() / 2) - (r2.getHeight() / 2);
		double x = r1.getX() + xOffset;
		double y = r1.getY() + yOffset;
		r2.setRect(x, y, r2.getWidth(), r2.getHeight());
		return r2;
	}

	public static double getScale(Rectangle2D r1, Rectangle2D r2) {
		return Math.min(r1.getWidth() / r2.getWidth(), r1.getHeight() / r2.getHeight());
	}

	public static Shape scaleShape(Shape shape, double scale) {
		AffineTransform transform = AffineTransform.getScaleInstance(scale, scale);
		return transform.createTransformedShape(shape);
	}

	public static Shape transformAbsolute(Shape shape, double x, double y) {
		Rectangle2D rec = shape.getBounds2D();
		AffineTransform transform = AffineTransform.getTranslateInstance(x - rec.getX(), y - rec.getY());
		return transform.createTransformedShape(shape);
	}

	public static void drawRect2D(Graphics2D g, Rectangle2D rect) {
		g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
	}

	public static void drawRect(Graphics2D g, Rectangle rect) {
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
	}
}
