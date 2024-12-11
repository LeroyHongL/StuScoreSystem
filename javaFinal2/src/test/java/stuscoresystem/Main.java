package stuscoresystem;

import stuscoresystem.sturcture.Role;
import stuscoresystem.sturcture.User;
import stuscoresystem.page.LoginPage;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Role> roles = StudentScoreManagementSystem.initializeRoles();
        Role studentRole = roles.get(0);
        Role teacherRole = roles.get(1);
        Role adminRole = roles.get(2);

        //系统初始化
        User user = new User(null,null,adminRole);
        StudentScoreManagementSystem system = new StudentScoreManagementSystem(user);
        system.addSystemData();
        system.addUser(new User("student","123456",studentRole));
        system.addUser(new User("teacher","123456",teacherRole));
        system.addUser(new User("admin","root",adminRole));


        new LoginPage(system);

    }
}