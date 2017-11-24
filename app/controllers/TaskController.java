package controllers;

import items.ItemTask;
import models.Persona;
import models.Task;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import responses.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import items.ItemSelect;
import responses.ItemSelect2Response;


/**
 * Created by educacion on 22/11/2017.
 */
public class TaskController extends Controller{

    public Result GetTaskView() {
        List<Task> lista = Task.listtask();
        List<ItemTask> listaitem = new ArrayList<>();
        Response<List<ItemTask>> response = null;

        for(Task task:lista){
            ItemTask item = new ItemTask();
            item.setIdtask(task.getIdTask());
            item.setTitle(task.getTitle());
            item.setDescription(task.getDescription());

            item.setIdPersona(task.getIdPersona().getIdPersona());
            item.setNombre(task.getIdPersona().getNombre());

            listaitem.add(item);
        }
        return ok(Json.toJson(new Response(true, "Exito", listaitem)));
    }

    // LLENAR SELECT LIST
    public Result getTaskToSelect(){
        ItemSelect2Response response;
        List<ItemSelect> itemSelect2List = new ArrayList<>();
        List<Persona> personalist = Persona.listapersona();

        for (Persona persona: personalist)
        {
            ItemSelect item = new ItemSelect();
            item.setId(persona.getIdPersona());
            item.setText(persona.getNombre());
            itemSelect2List.add(item);
        }

        response = new ItemSelect2Response(itemSelect2List);

        return ok(Json.toJson(response));
    }

    //METODO PARA ELIMINAR
    public Result getDeleteTask(Integer id){
        boolean success=false;
        String message = Messages.get("");
        Task task = null;
        Response response = null;
        try{
            task = Task.find.byId(id);
            if (task != null){
                task.delete();
                success = true;
                message = Messages.get("api.record.delete");
            }
            response = new Response(success,message);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ok(Json.toJson(response));
    }


    //METODO DE GUARDADO
    public Result getSaveTask() {
        //DynamicForm form = Form.form().bindFromRequest();
        //processPostRequest(request());
        boolean success = false;
        String message = "";
        Response response = null;

        Map<String, String[]> params = request().body().asFormUrlEncoded();

        String idHidden = params.get("idHidden")[0];
        String txtTitulo = params.get("txtTitulo")[0];
        String txtDescripcion = params.get("txtDescripcion")[0];
        Integer selPersona = Integer.parseInt(params.get("selPersona")[0]);

        try {

            if (txtTitulo.isEmpty()) {
                message = Messages.get("ERROR: ¡Campos vacios!");
                response = new Response(success, message);
                return ok(Json.toJson(response));
            } else{

                Task task;
                if (idHidden.isEmpty()) {
                    task = new Task();
                    message= Messages.get("api.record.save");
                }else {
                    int idObj= Integer.parseInt(idHidden);
                    task = Task.find.byId(idObj);
                    message= Messages.get("api.record.update");
                }
                task.setTitle(txtTitulo);
                task.setDescription(txtDescripcion);
                task.setIdPersona(Persona.find.byId(selPersona));
                task.save();
                success = true;
            }
            response = new Response(success,message);

        }catch(Exception e) {
            e.printStackTrace();
        }
        return ok(Json.toJson(response));
    }

    //LLENAR EL FORMULARIO PARA MODIFICAR
    public Result getTask(String id){
        int idCtrl=Integer.parseInt(id.trim());
        boolean success = false;
        String message = Messages.get("api.record.error");
        Task task = null;
        ItemTask item=null;
        Response<ItemTask> response=null;
        try{
            item = new ItemTask();
            task = Task.find.byId(idCtrl);

            if(task != null){
                item.setIdtask(task.getIdTask());
                item.setTitle(task.getTitle());
                item.setDescription(task.getDescription());
                item.setIdPersona(task.getIdPersona().getIdPersona());
                item.setNombre(task.getIdPersona().getNombre());

                success = true;
                message= Messages.get("api.record.success");
            }

            response = new Response(success,message,item);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ok(Json.toJson(response));
    }

}
