package controllers;

import items.ItemTaskTwo;
import models.Task_two;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import responses.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by educacion on 24/11/2017.
 */
public class TaskTwoController extends Controller{
    public Result GetTaskTwoView() {
        List<Task_two> lista = Task_two.list_task();
        List<ItemTaskTwo> listaitem = new ArrayList<>();
        Response<List<ItemTaskTwo>> response = null;

        for(Task_two task:lista){
            ItemTaskTwo item = new ItemTaskTwo();
            item.setIdTaskTwo(task.getIdTask());
            item.setTitleTask(task.getTitle());
            item.setDescriptionTask(task.getDescription());
            item.setDeleteAt(task.getDeletedAt());
            item.setIdPer(task.getIdPersona().getIdPersona());

            listaitem.add(item);
        }
        return ok(Json.toJson(new Response(true, "Exito", listaitem)));
    }
}
