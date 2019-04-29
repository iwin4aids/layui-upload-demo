package com.skloda.layedit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author: jiangkun
 * @Description:
 * @Date: Created in 2019-04-28 16:26
 */
@Controller
public class UploadController {

    private static final String NGINX_ATTACH_PREFIX_URL = "http://localhost/attachments/";
    private static final String NGINX_IMAGES_PREFIX_URL = "http://localhost/images/";
    private static final String NGINX_DOC_ROOT = "/Users/jiangkun/upload/";

    //处理文件上传
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Msg uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String contentType = file.getContentType();   //图片文件类型
        String fileName = file.getOriginalFilename();  //图片名字
        System.out.println(contentType + "," + fileName);
        //文件存放路径
        String newFilePath = NGINX_DOC_ROOT + (contentType.startsWith("image") ? "images/" : "attachments/");
        try {
            String newFileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf('.'));
            uploadFile(file, newFilePath, newFileName);
            return Msg.success(contentType.startsWith("image") ? NGINX_IMAGES_PREFIX_URL + newFileName : NGINX_ATTACH_PREFIX_URL + newFileName, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    private void uploadFile(MultipartFile file, String targetDir, String targetFileName) {
        File dir = new File(targetDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try (BufferedInputStream bis = new BufferedInputStream(file.getInputStream());
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetDir + File.separator + targetFileName))) {
            byte[] bytes = new byte[1024];
            int length;
            while ((length = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
