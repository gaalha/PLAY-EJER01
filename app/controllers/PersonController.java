package controllers;
import items.ItemPerson;
import models.Person;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import responses.Response;
import java.util.ArrayList;
import java.util.List;

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

}
