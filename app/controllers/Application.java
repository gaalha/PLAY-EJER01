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
        String usuario = session("user");
        if(usuario == null){
            return getLogin();
        }else{
            return ok(views.html.persona.render());
        }
    }
    public Result getTask(){
        String usuario = session("user");
        if(usuario == null){
            return getLogin();
        }else{
            return ok(views.html.taskGrid.render());
        }
    }
    public Result getPersonTask() {
        String usuario = session("user");
        if(usuario == null){
            return getLogin();
        }else{
            return ok(views.html.personTaskGrid.render());
        }
    }
    public Result getPerson() {
        String usuario = session("user");
        if(usuario == null){
            return getLogin();
        }else{
            return ok(views.html.personGrid.render());
        }
    }
    public Result getTaskTwo() {
        String usuario = session("user");
        if(usuario == null){
            return getLogin();
        }else{
            return ok(views.html.taskTwoGrid.render());
        }
    }

    public Result getDashboard(){
        String usuario = session("user");
        if(usuario == null){
            return getLogin();
        }else{
            return ok(views.html.dashboard.render());
        }
    }

    public Result getLogin(){
        String usuario = session("user");
        if(usuario != null){
            return getDashboard();
        }else{
            return ok(views.html.login.render());
        }
    }
}
