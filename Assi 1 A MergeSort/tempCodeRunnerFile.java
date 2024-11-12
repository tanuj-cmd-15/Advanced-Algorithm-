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