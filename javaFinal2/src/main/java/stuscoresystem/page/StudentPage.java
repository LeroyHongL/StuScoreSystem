package stuscoresystem.page;

import stuscoresystem.sturcture.Score;
import stuscoresystem.sturcture.Student;
import stuscoresystem.StudentScoreManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentPage {
    public void openStudentPage(StudentScoreManagementSystem system){
        JFrame studentFrame = new JFrame("学生端页面");
        studentFrame.setBounds(700, 300, 700, 500);


        // 计算窗口居中位置
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int x = (screenWidth - studentFrame.getWidth()) / 2;
        int y = (screenHeight - studentFrame.getHeight()) / 2;
        studentFrame.setLocation(x, y);

        JPanel studentPanel = new JPanel();
        studentPanel.setLayout(new FlowLayout());

        JLabel studentLabel = new JLabel("欢迎来到学生端，你具有查询成绩、修改个人信息等权限");
        JButton getScoreButton = new JButton("查询成绩");
        getScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出对话框让学生输入学号
                String studentId = JOptionPane.showInputDialog(studentFrame, "请输入学号");
                // 定义课程选择器的选项
                String[] courses = {"Math", "English", "Physics", "Chemistry", "Biology", "History", "Geography", "Computer Science", "Art", "Music"};
                // 弹出课程选择器对话框
                String course = (String) JOptionPane.showInputDialog(
                        studentFrame,
                        "请选择课程",
                        "课程选择",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        courses,
                        courses[0]);
                if (course == null) {
                    JOptionPane.showMessageDialog(studentFrame, "必须选择课程");
                    return;
                }
                Score scoreObj = system.getScoreByStudentAndCourse(studentId, course);
                if (scoreObj != null) {
                    StringBuilder scoreInfo = new StringBuilder("你的成绩如下：\n");
                    scoreInfo.append(scoreObj.getCourse()).append(": ").append(scoreObj.getScore()).append("\n");
                    JOptionPane.showMessageDialog(studentFrame, scoreInfo.toString());
                } else {
                    JOptionPane.showMessageDialog(studentFrame, "未找到该课程成绩");
                }
            }
        });
        JButton updateInfoButton = new JButton("修改个人信息");
        updateInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出对话框让学生输入学号
                String studentId = JOptionPane.showInputDialog(studentFrame, "请输入学号");
                Student student = system.getStudentById(studentId);
                if (student != null) {
                    // 弹出对话框让学生选择修改的属性（姓名、性别或班级信息）
                    String[] options = {"姓名", "性别", "班级信息"};
                    String selectedOption = (String) JOptionPane.showInputDialog(studentFrame, "请选择要修改的属性", "修改属性选择",
                            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (selectedOption != null) {
                        String newValue;
                        if ("姓名".equals(selectedOption)) {
                            newValue = JOptionPane.showInputDialog(studentFrame, "请输入新的姓名", student.getName());
                        } else if ("性别".equals(selectedOption)) {
                            newValue = JOptionPane.showInputDialog(studentFrame, "请输入新的性别", student.getGender());
                        } else {
                            newValue = JOptionPane.showInputDialog(studentFrame, "请输入新的班级信息", student.getClassInfo());
                        }
                        if (newValue != null) {
                            // 根据选择修改相应属性构建新的 Student 对象
                            Student updatedStudent = new Student(student.getId(),
                                    "姓名".equals(selectedOption) ? newValue : student.getName(),
                                    "性别".equals(selectedOption) ? newValue : student.getGender(),
                                    "班级信息".equals(selectedOption) ? newValue : student.getClassInfo());
                            system.updateStudent(updatedStudent);
                            JOptionPane.showMessageDialog(studentFrame, "个人信息修改成功");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(studentFrame, "未找到你的信息");
                }
            }
        });
        JButton viewInfoButton = new JButton("查看个人信息");
        viewInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出对话框让学生输入学号
                String studentId = JOptionPane.showInputDialog(studentFrame, "请输入学号");
                Student student = system.getStudentById(studentId);
                if (student != null) {
                    StringBuilder info = new StringBuilder("你的个人信息如下：\n");
                    info.append("姓名: ").append(student.getName()).append("\n");
                    info.append("学号: ").append(student.getId()).append("\n");
                    info.append("性别: ").append(student.getGender()).append("\n");
                    info.append("班级: ").append(student.getClassInfo()).append("\n");
                    JOptionPane.showMessageDialog(studentFrame, info.toString());
                } else {
                    JOptionPane.showMessageDialog(studentFrame, "未找到你的信息");
                }
            }
        });

        studentPanel.add(getScoreButton);
        studentPanel.add(updateInfoButton);
        studentPanel.add(viewInfoButton);

        studentFrame.add(studentPanel);
        studentFrame.setVisible(true);
    }
}
