package it.develhope.sql;

import java.sql.*;
import java.util.ArrayList;

public class Student {
    private String name;
    private String surname;

    public Student(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    public static void main(String[] args) throws SQLException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/newdb", "root", "524013659?Ln");
            statement = connection.createStatement();

            //VISTA ITALIA
            String viewIT = "CREATE VIEW italian_students AS SELECT first_name, last_name FROM students WHERE country ='Italian'";
            statement.execute(viewIT);

            //SELECT ITALIA + ARRAY
            String selectIT = "SELECT first_name, last_name FROM italian_students";
            statement.execute(selectIT);
            ResultSet italianRS = statement.executeQuery(selectIT);

            ArrayList<Student> italianStudents = new ArrayList<>();
            while (italianRS.next()) {
                String name = italianRS.getString("first_name");
                String surname = italianRS.getString("last_name");
                italianStudents.add(new Student(name,surname));
            }

            //VISTA GERMANIA
            String viewDE = "CREATE VIEW german_students AS SELECT first_name, last_name FROM students WHERE country ='Germany'";
            statement.execute(viewDE);

            //SELECT GERMANIA + ARRAY
            String selectDE = "SELECT first_name, last_name FROM german_students";
            statement.execute(selectDE);
            ResultSet germanRS = statement.executeQuery(selectDE);

            ArrayList<Student> germanStudents = new ArrayList<>();
            while (germanRS.next()) {
                String name = germanRS.getString("first_name");
                String surname = germanRS.getString("last_name");
                germanStudents.add(new Student(name,surname));
            }

            System.out.println("Italian Students:");
            for (Student student : italianStudents) {
                System.out.println(student.getName() + " " + student.getSurname());
            }

            System.out.println("German Students:");
            for (Student student : germanStudents) {
                System.out.println(student.getName() + " " + student.getSurname());
            }

            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
