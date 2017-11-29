package controllers.api;

import items.ItemSelect;
import items.ItemTaskTwo;
import models.Person;
import models.Task_two;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import responses.ItemSelect2Response;
import responses.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by educacion on 29/11/2017.
 */
public class ApiTask extends Controller {
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
            item.setIdPersona(task.getIdPersona().getIdPersona());
            item.setNombre(task.getIdPersona().getNombre());

            listaitem.add(item);
        }
        return ok(Json.toJson(new Response(true, "Exito", listaitem)));
    }


    //METODO DE GUARDADO
    public Result saveTask() {
        boolean success = false;
        String message = "";
        Response response = null;

        Map<String, String[]> params = request().body().asFormUrlEncoded();

        String idHidden = params.get("idHidden")[0];
        String txtTitulo = params.get("txtTitulo")[0];
        String txtDescripcion = params.get("txtDescripcion")[0];
        String selPersona = params.get("selPersona")[0];
        try {
            if (txtTitulo.isEmpty()) {
                message = Messages.get("api.record.empty");
                response = new Response(success, message);
                return ok(Json.toJson(response));
            } else{
                Task_two task;
                if (idHidden.isEmpty()) {
                    task = new Task_two();
                    message= Messages.get("api.record.save");
                }else {
                    int idObj= Integer.parseInt(idHidden);
                    task = Task_two.find.byId(idObj);
                    message= Messages.get("api.record.update");
                }
                task.setTitle(txtTitulo);
                task.setDescription(txtDescripcion);
                task.setIdPersona(Person.getById(Integer.parseInt(selPersona.trim())));
                task.save();
                success = true;
            }

        }catch(Exception e) {
            message = e.toString();
        }
        response = new Response(success,message);
        return ok(Json.toJson(response));
    }

    // LLENAR SELECT LIST
    public Result getTaskToSelect(){
        ItemSelect2Response response;
        List<ItemSelect> itemSelect2List = new ArrayList<>();
        List<Person> personalist = Person.listaperson();

        for (Person person: personalist)
        {
            ItemSelect item = new ItemSelect();
            item.setId(person.getIdPersona());
            item.setText(person.getNombre());
            itemSelect2List.add(item);
        }

        response = new ItemSelect2Response(itemSelect2List);

        return ok(Json.toJson(response));
    }

    //METODO PARA ELIMINAR
    public Result deleteTask(Integer id){
        boolean success=false;
        String message = Messages.get("");
        Task_two task = null;
        Response response = null;
        try{
            task = Task_two.find.byId(id);
            if (task != null){
                task.setDeletedAt(new Date());
                task.update();
                success = true;
                message = Messages.get("api.record.delete");
            }
            response = new Response(success,message);
        }catch (Exception e){
            message = e.toString();
        }
        return ok(Json.toJson(response));
    }

    //LLENAR EL FORMULARIO PARA MODIFICAR
    public Result getTaskTwo(String id){
        int idCtrl = Integer.parseInt(id.trim());
        boolean success = false;
        String message = Messages.get("api.record.error");
        Task_two task = null;
        ItemTaskTwo item=null;
        Response<ItemTaskTwo> response=null;
        try{
            item = new ItemTaskTwo();
            task = Task_two.find.byId(idCtrl);
            if(task != null){
                item.setIdTaskTwo(task.getIdTask());
                item.setTitleTask(task.getTitle());
                item.setDescriptionTask(task.getDescription());
                item.setIdPersona(task.getIdPersona().getIdPersona());
                item.setNombre(task.getIdPersona().getNombre());
                success = true;
                message= Messages.get("api.record.success");
            }

            response = new Response(success,message,item);
        }catch (Exception e){
            message = "api.record.error" + e.toString();
        }
        return ok(Json.toJson(response));
    }


    //OBTENER LAS TAREAS POR PERSONA
    public Result getTaskByPerson(String id){
        Integer idCtrl = Integer.parseInt(id.trim());
        boolean success =  false;
        String message = "";

        List<Task_two> taskList = Task_two.getTaskByPerson(idCtrl);
        List<ItemTaskTwo> itemTaskList = new ArrayList<>();
        Response<List<ItemTaskTwo>> response = null;

        if(!taskList.isEmpty()){
            for(Task_two task: taskList){
                ItemTaskTwo item = new ItemTaskTwo();
                item.setIdTaskTwo(task.getIdTask());
                item.setTitleTask(task.getTitle());
                item.setDescriptionTask(task.getDescription());
                itemTaskList.add(item);
            }
        }
        success = true;
        message = Messages.get("api.record.success");
        response = new Response(success, message, itemTaskList);
        return ok(Json.toJson(response));
    }
}
