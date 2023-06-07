package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.data.EmployerRepository;
import org.launchcode.techjobs.persistent.data.JobRepository;
import org.launchcode.techjobs.persistent.models.JobData;
import org.launchcode.techjobs.persistent.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "list")
public class ListController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private JobRepository jobRepository;

    static HashMap<String, String> columnChoices = new HashMap<>();

    public ListController () {

        columnChoices.put("all", "All");
        columnChoices.put("employer", "Employer");
        columnChoices.put("skill", "Skill");

    }

    @RequestMapping("")
    public String list(Model model) {
        Iterable<Job> jobs;
        Iterable<Employer> employers = new ArrayList<>();
        Iterable<Skill> skills = new ArrayList<>();
        jobs = jobRepository.findAll();
        employers = employerRepository.findAll();
        skills = skillRepository.findAll();
        model.addAttribute("jobs",jobs);
        model.addAttribute("employers", employers);
        model.addAttribute("skills", skills);
        return "list";
    }

    @RequestMapping(value = "jobs")
    public String listJobsByColumnAndValue(Model model, @RequestParam String column, @RequestParam String value) {
        Iterable<Job> jobs = jobRepository.findAll();
        Iterable<Employer> employers = employerRepository.findAll();
        Iterable<Skill> skills = skillRepository.findAll();
        if (column.toLowerCase().equals("all")){
           model.addAttribute("employers", employers);
           model.addAttribute("jobs",jobs);
           model.addAttribute("skills",skills);
            model.addAttribute("title", "All Jobs");
        } else {
            jobs = JobData.findByColumnAndValue(column, value, jobRepository.findAll());
            model.addAttribute("title", "Jobs with " + columnChoices.get(column) + ": " + value);


        }
        model.addAttribute("jobs",jobs);
        model.addAttribute("employers", employers);
        model.addAttribute("skills", skills);


        return "list-jobs";
    }
}
