package dm2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Apriori {

    public static void main(String[] args) throws IOException {
        List<Student> students = readAndParseData();
        List<String[]> courses = parseCourses(students);
        apriori(students, courses, 0.05);
    }

    private static double supportForCourses(List<Student> students, String[] courseCodes) {
        double support = (double) students
                .parallelStream()
                .filter(student -> {
                    for (String courseCode : courseCodes) {
                        if (!student.getCourseCodes().contains(courseCode)) {
                            return false;
                        }
                    }
                    return true;
                })
                .count() / students.size();
        return support;
    }

    private static String[] union(String[] set1, String[] set2) {
        String[] union = Arrays.copyOf(set1, set1.length + 1);
        union[union.length - 1] = set2[set2.length - 1];
        return union;
    }

    public static boolean arraysEqualToLength(Object[] set1, Object[] set2, int length) {
        for (int i = 0; i < length; i++) {
            if (!set1[i].equals(set2[i])) {
                return false;
            }
        }
        return true;
    }

    public static List<String[]> generate(List<String[]> courseCodes) {
        List<String[]> response = new ArrayList();
        for (int i = 0; i < courseCodes.size() - 1; i++) {
            String[] set1 = courseCodes.get(i);
            for (int j = i + 1; j < courseCodes.size(); j++) {
                String[] set2 = courseCodes.get(j);
                
                if (arraysEqualToLength(set1, set2, set1.length - 1)) {
                    response.add(union(set1, set2));
                } else {
                    // List is sorted so we can skip the rest
                    break;
                }
            }
        }
        return response;
    }

    public static void apriori(List<Student> students, List<String[]> courseCodes, double supportThreshold) {
        while (!courseCodes.isEmpty()) {

            // Print combinations count starting with combinations of size 1
            System.out.println(courseCodes.size());

            // Generate
            courseCodes = generate(courseCodes)
                    // Prune
                    .parallelStream()
                    .filter(candidate -> supportForCourses(students, candidate) >= supportThreshold)
                    .collect(Collectors.toList());
        }
    }

    
    // Helpers

    private static List<Student> readAndParseData() throws IOException {
        List<Student> students = Files
                .readAllLines(Paths.get("src", "data-2016.csv"))
                .stream()
                .map(line -> {
                    String parts[] = line.split(" ");
                    Student student = new Student(parts[0]);

                    for (int i = 1; i < parts.length; i += 5) {
                        String yearMonth = parts[i];
                        String code = parts[i + 1];
                        String name = parts[i + 2].substring(1, parts[i + 2].length() - 1).replace("_", " ");
                        double credits = Double.parseDouble(parts[i + 3]);
                        int grade = Integer.parseInt(parts[i + 4]);
                        student.addCourseEntry(new CourseEntry(yearMonth, code, name, credits, grade));
                    }

                    return student;
                })
                .collect(Collectors.toList());
        return students;
    }
    
    public static List<String[]> parseCourses(List<Student> students) {
        List<CourseEntry> courseEntries = students
                .stream()
                .map(Student::getCourseEntries)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        List<String[]> courses = courseEntries
                .stream()
                .map(CourseEntry::getCode)
                .distinct()
                .sorted()
                .map(code -> new String[]{code})
                .collect(Collectors.toList());
        return courses;
    }

}
