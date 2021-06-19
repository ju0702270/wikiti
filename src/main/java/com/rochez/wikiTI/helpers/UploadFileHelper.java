package com.rochez.wikiTI.helpers;

import com.cksource.ckfinder.CKFinder;
import org.apache.tomcat.util.http.fileupload.ProgressListener;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.FileImageInputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadFileHelper {
    public static String upload(ServletContext servletContext,MultipartFile multipartFile){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String fileName = simpleDateFormat.format(new Date() + multipartFile.getOriginalFilename());
            Path path = Paths.get(servletContext.getRealPath("/"+fileName));
            Files.write(path, multipartFile.getBytes());
            return fileName;
        }catch (Exception e){
            return null;
        }
    }

    public static String saveFile( String uploadDir, String fileName , MultipartFile multipartFile) throws IOException {


        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        System.out.println(multipartFile.getContentType());
        System.out.println(multipartFile.getSize());

        try (InputStream inputStream = new BufferedInputStream(multipartFile.getInputStream())) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath,StandardCopyOption.REPLACE_EXISTING) ;
            System.out.println(inputStream.readAllBytes());





            System.out.println(inputStream.readAllBytes());

            return fileName;
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    public static ServletFileUpload getServletFileUpload(DiskFileItemFactory factory,String uploadDir, String fileName ,MultipartFile multipartFile){
        ServletFileUpload upload = new ServletFileUpload(factory);
        // Listen file upload progress
        upload.setProgressListener(new ProgressListener() {
            @Override
            // pBytesRead: have been read to the file size
            // pContentLength: File Size
            public void update(long pBytesRead, long pContentLength, int pItems) {
                System.out.println("Total size:"+pContentLength+"uploaded"+pBytesRead);
            }
        });

        // handle the garbage problem
        upload.setHeaderEncoding("utf-8");
        // set the maximum individual file
        upload.setFileSizeMax(1024*1024*10);
        // set the total size of the files to upload
        upload.setSizeMax(1024 * 1024 * 10);


        return upload;
    }

}
