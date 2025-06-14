package app.domain.tasks;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;
import java.util.Optional;

@Path("/tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

    @Inject
    TaskRepository taskRepository;

    @GET
    public Response getAllTasks() {
        return Response.ok(taskRepository.listAll()).build();
    }

    @PUT
    @Path("/{taskId}/status")
    @Transactional
    public Response updateStatus(@PathParam("taskId") Long taskId, @Valid UpdateTaskStatus status) {
        Optional<Task> optTask = taskRepository.findByIdOptional(taskId);
        if (optTask.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "invalid task id"))
                    .build();
        }

        Task task = optTask.get();
        task.setCompleted(status.completed());
        taskRepository.persist(task);

        return Response.ok().build();
    }

    @POST
    @Transactional
    public Response create(@Valid CreateTask request) {
        Task task = new Task(request.taskName().trim());
        taskRepository.persist(task);
        return Response.ok().build();
    }

    @POST
    @Transactional
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id) {
        taskRepository.deleteById(id);
        return Response.ok().build();
    }
}
