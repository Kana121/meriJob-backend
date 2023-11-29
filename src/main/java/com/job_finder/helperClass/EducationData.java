package com.job_finder.helperClass;

import java.util.List;

import jakarta.persistence.ElementCollection;
import lombok.Data;

@Data
public class EducationData {

	
    private List<String> qualification;

    private String course;
    private String specialize;
    private String uni;
    
    @ElementCollection
    private List<String> courseType;
    private String passYear;
}
