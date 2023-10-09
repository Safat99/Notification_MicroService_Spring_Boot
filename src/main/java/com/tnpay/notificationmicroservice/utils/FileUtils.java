package com.tnpay.notificationmicroservice.utils;

public class FileUtils {

    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        String fileExtension = "";
        if (dotIndex != -1) {
            fileExtension = fileName.substring(dotIndex + 1).toLowerCase();
        }
        return fileExtension;
    }

    public static String convertFileSize(long fileSizeInBytes) {
        // Define the threshold for converting to MB
        long thresholdInBytes = 1024 * 1024; // 1 MB in bytes

        if (fileSizeInBytes >= thresholdInBytes) {
            double fileSizeInMB = (double) fileSizeInBytes / thresholdInBytes;
            return String.format("%.2f MB", fileSizeInMB);
        } else {
            double fileSizeInKB = (double) fileSizeInBytes / 1024;
            return String.format("%.2f KB", fileSizeInKB);
        }
    }
}
