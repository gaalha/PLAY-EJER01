package controllers.api;

import items.ItemPerson;
import models.Person;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import responses.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by educacion on 29/11/2017.
 */
public class ApiPerson extends Controller {

    public Result GetPersonView() {
        List<Person> lista = Person.listaperson();
        List<ItemPerson> listaitem = new ArrayList<>();
        Response<List<ItemPerson>> response = null;

        for(Person person:lista){
            ItemPerson item = new ItemPerson();
            item.setIdPersona(person.getIdPersona());
            item.setNombre(person.getNombre());
            item.setEdad(person.getEdad());
            item.setDeletedAt(person.getDeletedAt());

            listaitem.add(item);
        }
        return ok(Json.toJson(new Response(true, "Exito", listaitem)));
    }

    // METODO DE GUARDADO
    public Result savePerson() {
        boolean success = false;
        String message = "";
        Response response = null;

        Map<String, String[]> params = request().body().asFormUrlEncoded();

        String idHidden = params.get("idHidden")[0];
        String txtNombre = params.get("txtNombre")[0];
        Integer txtEdad = Integer.parseInt(params.get("txtEdad")[0]);

        try {

            if (txtNombre.isEmpty()) {
                message = Messages.get("api.record.empty");
                response = new Response(success, message);
                return ok(Json.toJson(response));
            } else{

                Person person;
                if (idHidden.isEmpty()) {
                    person = new Person();
                    person.save();
                    message= Messages.get("api.record.save");
                }else {
                    int idObj= Integer.parseInt(idHidden);
                    person = Person.find.byId(idObj);
                    message= Messages.get("api.record.update");
                }

                person.setNombre(txtNombre);
                person.setEdad(txtEdad);
                person.save();
                success = true;
            }
            response = new Response(success,message);

        }catch(Exception e) {
            e.printStackTrace();
        }
        return ok(Json.toJson(response));
    }

    public Result deletePerson(String id){
        boolean success = false;
        String message = Messages.get("api.record.delete.error");
        Person person = null;
        Response response = null;
        try{
            person = Person.find.byId(Integer.parseInt(id));
            if (person != null){
                person.setDeletedAt(new Date());
                person.update();
                success = true;
                message=Messages.get("api.record.delete");
            }
            response = new Response(success,message);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ok(Json.toJson(response));
    }

    //LLENAR EL FORMULARIO PARA MODIFICAR
    public Result getPerson(String id){
        int idCtrl=Integer.parseInt(id.trim());
        boolean success = false;
        String message = Messages.get("api.record.error");
        Person person = null;
        ItemPerson item=null;
        Response<ItemPerson> response=null;
        try{
            item = new ItemPerson();
            person = Person.find.byId(idCtrl);

            if(person != null){
                item.setIdPersona(person.getIdPersona());
                item.setNombre(person.getNombre());
                item.setEdad(person.getEdad());
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
