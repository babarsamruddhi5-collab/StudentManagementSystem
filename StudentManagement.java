
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    int rollNo;
    String name;
    int age;
    String course;
    String email;

    Student(int rollNo, String name, int age,
            String course, String email) {
        this.rollNo = rollNo;
        this.name = name;
        this.age = age;
        this.course = course;
        this.email = email;
    }

    void display() {
        System.out.println("Roll No: " + rollNo);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Course: " + course);
        System.out.println("Email: " + email);
        System.out.println("------------------------");
    }
}

public class StudentManagement {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Student> students = new ArrayList<>();
    static final String FILE_NAME = "students.dat";

    public static void main(String[] args) {
        loadStudents();

        while (true) {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Total Students");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    updateStudent();
                    break;

                case 5:
                    deleteStudent();
                    break;

                case 6:
                    System.out.println(
                        "Total Students: " + students.size()
                    );
                    break;

                case 7:
                    saveStudents();
                    System.out.println("Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    static int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            sc.nextLine();
        }

        int value = sc.nextInt();
        sc.nextLine();
        return value;
    }

    static void addStudent() {
        System.out.print("Enter Roll No: ");
        int rollNo = readInt();

        if (findStudent(rollNo) != null) {
            System.out.println("Roll number already exists!");
            return;
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Age: ");
        int age = readInt();

        System.out.print("Enter Course: ");
        String course = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        Student s = new Student(
            rollNo, name, age, course, email
        );

        students.add(s);
        saveStudents();

        System.out.println("Student added successfully!");
    }

    static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found!");
            return;
        }

        System.out.println("\n===== ALL STUDENTS =====");

        for (Student s : students) {
            s.display();
        }

        System.out.println("Total Students: " + students.size());
    }

    static void searchStudent() {
        System.out.print("Enter Roll No to search: ");
        int rollNo = readInt();

        Student s = findStudent(rollNo);

        if (s != null) {
            System.out.println("Student found:");
            s.display();
        } else {
            System.out.println("Student not found!");
        }
    }

    static void updateStudent() {
        System.out.print("Enter Roll No to update: ");
        int rollNo = readInt();

        Student s = findStudent(rollNo);

        if (s == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.print("Enter New Name: ");
        s.name = sc.nextLine();

        System.out.print("Enter New Age: ");
        s.age = readInt();

        System.out.print("Enter New Course: ");
        s.course = sc.nextLine();

        System.out.print("Enter New Email: ");
        s.email = sc.nextLine();

        saveStudents();
        System.out.println("Student updated successfully!");
    }

    static void deleteStudent() {
        System.out.print("Enter Roll No to delete: ");
        int rollNo = readInt();

        Student s = findStudent(rollNo);

        if (s != null) {
            students.remove(s);
            saveStudents();
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found!");
        }
    }

    static Student findStudent(int rollNo) {
        for (Student s : students) {
            if (s.rollNo == rollNo) {
                return s;
            }
        }

        return null;
    }

    static void saveStudents() {
        try (ObjectOutputStream out =
                 new ObjectOutputStream(
                     new FileOutputStream(FILE_NAME))) {

            out.writeObject(students);

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    static void loadStudents() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream in =
                 new ObjectInputStream(
                     new FileInputStream(FILE_NAME))) {

            students = (ArrayList<Student>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}