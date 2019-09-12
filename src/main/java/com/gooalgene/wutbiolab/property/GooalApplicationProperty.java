package com.gooalgene.wutbiolab.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "gooal.application")
public class GooalApplicationProperty {

    private String imagePath;

    private String imageNginxUrl;

    private String exportTempPath;

    private String exportNginxUrl;

}
