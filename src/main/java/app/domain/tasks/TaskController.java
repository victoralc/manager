package app.domain.tasks;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/tasks")
public class TaskController {

    @Inject
    TaskRepository taskRepository;

    @GET
    public String getTasks() {
        List<Task> tasks = taskRepository.findAll().list();
        return "tasks";
    }

    @PUT
    @Path("/{taskId}")
    public String update(@PathParam("taskId") Long taskId, String taskName) {
        Optional<Task> optTask = taskRepository.findByIdOptional(taskId);
        if (optTask.isEmpty()) {
            return "tasks";
        }
        Task task = optTask.get();
        task.setName(taskName);
        taskRepository.persist(task);
        return "tasks";
    }

    @PUT
    @Path("/{taskId}/status")
    public String update(@PathParam("taskId") Long taskId, Map<String, Boolean> body) {
        Optional<Task> optTask = taskRepository.findByIdOptional(taskId);
        if (optTask.isEmpty()) {
            return "tasks";
        }
        Task task = optTask.get();

        boolean shouldUpdate = body.get("completed");
        task.setCompleted(shouldUpdate);
        taskRepository.persist(task);
        return "tasks";
    }

    @DELETE
    @Path("/{taskId}")
    public String deleteTask(@PathParam("taskId") Long taskId) {
        taskRepository.deleteById(taskId);
        return "redirect:/tasks";
    }

    @POST
    public String create(@QueryParam("taskName") String taskName) {
        if (taskName == null || taskName.trim().isEmpty()) {
            return "redirect:/tasks";
        }
        Task task = new Task(taskName);
        taskRepository.persist(task);
        return "redirect:/tasks";
    }
}
