package introsde.document.ws;

import introsde.document.model.Goal;
import introsde.document.model.Measure;
import introsde.document.model.Person;

import java.text.ParseException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional
public interface People {
	
    @WebMethod(operationName="readPerson")
    @WebResult(name="person") 
    public Person readPerson(@WebParam(name="personId") int id);
 
    @WebMethod(operationName="readPersonList")
    @WebResult(name="people") 
    public List<Person> readPersonList();
 
    @WebMethod(operationName="createPerson")
    @WebResult(name="person") 
    public Person createPerson(@WebParam(name="person", targetNamespace = "http://ws.document.introsde/") Person person);
 
    @WebMethod(operationName="updatePerson")
    @WebResult(name="person") 
    public Person updatePerson(@WebParam(name="person", targetNamespace = "http://ws.document.introsde/") Person person);
    
    @WebMethod(operationName="deletePerson")
    @WebResult(name="delete") 
    public int deletePerson(@WebParam(name="personId") int id);
    
    @WebMethod(operationName="readPersonHistory")
    @WebResult(name="measure") 
    public List<Measure> readPersonHistory(@WebParam(name="personId") int id, @WebParam(name="type") String type);
    
    @WebMethod(operationName="readMeasureTypes")
    @WebResult(name="measure") 
    public List<Measure> readMeasureTypes();
    
    @WebMethod(operationName="readPersonMeasure")
    @WebResult(name="measure") 
    public Measure readPersonMeasure(@WebParam(name="personId")int id, @WebParam(name="type")String type, @WebParam(name="idMeasure")int mid);
    
    @WebMethod(operationName="savePersonMeasure")
    @WebResult(name="measure")
    public Measure savePersonMeasure(@WebParam(name="personId")int id,  @WebParam(name="measure") Measure m) throws ParseException;
       
    @WebMethod(operationName="updatePersonMeasure")
    @WebResult(name="measure") 
    public Measure updatePersonMeasure(@WebParam(name="personId") int id, @WebParam(name="measure") Measure m);
    
    //Added for Goals of Final Project
    @WebMethod(operationName="readPersonGoalHistory")
    @WebResult(name="goal") 
    public List<Goal> readPersonGoalHistory(@WebParam(name="personId") int id, @WebParam(name="type") String type);
    
    @WebMethod(operationName="readGoalTypes")
    @WebResult(name="goal") 
    public List<Goal> readGoalTypes();
    
    @WebMethod(operationName="readPersonGoal")
    @WebResult(name="goal") 
    public Goal readPersonGoal(@WebParam(name="personId")int id, @WebParam(name="type")String type, @WebParam(name="idGoal")int gid);
    
    @WebMethod(operationName="savePersonGoal")
    @WebResult(name="goal")
    public Goal savePersonGoal(@WebParam(name="personId")int id,  @WebParam(name="goal") Goal g) throws ParseException;
       
    @WebMethod(operationName="updatePersonGoal")
    @WebResult(name="goal") 
    public Goal updatePersonGoal(@WebParam(name="personId") int id, @WebParam(name="goal") Goal g);

}