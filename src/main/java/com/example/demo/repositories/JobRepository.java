package com.example.demo.repositories;

import com.example.demo.models.Job;
import com.example.demo.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> getJobsByCompanyId(Long id);

    @Query("SELECT DISTINCT j FROM Job j JOIN j.jobSkills js JOIN js.skill s WHERE s.id IN :skillIds")
    List<Job> findJobsBySkillIds(@Param("skillIds") List<Long> skillIds);

    @Query("SELECT j FROM Job j " +
            "JOIN j.company c " +
            "JOIN c.address a " +
            "JOIN j.jobSkills js " +
            "JOIN js.skill s " +
            "WHERE (:location IS NULL OR a.city = :location) " +
            "AND (:experience IS NULL OR js.skillLevel >= :experience) " +
            "AND (:skillIds IS NULL OR s.id IN :skillIds) " +
            "AND (:skillLevel IS NULL OR js.skillLevel = :skillLevel)")
    List<Job> searchJobs(
            @Param("location") String location,
            @Param("experience") Integer experience,
            @Param("skillIds") List<Long> skillIds,
            @Param("skillLevel") Integer skillLevel
    );

}