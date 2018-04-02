package model;

public enum Role {
    STUDENT("student"),
    TEACHER("docent"),
    ERROR("undefined"); // Not really a Role but this is how it's working in the frontend

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
