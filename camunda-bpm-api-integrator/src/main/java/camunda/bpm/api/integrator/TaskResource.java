package camunda.bpm.api.integrator;


import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.GetMapping;

public interface TaskResource {

    @GetMapping("/task")
    JsonObject queryTasks();

    @GetMapping("/task/complete")
    JsonObject completeTask();

}
