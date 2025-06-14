package app.domain.tasks;

import jakarta.validation.constraints.NotNull;

public record UpdateTaskStatus(@NotNull(message = "Invalid task status") Boolean completed) {
}
