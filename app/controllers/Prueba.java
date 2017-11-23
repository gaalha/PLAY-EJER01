package controllers;

import items.ItemPeople;
import models.Persona;
import play.libs.Json;
import play.libs.Jsonp;
import play.mvc.Controller;
import play.mvc.Result;
import play.i18n.Messages;
import responses.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import play.data.DynamicForm;
import play.data.Form;

/**
 * Created by educacion on 16/11/2017.
 */
public class Prueba extends Controller{
    public Result prueba() {
        List<Persona> listap = Persona.listapersona();
        List<ItemPeople> listaItem = new ArrayList<>();
        Response<List<ItemPeople>> response = null;

        for(Persona per:listap){
            ItemPeople item = new ItemPeople();
            item.setIdPersona(per.getIdPersona());
            item.setNombre(per.getNombre());
            item.setEdad(per.getEdad());

            listaItem.add(item);
        }

        return ok(Json.toJson(new Response(true, "Exito", listaItem)));
    }

    /*public Result getEditar(Integer id){
        Persona persona = Persona.find.byId(id);
        Integer edad = null;

        Map<String, String[]> params = request().body().asFormUrlEncoded();
        String nombre = params.get("nombre")[0];
        if(params.get("edad")[0] != null){
            edad = Integer.parseInt(params.get("edad")[0]);
        }

        persona.setNombre(nombre);
        persona.setEdad(edad);
        persona.save();

        return redirect("/peoples");
    }


    public Result getEliminar(Integer id){
        Persona persona = Persona.find.byId(id);
        if(persona!=null)
            persona.delete();

        return redirect("/peoples");
    }*/

    public Result getEliminar(Integer id){
        boolean success=false;
        String message = Messages.get("");
        Persona persona = null;
        Response response = null;
        try{
            persona = Persona.find.byId(id);
            if (persona!=null){
                //persona.setDeletedAt(new Date());
                //city.setStatus(0);
                //city.update();
                persona.delete();
                success = true;
                message=Messages.get("api.record.delete");
            }
            response = new Response(success,message);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ok(Json.toJson(response));
    }


    /*public Result getInsertar(){
        Persona persona = new Persona();
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        String nombre = params.get("txtNombre")[0];
        Integer edad = Integer.parseInt(params.get("txtEdad")[0]);

        persona.setNombre(nombre);
        persona.setEdad(edad);
        persona.save();

        return redirect("/peoples");
    } */
    //@routes.controllers.Application.getEditar(persona.getIdPersona())

    public Result save() {
        //DynamicForm form = Form.form().bindFromRequest();
        //processPostRequest(request());
        boolean success = false;
        String message = "";
        Response response = null;

        Map<String, String[]> params = request().body().asFormUrlEncoded();

        String idHidden = params.get("idHidden")[0];
        String txtNombre = params.get("txtNombre")[0];
        Integer txtEdad = Integer.parseInt(params.get("txtEdad")[0]);

        //String idHidden = form.get("idHidden");
        //String txtNombre = form.get("txtNombre");
        //String txtEdad = form.get("txtEdad");

        try {

            if (txtNombre.isEmpty()) {
                message = Messages.get("   ");
                response = new Response(success, message);
                return ok(Json.toJson(response));
            } else{

                Persona persona;
                if (idHidden.isEmpty()) {
                    persona = new Persona();
                    persona.save();
                    message= Messages.get("api.record.save");
                }else {
                    int idObj= Integer.parseInt(idHidden);
                    persona = Persona.find.byId(idObj);
                    message= Messages.get("api.record.update");
                }

                persona.setNombre(txtNombre);
                persona.setEdad(txtEdad);
                persona.save();
                success = true;
            }
            response = new Response(success,message);

        }catch(Exception e) {
            e.printStackTrace();
        }
        return ok(Json.toJson(response));
    }


    public Result getPeople(String id){
        int idCtrl=Integer.parseInt(id.trim());
        boolean success = false;
        String message = Messages.get("api.record.error");
        Persona persona=null;
        ItemPeople item=null;
        Response<ItemPeople> response=null;
        try{
            item = new ItemPeople();
            persona = Persona.find.byId(idCtrl);

            if(persona != null){
                item.setIdPersona(persona.getIdPersona());
                item.setNombre(persona.getNombre());
                item.setEdad(persona.getEdad());

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
