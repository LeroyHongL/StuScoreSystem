package stuscoresystem.page;
import stuscoresystem.StudentScoreManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private StudentScoreManagementSystem system;


    public LoginPage(StudentScoreManagementSystem system) {
        this.system = system;
        frame = new JFrame("登录页面");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(700, 300, 500, 500);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1));

        JLabel titleLabel = new JLabel("欢迎使用学生管理系统");
        JPanel titlePanel = new JPanel();
        titlePanel.add(titleLabel);

        // 账号相关组件部分
        JPanel usernamePanel = new JPanel();
        JLabel usernameLabel = new JLabel("账号:");
        usernamePanel.add(usernameLabel);

        usernameField = new JTextField(20);
        usernamePanel.add(usernameField);

        // 密码相关组件部分
        JPanel passwordPanel = new JPanel();
        JLabel passwordLabel = new JLabel("密码:");
        passwordPanel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordField);

        // 登录按钮部分
        JPanel loginButtonPanel = new JPanel();

        JButton loginButton = new JButton("登录");
        loginButtonPanel.add(loginButton);


        Dimension size = new Dimension(245, 30);
        loginButtonPanel.setPreferredSize(size);

        loginButton.addActionListener(new LoginButtonListener());

        //主面板
        mainPanel.add(titlePanel);
        mainPanel.add(usernamePanel);
        mainPanel.add(passwordPanel);
        mainPanel.add(loginButtonPanel);

        //主框架
        frame.add(mainPanel);
        frame.setVisible(true);

    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);

            if ("student".equals(username) && "123456".equals(password)) {
                StudentPage studentPage = new StudentPage();
                studentPage.openStudentPage(system);
                system.setCurrentUser(system.getUserByUsername(username));
                frame.setVisible(false);  // 隐藏登录界面
            } else if ("teacher".equals(username) && "123456".equals(password)) {
                TeacherPage teacherPage = new TeacherPage();
                teacherPage.openTeacherPage(system);
                system.setCurrentUser(system.getUserByUsername(username));
                frame.setVisible(false);  // 隐藏登录界面
            } else if ("admin".equals(username) && "root".equals(password)) {
                AdminPage adminPage = new AdminPage();
                adminPage.openAdminPage(system);
                system.setCurrentUser(system.getUserByUsername(username));
                frame.setVisible(false);  // 隐藏登录界面
            } else {
                JOptionPane.showMessageDialog(frame, "账号或密码错误，请重新输入");
            }
        }
    }

}

