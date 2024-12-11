package stuscoresystem.page;

import stuscoresystem.*;
import stuscoresystem.sturcture.Role;
import stuscoresystem.sturcture.Score;
import stuscoresystem.sturcture.Student;
import stuscoresystem.sturcture.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AdminPage {
    public void openAdminPage(StudentScoreManagementSystem system){
        JFrame adminFrame = new JFrame("管理员端页面");
        adminFrame.setBounds(700, 300, 700, 500);

        // 获取屏幕尺寸并计算居中位置
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int x = (screenWidth - adminFrame.getWidth()) / 2;
        int y = (screenHeight - adminFrame.getHeight()) / 2;
        adminFrame.setLocation(x, y);

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new FlowLayout());

        JPanel studentPanel = new JPanel();
        studentPanel.setLayout(new FlowLayout());

        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new FlowLayout());

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout());

        JLabel adminLabel = new JLabel("欢迎来到管理员端，你具有添加学生、管理用户等各种权限");
        JButton addStudentButton = new JButton("添加学生");
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建一个新的对话框用于添加学生信息
                JDialog studentDialog = new JDialog(adminFrame, "添加学生", true);
                studentDialog.setLayout(new BorderLayout());
                studentDialog.setSize(400, 300);

                // 计算对话框居中位置
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int screenWidth = screenSize.width;
                int screenHeight = screenSize.height;
                int x = (screenWidth - studentDialog.getWidth()) / 2;
                int y = (screenHeight - studentDialog.getHeight()) / 2;
                studentDialog.setLocation(x, y);

                JPanel inputPanel = new JPanel();
                inputPanel.setLayout(new GridLayout(4, 2));

                JLabel idLabel = new JLabel("学号:");
                JTextField idTextField = new JTextField();
                inputPanel.add(idLabel);
                inputPanel.add(idTextField);

                JLabel nameLabel = new JLabel("姓名:");
                JTextField nameTextField = new JTextField();
                inputPanel.add(nameLabel);
                inputPanel.add(nameTextField);

                JLabel genderLabel = new JLabel("性别:");
                JTextField genderTextField = new JTextField();
                inputPanel.add(genderLabel);
                inputPanel.add(genderTextField);

                JLabel classLabel = new JLabel("班级:");
                JTextField classTextField = new JTextField();
                inputPanel.add(classLabel);
                inputPanel.add(classTextField);

                JPanel buttonPanel = new JPanel();
                JButton saveButton = new JButton("保存");
                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        String studentId = idTextField.getText().trim();
                        String studentName = nameTextField.getText().trim();
                        String studentGender = genderTextField.getText().trim();
                        String studentClass = classTextField.getText().trim();

                        // 先判断学号是否已存在，通过调用系统的方法（假设系统中有相应查询方法）
                        Student existingStudent = system.getStudentById(studentId);
                        if (existingStudent != null) {
                            JOptionPane.showMessageDialog(studentDialog, "该学号已存在，请重新输入", "添加学生失败", JOptionPane.ERROR_MESSAGE);
                        } else {
                            Student newStudent = new Student(studentId, studentName, studentGender, studentClass);
                            system.addStudent(newStudent);
                            JOptionPane.showMessageDialog(studentDialog, "学生添加成功");
                            studentDialog.dispose();
                        }
                    }
                });
                buttonPanel.add(saveButton);

                studentDialog.add(inputPanel, BorderLayout.CENTER);
                studentDialog.add(buttonPanel, BorderLayout.SOUTH);
                studentDialog.setVisible(true);
            }
        });

        JButton deleteStudentButton = new JButton("删除学生");
        deleteStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出对话框让管理员输入要删除学生的学号
                String studentId = JOptionPane.showInputDialog(adminFrame, "请输入要删除学生的学号");

                if (studentId != null && !studentId.isEmpty()) {
                    Student student = system.getStudentById(studentId);
                    if (student != null) {
                        system.deleteStudent(studentId);  // 假设系统中有根据学号删除学生的方法
                        JOptionPane.showMessageDialog(adminFrame, "学生已成功删除");
                    } else {
                        JOptionPane.showMessageDialog(adminFrame, "系统中不存在该学号对应的学生，无法删除");
                    }
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "请输入有效的学号");
                }
            }
        });

        JButton updateStudentButton = new JButton("修改学生信息");
        updateStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出对话框让管理员输入要修改学生的学号
                String studentId = JOptionPane.showInputDialog(adminFrame, "请输入要修改学生的学号");
                Student student = system.getStudentById(studentId);
                if (student != null) {
                    // 弹出对话框让管理员选择修改的属性（姓名、性别或班级信息）
                    String[] options = {"姓名", "性别", "班级信息"};
                    String selectedOption = (String) JOptionPane.showInputDialog(adminFrame, "请选择要修改的属性", "修改属性选择",
                            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (selectedOption != null) {
                        String newValue;
                        if ("姓名".equals(selectedOption)) {
                            newValue = JOptionPane.showInputDialog(adminFrame, "请输入新的姓名", student.getName());
                        } else if ("性别".equals(selectedOption)) {
                            newValue = JOptionPane.showInputDialog(adminFrame, "请输入新的性别", student.getGender());
                        } else {
                            newValue = JOptionPane.showInputDialog(adminFrame, "请输入新的班级信息", student.getClassInfo());
                        }
                        if (newValue != null) {
                            // 根据选择修改相应属性构建新的 Student 对象
                            Student updatedStudent = new Student(student.getId(),
                                    "姓名".equals(selectedOption) ? newValue : student.getName(),
                                    "性别".equals(selectedOption) ? newValue : student.getGender(),
                                    "班级信息".equals(selectedOption) ? newValue : student.getClassInfo());
                            system.updateStudent(updatedStudent);
                            JOptionPane.showMessageDialog(adminFrame, "学生信息修改成功");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "系统中不存在该学号对应的学生，无法修改");
                }
            }
        });

        JButton getStudentButton = new JButton("查看学生信息");
        getStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出对话框让管理员输入学号
                String studentId = JOptionPane.showInputDialog(adminFrame, "请输入学生学号");
                Student student = system.getStudentById(studentId);
                if (student != null) {
                    StringBuilder info = new StringBuilder("学生个人信息如下：\n");
                    info.append("姓名: ").append(student.getName()).append("\n");
                    info.append("学号: ").append(student.getId()).append("\n");
                    info.append("性别: ").append(student.getGender()).append("\n");
                    info.append("班级: ").append(student.getClassInfo()).append("\n");
                    JOptionPane.showMessageDialog(adminFrame, info.toString());
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "系统中不存在该学号对应的学生，请重新输入");
                }
            }
        });

        JButton addScoreButton = new JButton("录入成绩");
        addScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = JOptionPane.showInputDialog(adminFrame, "请输入学生学号");
                final Student student;
                Student retrievedStudent = system.getStudentById(studentId);
                if (retrievedStudent == null) {
                    student = new Student(studentId, null, null, null);
                    system.addStudent(student);
                } else {
                    student = retrievedStudent;
                }
                // 创建一个新的对话框用于录入成绩
                JDialog scoreDialog = new JDialog(adminFrame, "录入成绩", true);
                scoreDialog.setLayout(new BorderLayout());
                scoreDialog.setSize(400, 300);

                // 计算对话框居中位置
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int screenWidth = screenSize.width;
                int screenHeight = screenSize.height;
                int x = (screenWidth - scoreDialog.getWidth()) / 2;
                int y = (screenHeight - scoreDialog.getHeight()) / 2;
                scoreDialog.setLocation(x, y);

                JPanel coursesPanel = new JPanel();
                coursesPanel.setLayout(new GridLayout(11, 2));

                // 课程名称数组
                String[] courses = {"Math", "English", "Physics", "Chemistry", "Biology", "History", "Geography", "Computer Science", "Art", "Music"};
                java.util.List<JTextField> scoreFields = new ArrayList<>();

                for (String course : courses) {
                    coursesPanel.add(new JLabel(course));
                    JTextField scoreField = new JTextField();
                    scoreFields.add(scoreField);
                    coursesPanel.add(scoreField);
                }

                JButton saveButton = new JButton("保存成绩");
                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        java.util.List<Score> scoresToAdd = new ArrayList<>();
                        for (int i = 0; i < courses.length; i++) {
                            String scoreStr = scoreFields.get(i).getText().trim();
                            double score;
                            if (scoreStr.isEmpty()) {
                                score = 0;
                            } else {
                                try {
                                    score = Double.parseDouble(scoreStr);
                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(scoreDialog, "成绩输入格式错误，请重新输入");
                                    return;
                                }
                            }
                            scoresToAdd.add(new Score(student, courses[i], score));
                        }
                        for (Score score : scoresToAdd) {
                            system.addScore(score);
                        }
                        JOptionPane.showMessageDialog(scoreDialog, "成绩录入成功");
                        scoreDialog.dispose();
                    }
                });

                JPanel buttonPanel = new JPanel();
                buttonPanel.add(saveButton);

                scoreDialog.add(coursesPanel, BorderLayout.CENTER);
                scoreDialog.add(buttonPanel, BorderLayout.SOUTH);
                scoreDialog.setVisible(true);
            }
        });

        JButton updateScoreButton = new JButton("修改成绩");
        updateScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出对话框让管理员输入学号
                String studentId = JOptionPane.showInputDialog(adminFrame, "请输入学生学号");
                Student student = system.getStudentById(studentId);
                if (student != null) {
                    // 课程名称数组
                    String[] courses = {"Math", "English", "Physics", "Chemistry", "Biology", "History", "Geography", "Computer Science", "Art", "Music"};
                    java.util.List<Score> studentScores = new ArrayList<>();
                    for (String course : courses) {
                        Score score = system.getScoreByStudentAndCourse(studentId, course);
                        if (score != null) {
                            studentScores.add(score);
                        }
                    }

                    if (!studentScores.isEmpty()) {
                        // 创建一个新的对话框用于修改成绩
                        JDialog scoreDialog = new JDialog(adminFrame, "修改成绩", true);
                        scoreDialog.setLayout(new BorderLayout());
                        scoreDialog.setSize(400, 300);

                        // 计算对话框居中位置
                        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                        int screenWidth = screenSize.width;
                        int screenHeight = screenSize.height;
                        int x = (screenWidth - scoreDialog.getWidth()) / 2;
                        int y = (screenHeight - scoreDialog.getHeight()) / 2;
                        scoreDialog.setLocation(x, y);

                        JPanel coursesPanel = new JPanel();
                        coursesPanel.setLayout(new GridLayout(11, 2));
                        java.util.List<JTextField> scoreFields = new ArrayList<>();

                        for (Score score : studentScores) {
                            String course = score.getCourse();
                            coursesPanel.add(new JLabel(course));
                            JTextField scoreField = new JTextField(String.valueOf(score.getScore()));
                            scoreFields.add(scoreField);
                            coursesPanel.add(scoreField);
                        }

                        JButton saveButton = new JButton("保存");
                        saveButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                java.util.List<Score> scoresToUpdate = new ArrayList<>();
                                for (int i = 0; i < scoreFields.size(); i++) {
                                    String scoreStr = scoreFields.get(i).getText().trim();
                                    double score;
                                    if (scoreStr.isEmpty()) {
                                        continue;
                                    } else {
                                        try {
                                            score = Double.parseDouble(scoreStr);
                                        } catch (NumberFormatException ex) {
                                            JOptionPane.showMessageDialog(scoreDialog, "成绩输入格式错误，请重新输入");
                                            return;
                                        }
                                    }
                                    Score oldScore = studentScores.get(i);
                                    Score updatedScore = new Score(oldScore.getStudent(), oldScore.getCourse(), score);
                                    scoresToUpdate.add(updatedScore);
                                }
                                for (Score score : scoresToUpdate) {
                                    system.updateScore(score);
                                }
                                JOptionPane.showMessageDialog(scoreDialog, "成绩修改成功");
                                scoreDialog.dispose();
                            }
                        });

                        JPanel buttonPanel = new JPanel();
                        buttonPanel.add(saveButton);

                        scoreDialog.add(coursesPanel, BorderLayout.CENTER);
                        scoreDialog.add(buttonPanel, BorderLayout.SOUTH);
                        scoreDialog.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(adminFrame, "该学生暂无成绩记录，无法修改");
                    }
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "系统中不存在该学号对应的学生，无法修改成绩");
                }
            }
        });

        JButton getScoreButton = new JButton("查询学生成绩");
        getScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = JOptionPane.showInputDialog(adminFrame, "请输入学生学号");
                // 定义课程选择器的选项
                String[] courses = {"Math", "English", "Physics", "Chemistry", "Biology", "History", "Geography", "Computer Science", "Art", "Music"};
                // 弹出课程选择器对话框
                String course = (String) JOptionPane.showInputDialog(
                        adminFrame,
                        "请选择课程",
                        "课程选择",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        courses,
                        courses[0]);

                if (course == null) {
                    JOptionPane.showMessageDialog(adminFrame, "必须选择课程");
                    return;
                }
                Score scoreObj = system.getScoreByStudentAndCourse(studentId, course);
                if (scoreObj != null) {
                    StringBuilder scoreInfo = new StringBuilder("成绩如下：\n");
                    scoreInfo.append(scoreObj.getCourse()).append(": ").append(scoreObj.getScore()).append("\n");
                    JOptionPane.showMessageDialog(adminFrame, scoreInfo.toString());
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "未找到该课程成绩");
                }
            }
        });

        JButton calculateCourseAvgScoreButton = new JButton("计算课程平均成绩");
        calculateCourseAvgScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 定义课程选择器的选项
                String[] courses = {"Math", "English", "Physics", "Chemistry", "Biology", "History", "Geography", "Computer Science", "Art", "Music"};
                // 弹出课程选择器对话框，确保用户选择了课程
                String course = null;
                while (course == null) {
                    course = (String) JOptionPane.showInputDialog(
                            adminFrame,
                            "请选择课程",
                            "课程选择",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            courses,
                            courses[0]);
                }
                double avgScore = system.calculateAverageScoreForCourse(course);
                if (avgScore == 0) {
                    JOptionPane.showMessageDialog(adminFrame, "该课程暂无成绩记录");
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "该课程的平均成绩为：" + avgScore);
                }
            }
        });

        JButton calculateStudentAvgScoreButton = new JButton("计算学生平均成绩");
        calculateStudentAvgScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出对话框让教师输入学号
                String studentId = JOptionPane.showInputDialog(adminFrame, "请输入学生学号");
                Student student = system.getStudentById(studentId);
                if (student == null) {
                    JOptionPane.showMessageDialog(adminFrame, "系统中未找到该学生，请重新输入");
                } else {
                    double avgScore = system.calculateAverageScoreForStudent(studentId);
                    if (avgScore == 0) {
                        JOptionPane.showMessageDialog(adminFrame, "该学生暂无成绩记录");
                    } else {
                        JOptionPane.showMessageDialog(adminFrame, "该学生的平均成绩为：" + avgScore);
                    }
                }
            }
        });

        JButton addUserButton = new JButton("添加用户");
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建第一个对话框用于输入用户名、密码和选择角色
                JDialog userDialog1 = new JDialog(adminFrame, "添加用户 - 步骤一", true);
                userDialog1.setLayout(new BorderLayout());
                userDialog1.setSize(400, 200);

                // 计算对话框居中位置
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int screenWidth = screenSize.width;
                int screenHeight = screenSize.height;
                int x = (screenWidth - userDialog1.getWidth()) / 2;
                int y = (screenHeight - userDialog1.getHeight()) / 2;
                userDialog1.setLocation(x, y);

                JPanel inputPanel1 = new JPanel();
                inputPanel1.setLayout(new GridLayout(3, 2));

                JLabel usernameLabel = new JLabel("用户名:");
                JTextField usernameTextField = new JTextField();
                inputPanel1.add(usernameLabel);
                inputPanel1.add(usernameTextField);

                JLabel passwordLabel = new JLabel("密码:");
                JPasswordField passwordField = new JPasswordField();
                inputPanel1.add(passwordLabel);
                inputPanel1.add(passwordField);

                JLabel roleLabel = new JLabel("角色:");
                String[] roles = {"studentRole", "teacherRole", "adminRole"};
                JComboBox<String> roleComboBox = new JComboBox<>(roles);
                inputPanel1.add(roleLabel);
                inputPanel1.add(roleComboBox);

                JPanel buttonPanel1 = new JPanel();
                JButton nextButton = new JButton("下一项");
                nextButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        String username = usernameTextField.getText().trim();
                        char[] passwordChars = passwordField.getPassword();
                        String password = new String(passwordChars);
                        String selectedRole = (String) roleComboBox.getSelectedItem();

                        // 简单验证用户名和密码不为空
                        if (username.isEmpty()) {
                            JOptionPane.showMessageDialog(userDialog1, "用户名不能为空，请重新输入");
                            return;
                        }
                        if (password.isEmpty()) {
                            JOptionPane.showMessageDialog(userDialog1, "密码不能为空，请重新输入");
                            return;
                        }

                        // 隐藏当前对话框
                        userDialog1.setVisible(false);

                        // 创建第二个对话框用于再次输入密码进行确认
                        JDialog userDialog2 = new JDialog(adminFrame, "添加用户 - 步骤二", true);
                        userDialog2.setLayout(new BorderLayout());
                        userDialog2.setSize(300, 150);

                        // 计算对话框居中位置
                        int x2 = (screenWidth - userDialog2.getWidth()) / 2;
                        int y2 = (screenHeight - userDialog2.getHeight()) / 2;
                        userDialog2.setLocation(x2, y2);

                        JPanel inputPanel2 = new JPanel();
                        inputPanel2.setLayout(new GridLayout(2, 1));

                        JLabel confirmPasswordLabel = new JLabel("请再次输入密码:");
                        JPasswordField confirmPasswordField = new JPasswordField();
                        inputPanel2.add(confirmPasswordLabel);
                        inputPanel2.add(confirmPasswordField);

                        JPanel buttonPanel2 = new JPanel();
                        JButton saveButton = new JButton("保存");
                        saveButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent ev) {
                                char[] confirmPasswordChars = confirmPasswordField.getPassword();
                                String confirmPassword = new String(confirmPasswordChars);

                                if (password.equals(confirmPassword)) {
                                    java.util.List<Role> roleList = StudentScoreManagementSystem.initializeRoles();
                                    Role role = null;
                                    switch (selectedRole){
                                        case "studentRole":
                                            role = roleList.get(0);
                                            break;
                                        case "teacherRole":
                                            role = roleList.get(1);
                                            break;
                                        case "adminRole":
                                            role = roleList.get(2);
                                            break;
                                    }
                                    User newUser = new User(username, password, role);
                                    system.addUser(newUser);
                                    JOptionPane.showMessageDialog(userDialog2, "用户添加成功");
                                    userDialog2.dispose();
                                } else {
                                    JOptionPane.showMessageDialog(userDialog2, "两次输入的密码不一致，请重新输入");
                                }
                            }
                        });
                        buttonPanel2.add(saveButton);

                        userDialog2.add(inputPanel2, BorderLayout.CENTER);
                        userDialog2.add(buttonPanel2, BorderLayout.SOUTH);
                        userDialog2.setVisible(true);
                    }
                });
                buttonPanel1.add(nextButton);

                userDialog1.add(inputPanel1, BorderLayout.CENTER);
                userDialog1.add(buttonPanel1, BorderLayout.SOUTH);
                userDialog1.setVisible(true);
            }
        });

        JButton updateUserPasswordButton = new JButton("修改用户密码");
        updateUserPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出对话框让管理员输入要修改密码的用户名
                String username = JOptionPane.showInputDialog(adminFrame, "请输入要修改密码的用户名");
                User user = system.getUserByUsername(username);
                if (user!= null) {
                    // 创建第一个对话框用于输入新密码
                    JDialog passwordDialog1 = new JDialog(adminFrame, "修改用户密码 - 步骤一", true);
                    passwordDialog1.setLayout(new BorderLayout());
                    passwordDialog1.setSize(300, 150);

                    // 计算对话框居中位置
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    int screenWidth = screenSize.width;
                    int screenHeight = screenSize.height;
                    int x = (screenWidth - passwordDialog1.getWidth()) / 2;
                    int y = (screenHeight - passwordDialog1.getHeight()) / 2;
                    passwordDialog1.setLocation(x, y);

                    JPanel inputPanel1 = new JPanel();
                    inputPanel1.setLayout(new GridLayout(2, 1));

                    JLabel newPasswordLabel = new JLabel("请输入新密码:");
                    JPasswordField newPasswordField = new JPasswordField();
                    inputPanel1.add(newPasswordLabel);
                    inputPanel1.add(newPasswordField);

                    JPanel buttonPanel1 = new JPanel();
                    JButton nextButton = new JButton("下一项");
                    nextButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            char[] newPasswordChars = newPasswordField.getPassword();
                            String newPassword = new String(newPasswordChars);

                            // 简单验证新密码不为空
                            if (newPassword.isEmpty()) {
                                JOptionPane.showMessageDialog(passwordDialog1, "新密码不能为空，请重新输入");
                                return;
                            }

                            // 隐藏当前对话框
                            passwordDialog1.setVisible(false);

                            // 创建第二个对话框用于再次输入新密码进行确认
                            JDialog passwordDialog2 = new JDialog(adminFrame, "修改用户密码 - 步骤二", true);
                            passwordDialog2.setLayout(new BorderLayout());
                            passwordDialog2.setSize(300, 150);

                            // 计算对话框居中位置
                            int x2 = (screenWidth - passwordDialog2.getWidth()) / 2;
                            int y2 = (screenHeight - passwordDialog2.getHeight()) / 2;
                            passwordDialog2.setLocation(x2, y2);

                            JPanel inputPanel2 = new JPanel();
                            inputPanel2.setLayout(new GridLayout(2, 1));

                            JLabel confirmPasswordLabel = new JLabel("请再次输入新密码:");
                            JPasswordField confirmPasswordField = new JPasswordField();
                            inputPanel2.add(confirmPasswordLabel);
                            inputPanel2.add(confirmPasswordField);

                            JPanel buttonPanel2 = new JPanel();
                            JButton saveButton = new JButton("保存");
                            saveButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent ev) {
                                    char[] confirmPasswordChars = confirmPasswordField.getPassword();
                                    String confirmPassword = new String(confirmPasswordChars);

                                    if (newPassword.equals(confirmPassword)) {
                                        user.setPassword(newPassword);
                                        system.updateUserPassword(user.getUsername(),user.getPassword());
                                        JOptionPane.showMessageDialog(passwordDialog2, "用户密码修改成功");
                                        passwordDialog2.dispose();
                                    } else {
                                        JOptionPane.showMessageDialog(passwordDialog2, "两次输入的新密码不一致，请重新输入");
                                    }
                                }
                            });
                            buttonPanel2.add(saveButton);

                            passwordDialog2.add(inputPanel2, BorderLayout.CENTER);
                            passwordDialog2.add(buttonPanel2, BorderLayout.SOUTH);
                            passwordDialog2.setVisible(true);
                        }
                    });
                    buttonPanel1.add(nextButton);

                    passwordDialog1.add(inputPanel1, BorderLayout.CENTER);
                    passwordDialog1.add(buttonPanel1, BorderLayout.SOUTH);
                    passwordDialog1.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "系统中不存在该用户名对应的用户，无法修改密码");
                }
            }
        });

        JButton setUserPermissionButton = new JButton("修改用户权限");
        setUserPermissionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出对话框让管理员输入要修改权限的用户ID
                String userId = JOptionPane.showInputDialog(adminFrame, "请输入要修改权限的用户ID");
                User user = system.getUserByUsername(userId);
                if (user!= null) {
                    // 弹出对话框让管理员输入管理员密码，验证管理员身份
                    String adminPassword = JOptionPane.showInputDialog(adminFrame, "请输入管理员密码", "验证管理员身份", JOptionPane.PLAIN_MESSAGE);
                    String currentUserPassword = system.getCurrentUser().getPassword();
                    if (currentUserPassword!=null&&currentUserPassword.equals(adminPassword)) {
                        // 创建对话框用于选择权限角色
                        JDialog roleDialog = new JDialog(adminFrame, "选择用户权限", true);
                        roleDialog.setLayout(new BorderLayout());
                        roleDialog.setSize(300, 150);

                        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                        int screenWidth = screenSize.width;
                        int screenHeight = screenSize.height;
                        int x = (screenWidth - roleDialog.getWidth()) / 2;
                        int y = (screenHeight - roleDialog.getHeight()) / 2;
                        roleDialog.setLocation(x, y);

                        JPanel rolePanel = new JPanel();
                        rolePanel.setLayout(new GridLayout(2, 1));

                        JLabel roleLabel = new JLabel("请选择权限角色:");
                        String[] roles = {"studentRole", "teacherRole", "adminRole"};
                        JComboBox<String> roleComboBox = new JComboBox<>(roles);
                        rolePanel.add(roleLabel);
                        rolePanel.add(roleComboBox);

                        JPanel buttonPanel = new JPanel();
                        JButton confirmButton = new JButton("确认");
                        confirmButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent ev) {
                                String selectedRole = (String) roleComboBox.getSelectedItem();
                                List<Role> roles = StudentScoreManagementSystem.initializeRoles();
                                Role role = null;
                                switch (selectedRole){
                                    case "studentRole":
                                        role = roles.get(0);
                                        break;
                                    case "teacherRole":
                                        role = roles.get(1);
                                        break;
                                    case "adminRole":
                                        role = roles.get(2);
                                        break;
                                }
                                system.updateUserRole(user.getUsername(), role);
                                JOptionPane.showMessageDialog(adminFrame, "权限修改成功", "操作成功", JOptionPane.INFORMATION_MESSAGE);
                                roleDialog.dispose();
                            }
                        });
                        buttonPanel.add(confirmButton);

                        roleDialog.add(rolePanel, BorderLayout.CENTER);
                        roleDialog.add(buttonPanel, BorderLayout.SOUTH);
                        roleDialog.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(adminFrame, "管理员密码错误，权限修改失败");
                    }
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "未找到对应ID的用户，权限修改失败");
                }
            }
        });

        studentPanel.add(addStudentButton);
        studentPanel.add(deleteStudentButton);
        studentPanel.add(updateStudentButton);
        studentPanel.add(getStudentButton);

        scorePanel.add(addScoreButton);
        scorePanel.add(updateScoreButton);
        scorePanel.add(getScoreButton);
        scorePanel.add(calculateCourseAvgScoreButton);
        scorePanel.add(calculateStudentAvgScoreButton);

        userPanel.add(addUserButton);
        userPanel.add(updateUserPasswordButton);
        userPanel.add(setUserPermissionButton);


        adminPanel.add(studentPanel);
        adminPanel.add(scorePanel);
        adminPanel.add(userPanel);

        adminFrame.add(adminPanel);
        adminFrame.setVisible(true);

    }
}
