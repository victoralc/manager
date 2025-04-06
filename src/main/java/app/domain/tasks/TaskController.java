package app.domain.tasks;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;
import java.util.Optional;

@Path("/tasks")
public class TaskController {

    @Inject
    Template tasks;

    @Inject
    TaskRepository taskRepository;

    @GET
    @Blocking
    public TemplateInstance renderTasksTemplate() {
        return tasks.data("tasks", taskRepository.listAll());
    }

    @PUT
    @Path("/{taskId}/status")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("taskId") Long taskId, Map<String, Boolean> body) {
        Optional<Task> optTask = taskRepository.findByIdOptional(taskId);
        if (optTask.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "invalid task id"))
                    .build();
        }

        boolean taskCompleted = body.get("completed");

        Task task = optTask.get();
        task.setCompleted(taskCompleted);
        taskRepository.persist(task);

        return Response.ok(Map.of("success", "Task updated")).build();
    }

    @POST
    @Blocking
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public TemplateInstance create(@FormParam("taskName") String taskName) {
        if (taskName == null || taskName.trim().isEmpty()) {
            return tasks
                    .data("error", "Invalid task name. Must not be empty")
                    .data("tasks", taskRepository.listAll());
        }
        Task task = new Task(taskName);
        taskRepository.persist(task);
        return tasks.data("tasks", taskRepository.listAll());
    }

    @POST
    @Blocking
    @Transactional
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public TemplateInstance delete(@PathParam("id") Long id) {
        taskRepository.deleteById(id);
        return tasks.data("tasks", taskRepository.listAll());
    }
}
