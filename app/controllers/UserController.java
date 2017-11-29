package controllers;

import items.ItemTaskTwo;
import models.Person;
import models.Task_two;
import models.User;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import responses.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by educacion on 29/11/2017.
 */
public class UserController extends Controller{

    public Result validateLogin(){
        boolean success = false;
        String message = "";
        Response response = null;

        Map<String, String[]> params = request().body().asFormUrlEncoded();
        String name = params.get("txtName")[0];
        String pass = params.get("txtPass")[0];

        try {
            if (name.isEmpty() || pass.isEmpty()) {
                message = Messages.get("api.record.empty");
                response = new Response(success, message);
                return ok(Json.toJson(response));
            } else{
                User user = new User();
                boolean validar = user.getUserLogin(name, pass);

                if (validar == true) {
                    message= Messages.get("api.record.welcome");
                    success = true;
                }else {
                    message= Messages.get("api.record.null");
                    success = false;
                }
            }
        }
        catch(Exception e) {
            message = e.toString();
        }
        response = new Response(success,message);
        return ok(Json.toJson(response));
    }

}
