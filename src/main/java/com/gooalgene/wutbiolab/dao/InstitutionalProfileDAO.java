package com.gooalgene.wutbiolab.dao;

import com.gooalgene.wutbiolab.entity.InstitutionalProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InstitutionalProfileDAO extends JpaRepository<InstitutionalProfile,Long> {

//    @Modifying
//    @Query("")
//    void saveUnpublished(InstitutionalProfile institutionalProfile);
}
