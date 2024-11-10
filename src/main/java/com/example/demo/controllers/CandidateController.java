package com.example.demo.controllers;

import com.example.demo.models.Candidate;
import com.example.demo.services.ICandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private ICandidate candidateService;

    @GetMapping
    public String showCandidateList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Page<Candidate> candidatePage = candidateService.getCandidates(PageRequest.of(page, size));
        model.addAttribute("candidates", candidatePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", candidatePage.getTotalPages());

        return "candidate-list"; // Points to the Thymeleaf template
    }

    @GetMapping("/all")
    public String showAllCandidates(Model model) {
        List<Candidate> candidates = candidateService.getAllCandidates();
        model.addAttribute("candidates", candidates);
        return "candidate-list-full"; // Non-paginated view
    }

    @GetMapping("/candidate-search")
    public String showSearchForm() {

        return "candidate-search";
    }

    @GetMapping("/search")
    public String searchCandidates(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) List<Long> skillIds,
            @RequestParam(required = false) Integer skillLevel,
            Model model
    ) {
        List<Candidate> candidates = candidateService.searchCandidates(location, skillIds, skillLevel);
        model.addAttribute("candidates", candidates);
        return "candidate-search-results";
    }

}
