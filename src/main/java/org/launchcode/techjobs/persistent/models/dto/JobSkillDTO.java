package org.launchcode.techjobs.persistent.models.dto;

import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;

import javax.validation.constraints.NotNull;
import java.util.List;

public class JobSkillDTO {
    @NotNull
    private Job job;

    @NotNull
    private List<Skill> skills;

    public JobSkillDTO() {
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
