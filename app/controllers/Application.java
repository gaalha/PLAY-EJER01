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

    public Result getTask(){
        return ok(views.html.taskGrid.render());
    }

}
