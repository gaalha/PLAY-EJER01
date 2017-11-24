package controllers;
import items.ItemPerson;
import models.Person;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import responses.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by educacion on 24/11/2017.
 */
public class PersonController extends Controller{

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
    public Result save() {
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

}
