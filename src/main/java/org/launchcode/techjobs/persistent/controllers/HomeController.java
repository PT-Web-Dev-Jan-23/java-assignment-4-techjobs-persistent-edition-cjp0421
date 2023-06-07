package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private JobRepository jobRepository;

    @RequestMapping("")
    public String index(Model model) {

        model.addAttribute("title", "My Jobs");
        model.addAttribute("jobs", jobRepository.findAll());
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute(new Job());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                       Errors errors, Model model, @RequestParam int employerId, @RequestParam List<Integer> skills) {
       // Optional<Employer> employer = employerRepository.findById(employerId);
        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
        newJob.setSkills(skillObjs);
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "add";
        } else {
            //Optional<Job> job = jobRepository.findById(newJob.getId());
            jobRepository.save(newJob);
            model.addAttribute("employers", employerRepository.findAll());
            //model.addAttribute("skills", skillObjs);
            model.addAttribute("employer",employerRepository.findById(employerId));
            model.addAttribute("skills",skillRepository.findAll());
           // model.addAttribute("job",job);

        }
        //jobRepository.save(newJob);
        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
        Optional<Job> optionalJob = jobRepository.findById(jobId);

        if(optionalJob.isPresent()){
            Job job = (Job) optionalJob.get();
            model.addAttribute("job",job);
            return "view";
        } else{
            model.addAttribute("title", "Invalid Job ID");
        }


//        if(result.isEmpty()){
//
//
//        } else {
         //   Job job = result.get();

            //model.addAttribute("title", job.getName());
//            model.addAttribute("job", job);
//            model.addAttribute("employers",employerRepository.findAll());
//            model.addAttribute("skills",skillRepository.findAll());
//            //model.addAttribute("employer",job.getEmployer());
//           // model.addAttribute("skills", job.getSkills());

//        }

        return "view";
    }


}
