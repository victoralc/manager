package app.domain.tasks;

import jakarta.validation.constraints.NotEmpty;

public record CreateTask(@NotEmpty(message = "Invalid task name") String taskName) {
}
