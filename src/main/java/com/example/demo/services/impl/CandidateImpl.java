package com.example.demo.services.impl;

import com.example.demo.models.Candidate;
import com.example.demo.models.Skill;
import com.example.demo.repositories.CandidateRepository;
import com.example.demo.repositories.SkillRepository;
import com.example.demo.services.ICandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidateImpl implements ICandidate {

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public Candidate findById(long id) {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        return candidate.orElse(null);
    }

    @Autowired
    private SkillRepository skillRepository;

    // Đề xuất các skill mà ứng viên chưa có
    @Override
    public List<Skill> suggestSkillsForCandidate(Long candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        List<Skill> allSkills = skillRepository.findAll();
        List<Skill> candidateSkills = candidate.getSkills();

        return allSkills.stream()
                .filter(skill -> !candidateSkills.contains(skill))
                .collect(Collectors.toList());
    }

    @Override
    public List<Candidate> findCandidatesBySkills(List<Skill> skills) {
        return candidateRepository.findCandidatesBySkills(skills);
    }


    @Override
    public Page<Candidate> getCandidates(Pageable pageable) {
        return candidateRepository.findAll(pageable);
    }

    @Override
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll(); // Retrieve all candidates without pagination
    }

    @Override
    public List<Candidate> searchCandidates(String location, List<Long> skillIds, Integer skillLevel) {
        return candidateRepository.searchCandidates(location, skillIds, skillLevel);
    }

}
