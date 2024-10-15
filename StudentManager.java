import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

class Student {
    int id;
    String name;
    double marks;

    public Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public String getRank() {
        if (marks < 5.0) return "Fail";
        if (marks < 6.5) return "Medium";
        if (marks < 7.5) return "Good";
        if (marks < 9.0) return "Very Good";
        return "Excellent";
    }
}

class Stack {
    private ArrayList<Integer> stack;

    public Stack() {
        stack = new ArrayList<>();
    }

    public void push(int id) {
        stack.add(id);
    }

    public int pop() {
        if (!isEmpty()) {
            return stack.remove(stack.size() - 1);
        }
        throw new RuntimeException("Stack is empty");
    }

    public int peek() {
        if (!isEmpty()) {
            return stack.get(stack.size() - 1);
        }
        throw new RuntimeException("Stack is empty");
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}

public class StudentManager {
    private ArrayList<Student> students;
    private Stack actionStack;

    public StudentManager() {
        students = new ArrayList<>();
        actionStack = new Stack();
    }

    public void addStudent(int id, String name, double marks) {
        if (isValidName(name)) {
            students.add(new Student(id, name, marks));
            actionStack.push(id);
            System.out.println("Student added successfully.");
        } else {
            System.out.println("Invalid student name. Please try again.");
        }
    }

    public void displayStudents() {
        for (Student s : students) {
            System.out.println("ID: " + s.id + ", Name: " + s.name + ", Marks: " + s.marks + ", Rank: " + s.getRank());
        }
    }

    public void bubbleSortStudents() {
        int n = students.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (students.get(j).marks > students.get(j + 1).marks) {
                    // Swap students
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }
    }

    public void editStudent(int id, String name, double marks) {
        for (Student s : students) {
            if (s.id == id) {
                if (isValidName(name)) {
                    s.name = name;
                    s.marks = marks;
                    System.out.println("Student information updated.");
                } else {
                    System.out.println("Invalid student name. Cannot update.");
                }
                return;
            }
        }
        System.out.println("Student with ID " + id + " not found.");
    }

    public void deleteStudent(int id) {
        boolean removed = students.removeIf(s -> s.id == id);
        if (removed) {
            actionStack.push(id); // Push deleted ID onto stack
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    private boolean isValidName(String name) {
        return name.length() >= 2 && name.length() <= 50 && Pattern.matches("^[a-zA-Z\\s]+$", name);
    }

    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Add student");
            System.out.println("2. Edit student");
            System.out.println("3. Delete student");
            System.out.println("4. Display students");
            System.out.println("5. Sort students by marks");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    System.out.print("Enter Student Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Marks: ");
                    double marks = scanner.nextDouble();

                    manager.addStudent(id, name, marks);
                    break;

                case 2:
                    System.out.print("Enter Student ID to edit: ");
                    int editId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    System.out.print("Enter New Name: ");
                    String newName = scanner.nextLine();

                    System.out.print("Enter New Marks: ");
                    double newMarks = scanner.nextDouble();

                    manager.editStudent(editId, newName, newMarks);
                    break;

                case 3:
                    System.out.print("Enter Student ID to delete: ");
                    int deleteId = scanner.nextInt();
                    manager.deleteStudent(deleteId);
                    break;

                case 4:
                    System.out.println("Student List:");
                    manager.displayStudents();
                    break;

                case 5:
                    manager.bubbleSortStudents();
                    System.out.println("Student list sorted by marks.");
                    break;

                case 6:
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
