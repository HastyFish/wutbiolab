package com.gooalgene.wutbiolab.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.wutbiolab.entity.Picture;
import com.gooalgene.wutbiolab.property.GooalApplicationProperty;
import com.gooalgene.wutbiolab.service.PictureService;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class PictureServiceImpl implements PictureService {

    private GooalApplicationProperty gooalApplicationProperty;

    private Logger logger = LoggerFactory.getLogger(PictureServiceImpl.class);

    public PictureServiceImpl(GooalApplicationProperty gooalApplicationProperty) {
        this.gooalApplicationProperty = gooalApplicationProperty;
    }

    @Override
    public String saveBase64(String oldPictureListString, String newPictureListString) {
        ObjectMapper objectMapper = new ObjectMapper();
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
}
