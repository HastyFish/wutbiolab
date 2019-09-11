package com.gooalgene.wutbiolab.controller.admin;

import com.gooalgene.wutbiolab.request.MentorRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lab")
public class LabController {

    @PostMapping("/researchTeam/publish")
    public void publishResearchTeam(@RequestBody List<MentorRequest> mentorRequests){

    }
}
