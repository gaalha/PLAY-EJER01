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
    public Result getPersonTask() {
        return ok(views.html.personTaskGrid.render());
    }
    public Result getPerson() {
        return ok(views.html.personGrid.render());
    }
    public Result getTaskTwo() {
        return ok(views.html.personTaskGrid.render());
    }
}
