package app.domain.employees;

public record NewEmployeeData(
        String firstName,
        String lastName,
        String department,
        String phoneNumber,
        String email) {
    public static NewEmployeeData newEmployeeData() {
        return new NewEmployeeData("", "", "", "", "");
    }
}
