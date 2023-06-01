package com.example.demo67;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ImageCopyExample {
    static int count = 0;
    public static String  copyfile(String path,String id) {
        int a= path.lastIndexOf("\\");
        System.out.println(a);
        String mainPath = "";
        for (int i = ++a; i < path.length(); i++) {
            mainPath+=path.charAt(i);
        }
        System.out.println(mainPath);
        // مسیر فایل اصلی عکس
        File sourceImage = new File(path);
        String newPath="src/main/resources/com/example/demo67/";
        String newPath_endOfFile= newPath+"product_id"+id+mainPath;
        count++;
        System.out.println(newPath_endOfFile);
        // مسیر فایل جدید برای کپی کردن عکس
        Path destinationPath = Path.of(newPath_endOfFile);

        try {
            // کپی کردن فایل عکس از مسیر اصلی به مسیر مقصد
            Files.copy(sourceImage.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("عکس با موفقیت کپی شد.");
        } catch (IOException e) {
            System.out.println("خطا در کپی کردن عکس: " + e.getMessage());
        }
        return newPath_endOfFile;
    }
}
