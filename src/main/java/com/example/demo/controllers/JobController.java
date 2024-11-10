package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/job")
public class JobController {
    @Autowired
    private IJob jobService;

    @Autowired
    private ISkill skillService;

    @Autowired
    private IJobSkill jobSkillService;

    @Autowired
    private ICandidate candidateService;

    @Autowired
    private Icompany companyService;

    @GetMapping("/")
    public String home() {
        return "home"; // Trả về trang home.html
    }

    @GetMapping("/company-id-form")
    public String showCompanyIdForm() {
        return "company-id-form";  // Trả về view 'company-id-form.html'
    }

    @GetMapping("/jobs")
    public String getAllJobs(@RequestParam(required = false) Long companyId, Model model) {
        List<Job> jobs;

        if (companyId != null) {
            jobs = jobService.getJobByCompanyId(companyId); // Lọc công việc theo companyId
        } else {
            jobs = jobService.getAllJobs(); // Lấy tất cả công việc nếu không có filter
        }
        model.addAttribute("companyId", companyId);
        model.addAttribute("jobs", jobs);
        return "job-list";
    }

    @GetMapping("/job/create")
    public String showJobForm(@RequestParam(required = false) Long companyId, Model model) {
        model.addAttribute("skills", skillService.getAllSkills()); // Thêm kỹ năng vào form
        model.addAttribute("companyId", companyId); // Truyền companyId vào form để tạo công việc
        return "job-form"; // Hiển thị form tạo công việc
    }


    @PostMapping("/job/create")
    public String createJob(Job job,
                            @RequestParam("skills[]") List<Long> skillIds,
                            @RequestParam("skillLevels[]") List<Byte> skillLevels,
                            @RequestParam Long companyId) {

        // Kiểm tra nếu số lượng kỹ năng không khớp với số lượng cấp độ
        if (skillIds.size() != skillLevels.size()) {
            throw new IllegalArgumentException("Mỗi kỹ năng phải có cấp độ tương ứng.");
        }

        // Lấy danh sách kỹ năng từ ID
        List<Skill> skills = skillService.getSkillsByIds(skillIds);

        // Kiểm tra nếu có kỹ năng không hợp lệ
        if (skills.size() != skillIds.size()) {
            throw new IllegalArgumentException("Một số kỹ năng không hợp lệ.");
        }

        // Tạo danh sách JobSkill từ kỹ năng và cấp độ
        List<JobSkill> jobSkills = new ArrayList<>();
        for (int i = 0; i < skills.size(); i++) {
            Skill skill = skills.get(i);
            Byte skillLevel = skillLevels.get(i);

            JobSkill jobSkill = new JobSkill();
            JobSkillId jobSkillId = new JobSkillId();
            jobSkillId.setJobId(job.getId());
            jobSkillId.setSkillId(skill.getId());

            jobSkill.setId(jobSkillId);
            jobSkill.setJob(job);
            jobSkill.setSkill(skill);
            jobSkill.setSkillLevel(skillLevel);

            jobSkills.add(jobSkill);
        }

        // Tìm công ty và gán cho công việc
        Company company = companyService.findById(companyId);
        if (company == null) {
            throw new IllegalArgumentException("Công ty không tồn tại.");
        }
        job.setCompany(company);

        // Lưu công việc và kỹ năng vào database
        jobService.saveJob(job);
        jobSkillService.saveJobSkills(jobSkills);

        return "redirect:/job/jobs?companyId=" + companyId;
    }

    @GetMapping("/job/{id}")
    public String getJobDetail(@PathVariable Long id, Model model) {
        Job job = jobService.getJobById(id);
        model.addAttribute("job", job);

        // Lấy danh sách kỹ năng yêu cầu cho công việc
        List<Skill> jobSkills = jobSkillService.getSkillsByJobId(id);

        // Tìm các ứng viên có kỹ năng phù hợp
        List<Candidate> candidates = candidateService.findCandidatesBySkills(jobSkills);

        // Thêm danh sách ứng viên vào model
        model.addAttribute("candidates", candidates);

        return "job-detail";
    }

    @GetMapping("/suggest-form")
    public String showSuggestForm() {
        return "candidate-id-form";  // Chuyển hướng đến trang nhập ID ứng viên
    }

    @PostMapping("/suggest")
    public String suggestJobs(@RequestParam("candidateId") Long candidateId, Model model) {
        Candidate candidate = candidateService.findById(candidateId);

        if (candidate != null) {
            List<Job> suggestedJobs = jobService.getJobsMatchingSkills(candidateId);
            model.addAttribute("suggestedJobs", suggestedJobs); // Thêm danh sách công việc vào model
        } else {
            model.addAttribute("suggestedJobs", new ArrayList<>()); // Gán danh sách trống nếu không tìm thấy ứng viên
            model.addAttribute("errorMessage", "Không tìm thấy ứng viên với ID đã nhập.");
        }

        return "suggested-jobs"; // Chuyển hướng đến trang hiển thị danh sách công việc
    }

    // Trang hiển thị form nhập ID
    @GetMapping("/candidate/skills")
    public String showForm() {
        return "candidate-id-form";
    }

    // Xử lý form khi ứng viên nhập ID
    @PostMapping("/candidate/skills")
    public String suggestSkills(@RequestParam Long candidateId, Model model) {
        List<Skill> suggestedSkills = candidateService.suggestSkillsForCandidate(candidateId);
        model.addAttribute("suggestedSkills", suggestedSkills);
        return "suggested-skills";
    }

    @GetMapping("/job-search")
    public String showSearchForm() {
        return "job-search";
    }


    @GetMapping("/search")
    public String searchJobs(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer experience,
            @RequestParam(required = false) List<Long> skillIds,
            @RequestParam(required = false) Integer skillLevel,
            Model model
    ) {
        List<Job> jobs = jobService.searchJobs(location, experience, skillIds, skillLevel);
        model.addAttribute("jobs", jobs);
        return "job-search-results";
    }
}
