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

    public static void mergeSort(Student[] arr, int start, int end, String sortField) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSort(arr, start, mid, sortField);
            mergeSort(arr, mid + 1, end, sortField);
            merge(arr, start, mid, end, sortField);
        }
    }

    private static void merge(Student[] arr, int start, int mid, int end, String sortField) {
        int nLeft = mid - start + 1;
        int nRight = end - mid;

        Student[] left = new Student[nLeft];
        Student[] right = new Student[nRight];

        for (int i = 0; i < nLeft; i++) {
            left[i] = arr[start + i];
        }
        for (int i = 0; i < nRight; i++) {
            right[i] = arr[mid + 1 + i];
        }

        int iLeft = 0, iRight = 0, k = start;

        while (iLeft < nLeft && iRight < nRight) {
            if (compareStudents(left[iLeft], right[iRight], sortField) <= 0) {
                arr[k] = left[iLeft];
                iLeft++;
            } else {
                arr[k] = right[iRight];
                iRight++;
            }
            k++;
        }

        while (iLeft < nLeft) {
            arr[k] = left[iLeft];
            iLeft++;
            k++;
        }

        while (iRight < nRight) {
            arr[k] = right[iRight];
            iRight++;
            k++;
        }
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
            mergeSort(students, 0, students.length - 1, sortField);

            System.out.println("\nSorted student data by " + sortField + ":");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }
}
