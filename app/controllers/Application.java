package controllers;

import models.Persona;
import play.mvc.Controller;
import play.mvc.Result;
import items.ItemPeople;

/**
 * Created by educacion on 17/11/2017.
 */
public class Application extends Controller {
    public Result getPeople(){
        return ok(views.html.persona.render());
    }

    public Result getPeopleEdit(Integer id) {
        Persona per = Persona.find.byId(id);
        return ok(views.html.personaEdit.render(per));
    }

    public Result getTask(){
        return ok(views.html.taskGrid.render());
    }

}
