/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dm2016;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author jsal
 */
public class Student {
    private String startingYear;
    private List<CourseEntry> courseEntries;
    private Set<String> courseCodes;

    public Student(String startingYear) {
        this.startingYear = startingYear;
        this.courseEntries = new ArrayList();
        this.courseCodes = new HashSet();
    }

    public void addCourseEntry(CourseEntry courseEntry) {
        this.courseEntries.add(courseEntry);
        courseCodes.add(courseEntry.getCode());
    }

    @Override
    public String toString() {
        return startingYear + " " + courseEntries.size();
    }

    public List<CourseEntry> getCourseEntries() {
        return this.courseEntries;
    }
    
    public Set<String> getCourseCodes() {
        return this.courseCodes;
    }
    
    
}
