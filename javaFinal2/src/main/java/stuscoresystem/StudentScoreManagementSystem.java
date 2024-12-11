package stuscoresystem;

import stuscoresystem.sturcture.*;

import java.util.ArrayList;
import java.util.List;

public class StudentScoreManagementSystem {
    private User currentUser;
    public User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public StudentScoreManagementSystem(User currentUser) {
        this.currentUser = currentUser;
    }

    private List<Student> students = new ArrayList<>();
    private List<Score> scores = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    //添加学生 *
    public void addStudent(Student student){
        if (currentUser.getRole().hasPermission(Permission.ADD_STUDENT)){
            students.add(student);
        }else {
            System.out.println("当前用户没有添加学生的权限");
        }
    }
    //删除学生 *
    public void deleteStudent(String studentId){
        if (currentUser.getRole().hasPermission(Permission.DELETE_STUDENT)){
            students.removeIf(student -> student.getId().equals(studentId));
        }else {
            System.out.println("当前用户没有删除学生的权限");
        }
    }
    //修改学生信息 *已完成
    public void updateStudent(Student updatedStudent){
        if (currentUser.getRole().hasPermission(Permission.UPDATE_STUDENT)){
            for (int i=0;i<students.size();i++){
                if (students.get(i).getId().equals(updatedStudent.getId())){
                    students.set(i,updatedStudent);
                    break;
                }
            }
        }else {
            System.out.println("当前用户没有修改学生的权限");
        }
    }
    //查询学生信息 *
    public Student getStudentById(String studentId){
        if (currentUser.getRole().hasPermission(Permission.GET_STUDENT)){
            for (Student student:students){
                if (student.getId().equals(studentId)){
                    return student;
                }
            }
        }else {
            System.out.println("当前用户没有查询学生信息权限");
        }
        return null;
    }

    //录入成绩 *
    public void addScore(Score score){
        if (currentUser.getRole().hasPermission(Permission.ADD_SCORE)){
            scores.add(score);
        }else {
            System.out.println("当前用户没有录入成绩权限");
        }
    }
    //修改成绩
    public void updateScore(Score updatedScore){
        if (currentUser.getRole().hasPermission(Permission.UPDATE_SCORE)){
            for (int i=0;i<scores.size();i++){
                if (scores.get(i).getStudent().getId().equals(updatedScore.getStudent().getId())
                        &&scores.get(i).getCourse().equals(updatedScore.getCourse())){
                    scores.set(i,updatedScore);
                    break;
                }
            }
        }else {
            System.out.println("当前用户没有修改成绩权限");
        }

    }
    //查询成绩 *已完成
    public Score getScoreByStudentAndCourse(String studentId,String course){
        if (currentUser.getRole().hasPermission(Permission.GET_SCORE)){
            for (Score score:scores){
                if (score.getStudent().getId().equals(studentId)
                        &&score.getCourse().equals(course)){
                    return score;
                }
            }
            return null;
        }else{
            System.out.println("当前用户没有查询成绩权限");
            return null;
        }
    }

    //计算学生平均成绩 *
    public double calculateAverageScoreForStudent(String studentId){
        if (currentUser.getRole().hasPermission(Permission.CALCULATE_STUDENT_AVG_SCORE)){
            double totalScore = 0;
            int count = 0;
            for (Score score:scores){
                if (score.getStudent().getId().equals(studentId)){
                    totalScore += score.getScore();
                    count++;
                }
            }
            return count>0?totalScore/count : 0;
        }else {
            System.out.println("当前用户没有计算学生平均成绩的权限");
            return 0;
        }

    }
    //计算课程平均成绩 *
    public double calculateAverageScoreForCourse(String course){
        if (currentUser.getRole().hasPermission(Permission.CALCULATE_COURSE_AVG_SCORE)){
            double totalScore = 0;
            int count=0;
            for (Score score:scores){
                if (score.getCourse().equals(course)){
                    totalScore += score.getScore();
                    count++;
                }
            }
            return count>0?totalScore/count:0;
        }else {
            System.out.println("当前用户没有计算课程平均成绩的权限");
            return 0;
        }
    }

    //添加用户 *
    public void addUser(User user){
        if (currentUser.getRole().hasPermission(Permission.ADD_USER)){
            users.add(user);
        }else{
            System.out.println("当前用户没有添加用户的权限");
        }
    }
    //修改用户密码 *
    public void updateUserPassword(String username,String newpassword){
        if (currentUser.getRole().hasPermission(Permission.UPDATE_USER_PASSWORD)){
            for (User user:users){
                if (user.getUsername().equals(username)){
                    user.setPassword(newpassword);
                    break;
                }
            }
        }else {
            System.out.println("当前用户没有修改用户密码权限");
        }

    }
    //设置用户权限
    public void updateUserRole(String username, Role newRole){
        if (currentUser.getRole().hasPermission(Permission.UPDATE_USER_ROLE)){
            for (User user:users){
                if (user.getUsername().equals(username)){
                    user.setRole(newRole);
                    break;
                }
            }
        }else{
            System.out.println("当前用户没有设置用户权限的权限");
        }
    }

    public User getUserByUsername(String username){
        for (User user:users){
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    //初始化角色权限
    public static List<Role> initializeRoles(){
        List<Permission> studentPermissions = new ArrayList<>();
        studentPermissions.add(Permission.GET_SCORE);
        studentPermissions.add(Permission.UPDATE_STUDENT);
        studentPermissions.add(Permission.GET_STUDENT);
        Role studentRole = new Role("学生",studentPermissions);

        List<Permission> teacherPermissions = new ArrayList<>();
        teacherPermissions.add(Permission.GET_SCORE);
        teacherPermissions.add(Permission.UPDATE_STUDENT);
        teacherPermissions.add(Permission.GET_STUDENT);
        teacherPermissions.add(Permission.ADD_SCORE);
        teacherPermissions.add(Permission.CALCULATE_COURSE_AVG_SCORE);
        teacherPermissions.add(Permission.CALCULATE_STUDENT_AVG_SCORE);
        Role teacherRole = new Role("教师",teacherPermissions);

        List<Permission> adminPermissions = new ArrayList<>();
        adminPermissions.add(Permission.ADD_STUDENT);
        adminPermissions.add(Permission.DELETE_STUDENT);
        adminPermissions.add(Permission.UPDATE_STUDENT);
        adminPermissions.add(Permission.GET_STUDENT);
        adminPermissions.add(Permission.ADD_SCORE);
        adminPermissions.add(Permission.UPDATE_SCORE);
        adminPermissions.add(Permission.GET_SCORE);
        adminPermissions.add(Permission.CALCULATE_STUDENT_AVG_SCORE);
        adminPermissions.add(Permission.CALCULATE_COURSE_AVG_SCORE);
        adminPermissions.add(Permission.ADD_USER);
        adminPermissions.add(Permission.UPDATE_USER_PASSWORD);
        adminPermissions.add(Permission.UPDATE_USER_ROLE);
        adminPermissions.add(Permission.ADD_SYSTEM_DATA);
        Role adminRole = new Role("管理员", adminPermissions);

        List<Role> roles = new ArrayList<>();
        roles.add(studentRole);
        roles.add(teacherRole);
        roles.add(adminRole);

        return roles;
    }

    //添加系统数据
    public void addSystemData(){
        if (currentUser.getRole().hasPermission(Permission.ADD_SYSTEM_DATA)){
            // 添加学生数据
            addStudent(new Student("1001", "jack", "Male", "001"));
            addStudent(new Student("1002", "lily", "Female", "002"));
            addStudent(new Student("1003", "tom", "Male", "003"));
            addStudent(new Student("1004", "lucy", "Female", "004"));
            addStudent(new Student("1005", "mike", "Male", "005"));
            addStudent(new Student("1006", "anna", "Female", "006"));
            addStudent(new Student("1007", "david", "Male", "007"));
            addStudent(new Student("1008", "emma", "Female", "008"));
            addStudent(new Student("1009", "peter", "Male", "009"));
            addStudent(new Student("1010", "sarah", "Female", "010"));

            // 添加学生成绩数据
            addScore(new Score(getStudentById("1001"), "Math", 85.0));
            addScore(new Score(getStudentById("1001"), "English", 80.0));
            addScore(new Score(getStudentById("1001"), "Physics", 75.0));
            addScore(new Score(getStudentById("1001"), "Physics", 75.0));
            addScore(new Score(getStudentById("1001"), "Chemistry", 82.0));
            addScore(new Score(getStudentById("1001"), "Biology", 88.0));
            addScore(new Score(getStudentById("1001"), "History", 72.0));
            addScore(new Score(getStudentById("1001"), "Geography", 78.0));
            addScore(new Score(getStudentById("1001"), "Computer Science", 90.0));
            addScore(new Score(getStudentById("1001"), "Art", 83.0));
            addScore(new Score(getStudentById("1001"), "Music", 76.0));

            addScore(new Score(getStudentById("1002"), "Math", 88.0));
            addScore(new Score(getStudentById("1002"), "English", 92.0));
            addScore(new Score(getStudentById("1002"), "Physics", 80.0));
            addScore(new Score(getStudentById("1002"), "Chemistry", 85.0));
            addScore(new Score(getStudentById("1002"), "Biology", 90.0));
            addScore(new Score(getStudentById("1002"), "History", 82.0));
            addScore(new Score(getStudentById("1002"), "Geography", 84.0));
            addScore(new Score(getStudentById("1002"), "Computer Science", 95.0));
            addScore(new Score(getStudentById("1002"), "Art", 87.0));
            addScore(new Score(getStudentById("1002"), "Music", 80.0));

            addScore(new Score(getStudentById("1003"), "Math", 78.0));
            addScore(new Score(getStudentById("1003"), "English", 82.0));
            addScore(new Score(getStudentById("1003"), "Physics", 70.0));
            addScore(new Score(getStudentById("1003"), "Chemistry", 75.0));
            addScore(new Score(getStudentById("1003"), "Biology", 80.0));
            addScore(new Score(getStudentById("1003"), "History", 70.0));
            addScore(new Score(getStudentById("1003"), "Geography", 72.0));
            addScore(new Score(getStudentById("1003"), "Computer Science", 85.0));
            addScore(new Score(getStudentById("1003"), "Art", 77.0));
            addScore(new Score(getStudentById("1003"), "Music", 70.0));

            addScore(new Score(getStudentById("1004"), "Math", 82.0));
            addScore(new Score(getStudentById("1004"), "English", 86.0));
            addScore(new Score(getStudentById("1004"), "Physics", 75.0));
            addScore(new Score(getStudentById("1004"), "Chemistry", 80.0));
            addScore(new Score(getStudentById("1004"), "Biology", 85.0));
            addScore(new Score(getStudentById("1004"), "History", 78.0));
            addScore(new Score(getStudentById("1004"), "Geography", 80.0));
            addScore(new Score(getStudentById("1004"), "Computer Science", 90.0));
            addScore(new Score(getStudentById("1004"), "Art", 82.0));
            addScore(new Score(getStudentById("1004"), "Music", 75.0));

            addScore(new Score(getStudentById("1005"), "Math", 88.0));
            addScore(new Score(getStudentById("1005"), "English", 90.0));
            addScore(new Score(getStudentById("1005"), "Physics", 82.0));
            addScore(new Score(getStudentById("1005"), "Chemistry", 85.0));
            addScore(new Score(getStudentById("1005"), "Biology", 92.0));
            addScore(new Score(getStudentById("1005"), "History", 80.0));
            addScore(new Score(getStudentById("1005"), "Geography", 84.0));
            addScore(new Score(getStudentById("1005"), "Computer Science", 95.0));
            addScore(new Score(getStudentById("1005"), "Art", 87.0));
            addScore(new Score(getStudentById("1005"), "Music", 80.0));

            addScore(new Score(getStudentById("1006"), "Math", 75.0));
            addScore(new Score(getStudentById("1006"), "English", 80.0));
            addScore(new Score(getStudentById("1006"), "Physics", 70.0));
            addScore(new Score(getStudentById("1006"), "Chemistry", 75.0));
            addScore(new Score(getStudentById("1006"), "Biology", 80.0));
            addScore(new Score(getStudentById("1006"), "History", 72.0));
            addScore(new Score(getStudentById("1006"), "Geography", 78.0));
            addScore(new Score(getStudentById("1006"), "Computer Science", 85.0));
            addScore(new Score(getStudentById("1006"), "Art", 77.0));
            addScore(new Score(getStudentById("1006"), "Music", 70.0));

            addScore(new Score(getStudentById("1007"), "Math", 80.0));
            addScore(new Score(getStudentById("1007"), "English", 84.0));
            addScore(new Score(getStudentById("1007"), "Physics", 72.0));
            addScore(new Score(getStudentById("1007"), "Chemistry", 78.0));
            addScore(new Score(getStudentById("1007"), "Biology", 82.0));
            addScore(new Score(getStudentById("1007"), "History", 75.0));
            addScore(new Score(getStudentById("1007"), "Geography", 80.0));
            addScore(new Score(getStudentById("1007"), "Computer Science", 88.0));
            addScore(new Score(getStudentById("1007"), "Art", 80.0));
            addScore(new Score(getStudentById("1007"), "Music", 72.0));

            addScore(new Score(getStudentById("1008"), "Math", 90.0));
            addScore(new Score(getStudentById("1008"), "English", 92.0));
            addScore(new Score(getStudentById("1008"), "Physics", 85.0));
            addScore(new Score(getStudentById("1008"), "Chemistry", 88.0));
            addScore(new Score(getStudentById("1008"), "Biology", 90.0));
            addScore(new Score(getStudentById("1008"), "History", 82.0));
            addScore(new Score(getStudentById("1008"), "Geography", 84.0));
            addScore(new Score(getStudentById("1008"), "Computer Science", 95.0));
            addScore(new Score(getStudentById("1008"), "Art", 87.0));
            addScore(new Score(getStudentById("1008"), "Music", 80.0));

            addScore(new Score(getStudentById("1009"), "Math", 72.0));
            addScore(new Score(getStudentById("1009"), "English", 76.0));
            addScore(new Score(getStudentById("1009"), "Physics", 68.0));
            addScore(new Score(getStudentById("1009"), "Chemistry", 72.0));
            addScore(new Score(getStudentById("1009"), "Biology", 75.0));
            addScore(new Score(getStudentById("1009"), "History", 68.0));
            addScore(new Score(getStudentById("1009"), "Geography", 70.0));
            addScore(new Score(getStudentById("1009"), "Computer Science", 78.0));
            addScore(new Score(getStudentById("1009"), "Art", 70.0));
            addScore(new Score(getStudentById("1009"), "Music", 65.0));

            addScore(new Score(getStudentById("1010"), "Math", 85.0));
            addScore(new Score(getStudentById("1010"), "English", 88.0));
            addScore(new Score(getStudentById("1010"), "Physics", 80.0));
            addScore(new Score(getStudentById("1010"), "Chemistry", 82.0));
            addScore(new Score(getStudentById("1010"), "Biology", 85.0));
            addScore(new Score(getStudentById("1010"), "History", 78.0));
            addScore(new Score(getStudentById("1010"), "Geography", 80.0));
            addScore(new Score(getStudentById("1010"), "Computer Science", 90.0));
            addScore(new Score(getStudentById("1010"), "Art", 82.0));
            addScore(new Score(getStudentById("1010"), "Music", 75.));

        }else{
            System.out.println("当前用户没有添加用户数据的权限");
        }
    }
}
