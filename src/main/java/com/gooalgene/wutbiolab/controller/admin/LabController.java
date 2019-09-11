package com.gooalgene.wutbiolab.controller.admin;

import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.request.MentorRequest;
import com.gooalgene.wutbiolab.service.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/lab")
public class LabController {
    @Autowired
    private LabService labService;
    @PostMapping("/researchTeam/publish")
    public void publishResearchTeam(@RequestBody List<MentorRequest> mentorRequests){

    }

    @GetMapping("/")
    public void getResearchTeam(@RequestParam("labCategoryId") Long labCategoryId){
        Page<LabDetail> labDetailPage = labService.getLabDetailByLabCategoryId(labCategoryId, null, null);
        List<LabDetail> labDetails = labDetailPage.getContent();
        List<Long> mentorCategoryIds=new ArrayList<>();
        labDetails.forEach(labDetail -> {
            Long mentorCategoryId = labDetail.getMentorCategoryId();
            mentorCategoryIds.add(mentorCategoryId);
        });

    }
}
