package com.covid.chitchat.repository;

import com.covid.chitchat.entity.ScreenShot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenShotRepository extends JpaRepository<ScreenShot, Long> {
}
