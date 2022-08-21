package com.zhkj.code.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class ImageGenerate {
	static Random random = new Random();
	
	public static void paintTextPNG(String str) {
		
		BufferedImage bufImg = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = bufImg.createGraphics();
		g2d.setFont(new Font("΢���ź�", Font.PLAIN, 30));
		FontMetrics metrics = g2d.getFontMetrics();
		int sw = metrics.stringWidth(str);
		int sh = metrics.getAscent() + metrics.getDescent();
		g2d.dispose();
		bufImg = new BufferedImage(sw + 20 , 95, BufferedImage.TYPE_4BYTE_ABGR);
		g2d = bufImg.createGraphics();
		g2d.setFont(new Font("΢���ź�", Font.PLAIN, 30));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
//		g2d.translate(30, 30);
		// ������Ȧ
		Rectangle rect = new Rectangle(0, 0, sw + 20, 58);
		float[] fractions;
		Color[] colors;
		Paint p;
		// �������Ӻ���Ӱ
		int pw = 11; // pillar
		int ph = 34;
		int x = (int) (rect.getWidth() - pw) / 2;
		int y = (int) (rect.getY() + rect.getHeight());
		// ������Ӱ
		int shadowW = 19;
		int shadowH = 8;
		g2d.setPaint(Color.BLACK);
		g2d.fillOval(x + pw / 2 - shadowW / 2, y + ph - shadowH / 2 - 2,
				shadowW, shadowH);
		// ��������
		fractions = new float[] { 0.0f, 0.2f, 0.27f, 0.36f, 0.9f };
		colors = new Color[] {new Color(124, 124, 124),
				new Color(186, 186, 186),
				new Color(241, 241, 241),
				new Color(230, 230, 230),
				new Color(80, 80, 80)};
		p = new LinearGradientPaint(x, y, x + pw, y, fractions, colors);
		g2d.setPaint(p);
		g2d.fillRoundRect(x, y-3, pw, ph+4, 10, 6);
		fractions = new float[] { 0.0f, 0.35f, 0.65f, 0.97f, 1.0f};
		colors = new Color[] {new Color(90, 90, 90),
				new Color(233, 233, 233), new Color(233, 233, 233),
				new Color(106, 106, 106), new Color(103, 103, 103, 160)};
		p = new LinearGradientPaint((float) rect.getX(), (float) rect
				.getY(), (float) rect.getX(), (float) rect.getHeight(),
				fractions, colors);
		g2d.setPaint(p);
		g2d.fillRoundRect(0, 0, (int) rect.getWidth(), (int) rect.getHeight(),
				20, 20);
		// �����ַ����ĺ�ɫ����
		fractions = new float[] { 0.05f, 0.11f, 0.86f, 0.92f, 0.93f};
		colors = new Color[] {new Color(178, 6, 0),
				new Color(207, 7, 0), new Color(207, 7, 0),
				new Color(184, 6, 0), new Color(165, 30, 25)};
		p = new LinearGradientPaint((float) rect.getX(), (float) rect
				.getY(), (float) rect.getX(), (float) rect.getHeight(),
				fractions, colors);
		g2d.setPaint(p);
		int margin = 3;
		g2d.fillRoundRect(margin, margin, (int) rect.getWidth() - margin * 2,
				(int) rect.getHeight() - margin * 2, 15, 15);
		fractions = new float[] { 0.01f, 0.02f, 0.04f, 0.95f, 0.98f};
		colors = new Color[] {new Color(186, 68, 46),
				new Color(190, 7, 0, 255), new Color(207, 7, 0, 0),
				new Color(207, 7, 0, 0), new Color(181, 7, 0, 255)};
		p = new LinearGradientPaint((float) rect.getX(), (float) rect
				.getY(), (float) rect.getWidth(), (float) rect.getY(),
				fractions, colors);
		g2d.setPaint(p);
		g2d.fillRoundRect(margin, margin, (int) rect.getWidth() - margin * 2,
				(int) rect.getHeight() - margin * 2, 15, 15);
		g2d.setPaint(new Color(207, 7, 0, 50));
		g2d.drawLine((int)rect.getWidth() - 3, 9, 
				(int)rect.getWidth() - 3, (int)rect.getHeight() - 9);
		// �����ַ���
		g2d.setPaint(Color.WHITE);
		g2d.drawString(str, (int) (rect.getWidth() - sw) / 2, (int) (rect.getHeight() - sh)
				/ 2 + metrics.getAscent());
		try {
			ImageIO.write(bufImg, "PNG", new File("image1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void paintPNG(String path) {
		//
		int imgWidth = 200;
		int imgHeight = 200;
		BufferedImage bufImg = new BufferedImage(imgWidth , imgHeight, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = bufImg.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.translate(30, 30);
		// ���ƾ���
		Rectangle rect = new Rectangle(0, 0, 100, 58);
		float[] fractions;
		Color[] colors;
		//�滭
		Paint p;
		// �������Ӻ���Ӱ
		int pw = 11; // pillar
		int ph = 34;
		int x = (int) (rect.getWidth() - pw) / 2;
		int y = (int) (rect.getY() + rect.getHeight());
		// ������Ӱ
		int shadowW = 19;
		int shadowH = 8;
		g2d.setPaint(Color.red);
		g2d.fillOval(x + pw / 2 - shadowW / 2, y + ph - shadowH / 2 - 2,
				shadowW, shadowH);
		//���ý���Ч��
		fractions = new float[] { 0.0f, 0.2f, 0.27f, 0.36f, 0.9f };
		colors = new Color[] {new Color(124, 124, 124), new Color(186, 186, 186),
				new Color(241, 241, 241), new Color(230, 230, 230),
				new Color(80, 80, 80)};
		p = new LinearGradientPaint(x, y, x + pw, y, fractions, colors);
		g2d.setPaint(p);
		//���� Բ��
		g2d.fillRoundRect(x, y-3, pw, ph+4, 10, 6);
		
		//���ý���Ч��
		fractions = new float[] { 0.0f, 0.35f, 0.65f, 0.97f, 1.0f};
		colors = new Color[] {new Color(90, 90, 90),
				new Color(233, 233, 233), new Color(233, 233, 233),
				new Color(106, 106, 106), new Color(103, 103, 103, 160)};
		p = new LinearGradientPaint((float) rect.getX(), (float) rect.getY(), 
					(float) rect.getX(), (float) rect.getHeight(),
				fractions, colors);
		g2d.setPaint(p);
		//���ƾ���
		g2d.fillRoundRect(0, 0, (int) rect.getWidth(), (int) rect.getHeight(),
				20, 20);
		
		// �����ַ����ĺ�ɫ����
		int margin = 3;
		g2d.fillRoundRect(margin, margin, (int) rect.getWidth() - margin * 2,
				(int) rect.getHeight() - margin * 2, 15, 15);
		fractions = new float[] { 0.01f, 0.02f, 0.04f, 0.95f, 0.98f};
		colors = new Color[] {new Color(186, 68, 46),
				new Color(190, 7, 0, 255), new Color(207, 7, 0, 255),
				new Color(207, 7, 0, 255), new Color(181, 7, 0, 255)};
		System.out.println(rect.getX());
		p = new LinearGradientPaint((float) rect.getX(), (float) rect
				.getY(), (float) rect.getWidth(), (float) rect.getY(),
				fractions, colors);
		g2d.setPaint(p);
		g2d.fillRoundRect(margin, margin, (int) rect.getWidth() - margin * 2,
				(int) rect.getHeight() - margin * 2, 15, 15);

		g2d.dispose();
		try {
			int nub = 2 + new Random().nextInt(10);
			ImageIO.write(bufImg, "PNG", 
					new File(path +File.separator+ RandomStringUtil.randomLowSting(nub)+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * �������PNGͼƬ
	 * @param path
	 */
	public static void paintRandomPNG(String path) {
		int imgWidth = 20 + random.nextInt(780);
		int imgHeight = 20 + random.nextInt(780);
		BufferedImage bufImg = new BufferedImage(imgWidth , imgHeight, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = bufImg.createGraphics();
		int key = 0;
		for (int i = 0; i < 100; i++) {
			key = random.nextInt(3);
			int  imgWidthTmp = 0;
			int  imgHeightTmp = 0;
			switch (key) {
			case 0:
				imgWidthTmp = random.nextInt(imgWidth)+2;
				imgHeightTmp = random.nextInt(imgHeight)+2;
				g2d.setPaint(randomPaint(imgWidthTmp, imgHeightTmp));
				g2d.fillOval( imgWidthTmp, imgHeightTmp, random.nextInt(imgWidthTmp), random.nextInt(imgWidthTmp));
				break;
			case 1:
				imgWidthTmp = random.nextInt(imgWidth)+2;
				imgHeightTmp = random.nextInt(imgHeight)+2;
				g2d.setPaint(randomPaint(imgWidthTmp, imgHeightTmp));
				g2d.drawLine( random.nextInt(imgWidth), random.nextInt(imgHeight), random.nextInt(imgWidthTmp), random.nextInt(imgWidthTmp));
				break;
			case 2:
				imgWidthTmp = random.nextInt(imgWidth)+2;
				imgHeightTmp = random.nextInt(imgHeight)+2;
				g2d.setPaint(randomPaint(imgWidthTmp, imgHeightTmp));
				g2d.fillRoundRect( random.nextInt(imgWidth), random.nextInt(imgHeight), random.nextInt(imgWidthTmp), random.nextInt(imgWidthTmp),
						random.nextInt(imgWidthTmp), random.nextInt(imgWidthTmp));
				break;
			default:
				break;
			}
		}
		g2d.dispose();
		try {
			int nub = 3 + new Random().nextInt(3);
			ImageIO.write(bufImg, "PNG", 
					new File(path +File.separator+ RandomStringUtil.randomLowSting(nub)+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �������Ч��
	 * @param width
	 * @param heigth
	 * @return
	 */
	public static Paint randomPaint(int width,int heigth){
		float[] fractions = new float[] { 0.0f, 0.35f, 0.65f, 0.97f, 1.0f};
		Color[] colors = new Color[] {
				new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)),
				new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)),
				new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)),
				new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)),
				new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))};
		int originX = random.nextInt(width)+1;
		int originY = random.nextInt(width)+1;
		int endX = random.nextInt(heigth)+1;
		int endY = random.nextInt(heigth)+1;
		if(originX == endX&&originY == endY){
			endX++;
		}
		Paint paint = new LinearGradientPaint( originX, originY, endX, endY,
				fractions, colors);
		return paint;
	}
	
	public static void main(String[] args) {
		FileUtils.deleteFile(new File("png"));
		new File("png").mkdirs();
		for (int i = 0; i < 1000; i++) {
			paintRandomPNG("png");
		}
		paintRandomPNG("png");
	}
}