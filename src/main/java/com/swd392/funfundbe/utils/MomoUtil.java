package com.swd392.funfundbe.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class MomoUtil {

    private final int min = 500000000;
    private final int max = 999999999;

    public static String generateRequestId() {
        return UUID.randomUUID().toString() + "-request-id";
    }
    public static String generateOrderId() {
        return UUID.randomUUID().toString() + "-order-id";
    }

    public static String hmacSHA256(String inputData, String key) {
        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacSha256.init(secretKey);
            byte[] hmacBytes = hmacSha256.doFinal(inputData.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hmacBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        return null;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
