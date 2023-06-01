package com.example.freshly;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ImageCopy {
    static int count = 0;

    public static String copyfile(String path, String id) {
        int a = path.lastIndexOf("\\");
        System.out.println(a);
        String mainPath = "";
        ++a;

        for(int i = a; i < path.length(); ++i) {
            mainPath = mainPath + path.charAt(i);
        }

        System.out.println(mainPath);
        File sourceImage = new File(path);
        String newPath = "src/main/resources/com/example/freshly/ProductsImages/";
        String newPath_endOfFile = newPath + "product_id" + id + mainPath;
        ++count;
        System.out.println(newPath_endOfFile);
        Path destinationPath = Path.of(newPath_endOfFile);

        try {
            Files.copy(sourceImage.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("عکس با موفقیت کپی شد.");
        } catch (IOException var9) {
            System.out.println("خطا در کپی کردن عکس: " + var9.getMessage());
        }

        return newPath_endOfFile;
    }
}

