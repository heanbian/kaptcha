package com.heanbian.captcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.KaptchaConfig;

public final class CaptchaUtils {

    static {
        ImageIO.setUseCache(false);
    }

    private static final SecureRandom RAND = new SecureRandom();

    public static final String BASE64_PREFIX = "data:image/png;base64,";

    public static final String DEFAULT_CHARS = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

    public static final int DEFAULT_IMAGE_WIDTH = 180;
    public static final int DEFAULT_IMAGE_HEIGHT = 56;
    public static final int DEFAULT_CAPTCHA_LENGTH = 5;

    public static final int MIN_CAPTCHA_LENGTH = 4;
    public static final int MAX_CAPTCHA_LENGTH = 6;

    private static final int MIN_IMAGE_WIDTH = 100;
    private static final int MIN_IMAGE_HEIGHT = 32;

    private static final String IMAGE_FORMAT = "png";

    private CaptchaUtils() {
    }

    public static CaptchaResult generate(int imageWidth, int imageHeight,
                                         int captchaLength, String candidateChars) {
        validateImageWidth(imageWidth);
        validateImageHeight(imageHeight);
        validateCaptchaLength(captchaLength);

        char[] chars = normalizeCandidateChars(candidateChars);
        validateImageCapacity(imageWidth, captchaLength);

        String captchaText = buildRandomText(captchaLength, chars);
        DefaultKaptcha producer = buildProducer(imageWidth, imageHeight, captchaLength, chars);

        BufferedImage image = producer.createImage(captchaText, imageWidth, imageHeight);
        String base64 = toBase64(image);

        return new CaptchaResult(
                captchaText,
                base64,
                BASE64_PREFIX + base64,
                IMAGE_FORMAT,
                imageWidth,
                imageHeight,
                captchaLength,
                new String(chars)
        );
    }

    public static CaptchaResult generate(int imageWidth, int imageHeight, int captchaLength) {
        return generate(imageWidth, imageHeight, captchaLength, DEFAULT_CHARS);
    }

    public static CaptchaResult generate(int captchaLength) {
        return generate(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT, captchaLength, DEFAULT_CHARS);
    }

    public static CaptchaResult generateDefault() {
        return generate(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT, DEFAULT_CAPTCHA_LENGTH, DEFAULT_CHARS);
    }

    public static String normalizeInput(String input) {
        if (input == null) {
            return "";
        }
        return input.trim()
                .replace(" ", "")
                .toUpperCase(Locale.ROOT);
    }

    public static boolean verify(String expected, String actual) {
        return !normalizeInput(expected).isEmpty()
                && normalizeInput(expected).equals(normalizeInput(actual));
    }

    private static DefaultKaptcha buildProducer(int imageWidth, int imageHeight,
                                                int captchaLength, char[] chars) {
        int fontSize = calculateFontSize(imageHeight);
        int charSpace = calculateCharSpace(imageWidth, captchaLength);

        Properties p = new Properties();

        // 边框
        p.setProperty(Constants.KAPTCHA_BORDER, "no");

        // 图片尺寸
        p.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, String.valueOf(imageWidth));
        p.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, String.valueOf(imageHeight));

        // 文本配置
        p.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, String.valueOf(captchaLength));
        p.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, new String(chars));
        p.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Arial,Courier,Monospaced");
        p.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, String.valueOf(fontSize));
        p.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "20,40,80");
        p.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, String.valueOf(charSpace));

        // 背景
        p.setProperty(Constants.KAPTCHA_BACKGROUND_CLR_FROM, "245,248,250");
        p.setProperty(Constants.KAPTCHA_BACKGROUND_CLR_TO, "255,255,255");

        // 噪点与扭曲
        p.setProperty(Constants.KAPTCHA_NOISE_COLOR, "110,120,140");
        p.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL,
                "com.google.code.kaptcha.impl.WaterRipple");

        DefaultKaptcha producer = new DefaultKaptcha();
        producer.setKaptchaConfig(new KaptchaConfig(p));
        return producer;
    }

    private static String buildRandomText(int captchaLength, char[] chars) {
        StringBuilder sb = new StringBuilder(captchaLength);
        int lastIndex = -1;

        for (int i = 0; i < captchaLength; i++) {
            int nextIndex;
            do {
                nextIndex = RAND.nextInt(chars.length);
            } while (chars.length > 1 && nextIndex == lastIndex);

            sb.append(chars[nextIndex]);
            lastIndex = nextIndex;
        }

        return sb.toString();
    }

    private static char[] normalizeCandidateChars(String candidateChars) {
        String raw = (candidateChars == null || candidateChars.isBlank())
                ? DEFAULT_CHARS
                : candidateChars.trim();

        Set<Character> unique = new LinkedHashSet<>();
        for (char c : raw.toCharArray()) {
            if (!Character.isWhitespace(c)) {
                unique.add(c);
            }
        }

        if (unique.size() < 2) {
            throw new IllegalArgumentException("candidateChars 至少需要 2 个非空白且不重复的字符");
        }

        char[] chars = new char[unique.size()];
        int i = 0;
        for (Character c : unique) {
            chars[i++] = c;
        }
        return chars;
    }

    private static String toBase64(BufferedImage image) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream(2048)) {
            boolean written = ImageIO.write(image, IMAGE_FORMAT, out);
            if (!written) {
                throw new IllegalStateException("No ImageIO writer found for format: " + IMAGE_FORMAT);
            }
            return Base64.getEncoder().encodeToString(out.toByteArray());
        } catch (IOException e) {
            throw new IllegalStateException("验证码图片转 Base64 失败", e);
        }
    }

    /**
     * 按图片高度估算字体大小
     */
    private static int calculateFontSize(int imageHeight) {
        int fontSize = imageHeight - 16;
        if (fontSize < 24) {
            return 24;
        }
        return Math.min(fontSize, 44);
    }

    /**
     * 按宽度和验证码长度估算字符间距
     */
    private static int calculateCharSpace(int imageWidth, int captchaLength) {
        int charSpace = (imageWidth - 20) / (captchaLength * 8);
        if (charSpace < 2) {
            return 2;
        }
        return Math.min(charSpace, 8);
    }

    private static void validateImageWidth(int imageWidth) {
        if (imageWidth < MIN_IMAGE_WIDTH) {
            throw new IllegalArgumentException("imageWidth 不能小于 " + MIN_IMAGE_WIDTH);
        }
    }

    private static void validateImageHeight(int imageHeight) {
        if (imageHeight < MIN_IMAGE_HEIGHT) {
            throw new IllegalArgumentException("imageHeight 不能小于 " + MIN_IMAGE_HEIGHT);
        }
    }

    private static void validateCaptchaLength(int captchaLength) {
        if (captchaLength < MIN_CAPTCHA_LENGTH || captchaLength > MAX_CAPTCHA_LENGTH) {
            throw new IllegalArgumentException(
                    "captchaLength 必须在 " + MIN_CAPTCHA_LENGTH + " 到 " + MAX_CAPTCHA_LENGTH + " 之间"
            );
        }
    }

    /**
     * 防止图片宽度过小导致字符重叠严重
     */
    private static void validateImageCapacity(int imageWidth, int captchaLength) {
        int minRecommendedWidth = captchaLength * 24 + 20;
        if (imageWidth < minRecommendedWidth) {
            throw new IllegalArgumentException(
                    "当前 imageWidth 过小，至少建议为 " + minRecommendedWidth + "，否则验证码可能难以识别"
            );
        }
    }

    public record CaptchaResult(
            String text,
            String base64,
            String dataUrl,
            String imageFormat,
            int imageWidth,
            int imageHeight,
            int captchaLength,
            String candidateChars) {
    }
}
