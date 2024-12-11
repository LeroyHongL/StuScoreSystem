package stuscoresystem.sturcture;

public class Student {
    private String id;
    private String name;
    private String gender;
    private String classInfo;

    public Student(String id, String name, String gender, String classInfo) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.classInfo = classInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }

}
