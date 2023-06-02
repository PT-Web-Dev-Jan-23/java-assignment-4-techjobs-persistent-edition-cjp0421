package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("skills")
public class SkillController {
    @Autowired
    private SkillRepository skillRepository;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("skills",skillRepository.findAll());
        return "skills/index";
    }


    @GetMapping("view")
    public String displayAllSkills(@RequestParam(required=false) Integer skillId, Model model){
        if(skillId == null){
            model.addAttribute("title", "All Skills");
            model.addAttribute("skills", skillRepository.findAll());
        } else {
            Optional<Skill> result = skillRepository.findById(skillId);
            if(result.isEmpty()){
                model.addAttribute("title","Invalid Skill Id: " + skillId);
            } else {
                Skill skill = result.get();
                model.addAttribute("title", "Skill with ID" + skillId);
                model.addAttribute("skill",skill.getName());
            }
        }
        return "skills/index";
    }

    @GetMapping("add")
    public String displayAddSkillForm(Model model) {
        model.addAttribute(new Skill());
        return "skills/add";
    }


    @PostMapping("add")
    public String processAddSkillForm(@ModelAttribute @Valid Skill newSkill,
                                         Errors errors, Model model) {

        if(!errors.hasErrors()) {

            if (!skillRepository.toString().contains(newSkill.getName())) {
                Skill skill = skillRepository.save(newSkill);
                model.addAttribute("skill", skill);
            }
            return "redirect:";
        }

        return "redirect:";
    }

    @GetMapping("view/{skillId}")
    public String displayViewSkill(Model model, @PathVariable int skillId) {

        Optional optSkill = skillRepository.findById(skillId);
        if (optSkill.isPresent()) {
            Skill skill = (Skill) optSkill.get();
            model.addAttribute("skill", skill);
            return "skills/view";
        } else {
            return "redirect:../";
        }
    }

}
