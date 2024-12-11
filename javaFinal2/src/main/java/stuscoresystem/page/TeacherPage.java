package stuscoresystem.page;

import stuscoresystem.sturcture.Score;
import stuscoresystem.sturcture.Student;
import stuscoresystem.StudentScoreManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TeacherPage {
    public void openTeacherPage(StudentScoreManagementSystem system){
        JFrame teacherFrame = new JFrame("教师端页面");
        teacherFrame.setBounds(700, 300, 700, 500);

        // 获取屏幕尺寸并计算居中位置
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int x = (screenWidth - teacherFrame.getWidth()) / 2;
        int y = (screenHeight - teacherFrame.getHeight()) / 2;
        teacherFrame.setLocation(x, y);

        JPanel teacherPanel = new JPanel();
        teacherPanel.setLayout(new FlowLayout());

        JButton getScoreButton = new JButton("查询学生成绩");
        getScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出对话框让教师输入学号
                String studentId = JOptionPane.showInputDialog(teacherFrame, "请输入学生学号");
                // 定义课程选择器的选项
                String[] courses = {"Math", "English", "Physics", "Chemistry", "Biology", "History", "Geography", "Computer Science", "Art", "Music"};
                // 弹出课程选择器对话框
                String course = (String) JOptionPane.showInputDialog(
                        teacherFrame,
                        "请选择课程",
                        "课程选择",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        courses,
                        courses[0]);

                if (course == null) {
                    JOptionPane.showMessageDialog(teacherFrame, "必须选择课程");
                    return;
                }
                Score scoreObj = system.getScoreByStudentAndCourse(studentId, course);
                if (scoreObj != null) {
                    StringBuilder scoreInfo = new StringBuilder("成绩如下：\n");
                    scoreInfo.append(scoreObj.getCourse()).append(": ").append(scoreObj.getScore()).append("\n");
                    JOptionPane.showMessageDialog(teacherFrame, scoreInfo.toString());
                } else {
                    JOptionPane.showMessageDialog(teacherFrame, "未找到该课程成绩");
                }
            }
        });

        JButton updateStudentButton = new JButton("修改学生信息");
        updateStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出对话框让教师输入学号
                String studentId = JOptionPane.showInputDialog(teacherFrame, "请输入学生学号");
                Student student = system.getStudentById(studentId);
                if (student != null) {
                    // 弹出对话框让学生选择修改的属性（姓名、性别或班级信息）
                    String[] options = {"姓名", "性别", "班级信息"};
                    String selectedOption = (String) JOptionPane.showInputDialog(teacherFrame, "请选择要修改的属性", "修改属性选择",
                            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (selectedOption != null) {
                        String newValue;
                        if ("姓名".equals(selectedOption)) {
                            newValue = JOptionPane.showInputDialog(teacherFrame, "请输入新的姓名", student.getName());
                        } else if ("性别".equals(selectedOption)) {
                            newValue = JOptionPane.showInputDialog(teacherFrame, "请输入新的性别", student.getGender());
                        } else {
                            newValue = JOptionPane.showInputDialog(teacherFrame, "请输入新的班级信息", student.getClassInfo());
                        }
                        if (newValue != null) {
                            // 根据选择修改相应属性构建新的 Student 对象
                            Student updatedStudent = new Student(student.getId(),
                                    "姓名".equals(selectedOption) ? newValue : student.getName(),
                                    "性别".equals(selectedOption) ? newValue : student.getGender(),
                                    "班级信息".equals(selectedOption) ? newValue : student.getClassInfo());
                            system.updateStudent(updatedStudent);
                            JOptionPane.showMessageDialog(teacherFrame, "学生信息修改成功");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(teacherFrame, "未找到相关的学生信息");
                }
            }
        });

        JButton getStudentButton = new JButton("查看学生信息");
        getStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出对话框让教师输入学号
                String studentId = JOptionPane.showInputDialog(teacherFrame, "请输入学生学号");
                Student student = system.getStudentById(studentId);
                if (student != null) {
                    StringBuilder info = new StringBuilder("学生的个人信息如下：\n");
                    info.append("姓名: ").append(student.getName()).append("\n");
                    info.append("学号: ").append(student.getId()).append("\n");
                    info.append("性别: ").append(student.getGender()).append("\n");
                    info.append("班级: ").append(student.getClassInfo()).append("\n");
                    JOptionPane.showMessageDialog(teacherFrame, info.toString());
                } else {
                    JOptionPane.showMessageDialog(teacherFrame, "未找到相关学生的信息");
                }
            }
        });

        JButton addScoreButton = new JButton("录入成绩");
        addScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出对话框让教师输入学号
                String studentId = JOptionPane.showInputDialog(teacherFrame, "请输入学生学号");
                final Student student;
                Student retrievedStudent = system.getStudentById(studentId);
                if (retrievedStudent == null) {
                    student = new Student(studentId, null, null, null);
                    system.addStudent(student);
                } else {
                    student = retrievedStudent;
                }
                // 创建一个新的对话框用于录入成绩
                JDialog scoreDialog = new JDialog(teacherFrame, "录入成绩", true);
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
                        List<Score> scoresToAdd = new ArrayList<>();
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
                            teacherFrame,
                            "请选择课程",
                            "课程选择",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            courses,
                            courses[0]);
                }
                double avgScore = system.calculateAverageScoreForCourse(course);
                if (avgScore == 0) {
                    JOptionPane.showMessageDialog(teacherFrame, "该课程暂无成绩记录");
                } else {
                    JOptionPane.showMessageDialog(teacherFrame, "该课程的平均成绩为：" + avgScore);
                }
            }
        });

        JButton calculateStudentAvgScoreButton = new JButton("计算学生平均成绩");
        calculateStudentAvgScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出对话框让教师输入学号
                String studentId = JOptionPane.showInputDialog(teacherFrame, "请输入学生学号");
                Student student = system.getStudentById(studentId);
                if (student == null) {
                    JOptionPane.showMessageDialog(teacherFrame, "系统中未找到该学生，请重新输入");
                } else {
                    double avgScore = system.calculateAverageScoreForStudent(studentId);
                    if (avgScore == 0) {
                        JOptionPane.showMessageDialog(teacherFrame, "该学生暂无成绩记录");
                    } else {
                        JOptionPane.showMessageDialog(teacherFrame, "该学生的平均成绩为：" + avgScore);
                    }
                }
            }
        });

        teacherPanel.add(getScoreButton);
        teacherPanel.add(updateStudentButton);
        teacherPanel.add(getStudentButton);
        teacherPanel.add(addScoreButton);
        teacherPanel.add(calculateCourseAvgScoreButton);
        teacherPanel.add(calculateStudentAvgScoreButton);

        teacherFrame.add(teacherPanel);
        teacherFrame.setVisible(true);
    }
}
