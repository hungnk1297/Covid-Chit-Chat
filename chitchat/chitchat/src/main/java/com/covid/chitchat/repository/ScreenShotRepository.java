package com.covid.chitchat.repository;

import com.covid.chitchat.entity.ScreenShot;
import com.covid.chitchat.entity.injection.ScreenShotView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenShotRepository extends JpaRepository<ScreenShot, Long> {

    @Query(nativeQuery = true,
            value = "SELECT ss.SCREEN_SHOT_ID   screenShotID, " +
                    "       ss.SCREEN_SHOT_NAME screenShotName, " +
                    "       u.USERNAME username\n" +
                    "FROM AS2_SCREEN_SHOT ss\n" +
                    "         LEFT JOIN AS2_USER u ON ss.USER_ID = u.USER_ID " +
                    "ORDER BY ss.CREATED_ON DESC ")
    List<ScreenShotView> findAllScreenShot();
}
