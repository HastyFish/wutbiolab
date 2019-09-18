package com.gooalgene.wutbiolab.controller.admin;

import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.util.CacheUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @ApiOperation(value="退出登录", notes="退出登录")
    @PostMapping("/logout")
    public CommonResponse logout(HttpServletRequest request){
        String token = request.getHeader("token");
        if(StringUtils.isNotEmpty(token)){
            CacheUtil.deleteToken(token);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return ResponseUtil.success();
    }
}
