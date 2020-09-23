/**
 * 
 */
package com.example.demo.student;

/**
 * @author ezak4
 *
 */
public class Student {
    private final Integer studentId;
    private final String studentName;
    /**
     * @return the studentId
     */
    public Integer getStudentId() {
        return studentId;
    }
    /**
     * @return the studentName
     */
    public String getStudentName() {
        return studentName;
    }
    /**
     * @param studentId
     * @param studentName
     */
    public Student(Integer studentId, String studentName) {
        super();
        this.studentId   = studentId;
        this.studentName = studentName;
    }
    
    
    
}
