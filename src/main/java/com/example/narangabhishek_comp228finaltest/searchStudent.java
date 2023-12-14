package com.example.narangabhishek_comp228finaltest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class searchStudent extends JFrame {


    public searchStudent() {
        setTitle("Search Student");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        JLabel lblProvince = new JLabel("Enter Province (Only 2 Characters)");
        JTextField txtProvince = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD",
                            "COMP228_F23_piy_23", "password");

                    String searchQuery = "select studentID, firstName, lastName, address, city, province, postalCode from students where province = '?'";
                    searchQuery = searchQuery.replace("?", txtProvince.getText());

                    PreparedStatement searchStatement = conn.prepareStatement(searchQuery);
                    String province = txtProvince.getText();

                    ResultSet resultSet = searchStatement.executeQuery();

                    StringBuilder reportInfo = new StringBuilder();
                    reportInfo.append("Student ID\t First Name\t Last Name\t Address\t\t City\t Province\t Postal Code \n");
                   while (resultSet.next()) {
                        reportInfo.append(resultSet.getString("studentID")).append("\t")
                                .append(resultSet.getString("firstName")).append("\t")
                                .append(resultSet.getString("lastName")).append("\t")
                                .append(resultSet.getString("address")).append("\t ")
                                .append(resultSet.getString("city")).append("\t")
                                .append(resultSet.getString("province")).append("\t")
                                .append(resultSet.getString("postalCode")).append("\n");
                    }

                    JOptionPane.showMessageDialog(null, new JTextArea(reportInfo.toString()));
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(panel);
        panel.add(lblProvince);
        panel.add(txtProvince);
        panel.add(searchButton);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new searchStudent());
    }
}