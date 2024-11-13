import java.util.*;

class Student {
    String name;
    int rollNumber;
    int entranceMarks;

    public Student(String name, int rollNumber, int entranceMarks) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.entranceMarks = entranceMarks;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Entrance Marks: " + entranceMarks;
    }
}

public class StudentSorter {

    public static void quickSort(Student[] arr, int start, int end, String sortField) {
        if (start < end) {
            int pi = partition(arr, start, end, sortField);
            quickSort(arr, start, pi - 1, sortField);
            quickSort(arr, pi + 1, end, sortField);
        }
    }

    private static int partition(Student[] arr, int start, int end, String sortField) {
    
        int randomIndex = start + (int) (Math.random() * (end - start + 1));
        swap(arr, end, randomIndex);

        Student pivot = arr[end];
        int i = start;
        int j = end;

        while (i < j) {
            while (i < end && compareStudents(arr[i], pivot, sortField) < 0) {
                i++;
            }
            while (j > start && compareStudents(arr[j], pivot, sortField) >= 0) {
                j--;
            }
            if (i >= j) {
                swap(arr, i, end);
                return i;
            }
            swap(arr, i, j);
        }
        return i;
    }

    private static void swap(Student[] arr, int i, int j) {
        Student temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static int compareStudents(Student a, Student b, String sortField) {
        switch (sortField.toLowerCase()) {
            case "name":
                return a.name.compareToIgnoreCase(b.name);
            case "roll number":
                return Integer.compare(a.rollNumber, b.rollNumber);
            case "entrance marks":
                return Integer.compare(a.entranceMarks, b.entranceMarks);
            default:
                throw new IllegalArgumentException("Invalid sorting field");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Student[] students = {
            new Student("Tushar", 5, 85),
            new Student("Harish", 3, 78),
            new Student("Om", 2, 90),
            new Student("Gayatri", 4, 82),
            new Student("Rutik", 1, 95),
            new Student("Vivek", 6, 88),
            new Student("Kalyani", 7, 91),
            new Student("Rutuja", 8, 79),
            new Student("Jay", 10, 84),
            new Student("Anita", 9, 80)
        };

        while (true) {
            System.out.println("\nChoose the field for sorting the student data:");
            System.out.println("1. Name\n2. Roll Number\n3. Entrance Marks\n4. Exit");
            int choice = scanner.nextInt();
            String sortField = "";

            switch (choice) {
                case 1:
                    sortField = "name";
                    break;
                case 2:
                    sortField = "roll number";
                    break;
                case 3:
                    sortField = "entrance marks";
                    break;
                case 4:
                    System.out.println("Exiting the program.");
                    return;
                default:
                    System.out.println("Invalid choice. Please choose again.");
                    continue;
            }

            // Sort and display the result
            quickSort(students, 0, students.length - 1, sortField);

            System.out.println("\nSorted student data by " + sortField + ":");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }
}
