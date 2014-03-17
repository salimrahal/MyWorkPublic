package controllers;
//http://www.playframework.com/documentation/2.2.0/JavaTodoList
import play.*;
import play.mvc.*;
import models.*;
import play.data.*;
import views.html.*;
import play.data.*;
import static play.data.Form.*;
import play.data.validation.Constraints.*;
import java.net.*;
import java.io.*;


import views.html.*;
public class Application extends Controller {
	static Form<Task> taskForm = Form.form(Task.class);
    
	public static Result index() {
        //return ok(index.render("Your new application is ready."));
    	//return ok("Hello World");
    	return redirect(routes.Application.tasks());
    }

    public static Result tasks() {
    	return ok(views.html.index.render(Task.all(), taskForm));      
    	}
      
      public static Result newTask() { 	
    	  Form<Task> filledForm = taskForm.bindFromRequest();
    	  if(filledForm.hasErrors()) { 
    	    return badRequest(
    	      views.html.index.render(Task.all(), filledForm)
    	    );
    	  } else {
    	    Task.create(filledForm.get());
    	    return redirect(routes.Application.tasks());  
    	  }
      }
      
      public static Result deleteTask(Long id) {
        return TODO;
      }
      public static Result show() {
    	  String s ="<a href=\"https://www.coursera.org/courses?search=chemistry\" tabindex=\"2\">Chemistry</a><br>";
          s+="<a href=\"https://www.coursera.org/courses?orderby=upcoming&search=CS:Software%20Engineering/\" tabindex=\"1\">CS:Software Engineering</a><br>";
    	  //s+="<p>"+readURL()+"</p>";
          return ok(s).as("text/html");
    	  //return redirect("http://stackoverflow.com/questions/10962694");
      	}
      
      //Unused
      public static String readURL(){
    	  String res="";	
    	  try{
    	  URL oracle = new URL("https://www.coursera.org/courses?search=chemistry");
      
      BufferedReader in = new BufferedReader(
      new InputStreamReader(oracle.openStream()));
      String inputLine;
      while ((inputLine = in.readLine()) != null){
          System.out.println("readURL"+inputLine);
          res+=inputLine;
      in.close();
      }
      }
      catch (MalformedURLException e) { 
    	    // new URL() failed
    	    // ...
    	} catch (IOException e) {   
    	    // openConnection() failed
    	    // ...
    	}    
      return res;
      }
}
