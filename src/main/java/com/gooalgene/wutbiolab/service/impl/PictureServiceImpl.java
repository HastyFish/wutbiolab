package com.gooalgene.wutbiolab.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.wutbiolab.entity.Picture;
import com.gooalgene.wutbiolab.exception.WutbiolabException;
import com.gooalgene.wutbiolab.property.GooalApplicationProperty;
import com.gooalgene.wutbiolab.response.common.ResultCode;
import com.gooalgene.wutbiolab.service.PictureService;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class PictureServiceImpl implements PictureService {
    private final static String PERFIX = "(^data:image/(png|jpg|jpeg);base64,).*";

    private GooalApplicationProperty gooalApplicationProperty;
    private ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(PictureServiceImpl.class);

    public PictureServiceImpl(GooalApplicationProperty gooalApplicationProperty,ObjectMapper objectMapper) {
        this.gooalApplicationProperty = gooalApplicationProperty;
        this.objectMapper=objectMapper;
    }

    @Override
    public String saveBase64(String oldPictureListString, String newPictureListString) {
        if (null != oldPictureListString) {
            try {
                List<Picture> oldImageList = objectMapper.readValue(oldPictureListString,
                        objectMapper.getTypeFactory().constructParametricType(List.class, Picture.class));
                oldImageList.forEach(one -> {
                    String oldImageName = StringUtils.substringAfterLast(one.getUrl(), "/");
                    String oldImagePath = gooalApplicationProperty.getImagePath() + oldImageName;
                    File oldImageFile = new File(oldImagePath);
                    if (oldImageFile.exists()) {
                        if (!oldImageFile.delete()) {
                            logger.error(" Old image exist but fail to delete");
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            List<Picture> newImageList = objectMapper.readValue(newPictureListString,
                    objectMapper.getTypeFactory().constructParametricType(List.class, Picture.class));
            newImageList.forEach(one -> {
                try {
                    byte[] bytes = Base64.decodeBase64(one.getUrl());
                    String newImageName = UUID.randomUUID() + ".png";
                    String newImagePath = gooalApplicationProperty.getImagePath() + newImageName;
                    for (int i = 0; i < bytes.length; i++) {
                        if (bytes[i] < 0) {
                            bytes[i] += 256;
                        }
                    }
                    OutputStream out = new FileOutputStream(newImagePath);
                    out.write(bytes);
                    out.flush();
                    out.close();
                    one.setUrl(gooalApplicationProperty.getImageNginxUrl() + newImageName);
                } catch (IOException e) {
                    e.printStackTrace();
                    one.setUrl("");
                }
            });
            return objectMapper.writeValueAsString(newImageList);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    public  String convertPropertyImageAndSave(String imageCodeInfoList) throws IOException {
        if (StringUtils.isEmpty(imageCodeInfoList)) {
            return "";
        }
        List<Picture> oldImageList = objectMapper.readValue(imageCodeInfoList,objectMapper.getTypeFactory().constructParametricType(List.class, Picture.class));
        List<Picture> urlImageResponses = new ArrayList<>();
        for (Picture picture : urlImageResponses) {
            //picture.getUrl()刚进来时为base64串而非url
            String base64Str = picture.getUrl();
            Matcher matcher = matchBase64(base64Str);
            if (matcher.find()) {
                String perfix = matcher.group(1);
                String fileSuffix = matcher.group(2);
                picture = saveToLocal(formatBase64(base64Str, perfix), fileSuffix);
            } else {
                //前端回传时会带上url，而非相对路径了，要处理
                String url = picture.getUrl();
                String imageName = picture.getName();
                if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                    if (StringUtils.isEmpty(imageName)) {
                        imageName = url.substring(url.lastIndexOf("/") + 1);
                    }
                    picture.setUrl(imageName);
                }
            }
            urlImageResponses.add(picture);
        }
        return objectMapper.writeValueAsString(urlImageResponses);
    }

    public  Picture saveToLocal(String base64Str, String fileSuffix) {
        Boolean needCompress = false;
        //通过base64判断图片大小
//        Integer imgSize = getImgSize(base64Str);
//        if (imgSize > 1048576) {
//            //当图片大于1m时，要压缩
//            needCompress = true;
//        }

//        GooalApplicationProperty gooalApplicationProperty = SpringContextHolder.getBean(GooalApplicationProperty.class);
        String path = gooalApplicationProperty.getImagePath();
        File file = new File(path);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            if (!mkdirs) {
                throw new WutbiolabException(ResultCode.CREATE_FILE_FAIL_fOR_UPLOAD);
            }
        }
        String fileName = UUID.randomUUID() + "." + fileSuffix;
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            boolean newFile;
            try {
                newFile = targetFile.createNewFile();
            } catch (IOException e) {
                logger.error("[base64转换]创建文件失败，", e);
                throw new WutbiolabException(ResultCode.CREATE_FILE_FAIL_fOR_UPLOAD);
            }
            if (!newFile) {
                throw new WutbiolabException(ResultCode.CREATE_FILE_FAIL_fOR_UPLOAD);
            }
        }
        boolean b = base64ToImage(base64Str, targetFile);
        if (!b) {
            throw new WutbiolabException(ResultCode.BASE_64_CONVERT_FAIL);
        }
//        if (needCompress) {
//            //压缩文件
//            try {
//                Thumbnails.of(targetFile).scale(1f).outputQuality(1f).toFile(targetFile);
//            } catch (IOException e) {
//                throw new WebOfficialException(ResultCode.IMAGE_COMPRESS_FAIL);
//            }
//        }
        //url要写相对路径，所有也是fileName
        return new Picture(fileName, fileName, targetFile.getAbsolutePath());
    }

    public Matcher matchBase64(String base64Str) {
        Pattern pattern = Pattern.compile(PERFIX);
        Matcher matcher = pattern.matcher(base64Str);
        return matcher;
    }
    public  String formatBase64(String base64Str, String perfix) {
        base64Str = base64Str.substring(perfix.length());
        return base64Str;
    }

    public  boolean base64ToImage(String imgStr, File file) { // 对字节数组字符串进行Base64解码并生成图片
        if (StringUtils.isEmpty(imgStr)) // 图像数据为空
            return false;
        try (OutputStream out = new FileOutputStream(file)) {
            // Base64解码
            byte[] b = org.apache.commons.codec.binary.Base64.decodeBase64(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            out.write(b);
            out.flush();
            return true;
        } catch (Exception e) {
            logger.error("base64转换失败", e);
            return false;
        }
    }
}
