package introsde.document.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import introsde.document.dao.LifeCoachDao;
@Entity  // indicates that this class is an entity to persist in DB
@Table(name="\"Goal\"") // to whate table must be persisted

@NamedQuery(name="Goal.findAll", query="SELECT p FROM Goal p")
public class Goal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id // defines this attributed as the one that identifies the entity
    // @GeneratedValue(strategy=GenerationType.AUTO) 
    @GeneratedValue(generator="sqlite_Goal")
    @TableGenerator(name="sqlite_Goal", table="sqlite_sequence",
        pkColumnName="name", valueColumnName="seq",
        pkColumnValue="Goal")
    @Column(name="\"idGoal\"") // maps the following attribute to a column
    private int idGoal;
    
    @ManyToOne
	@JoinColumn(name="\"idPerson\"",referencedColumnName="\"idPerson\"")
    private Person person;
    
    @Column(name="\"value\"")
    private String value;
    
    @Column(name="\"valueType\"")
    private String valueType;
    
    @Column(name="\"type\"")
    private String type;
    
    @Temporal(TemporalType.DATE) // defines the precision of the date attribute
    @Column(name="\"dateRegistered\"")
    private Date date; 
    
    // the GETTERS and SETTERS of all the private attributes
    
    public int getIdGoal() {
		return idGoal;
	}

	public void setIdGoal(int idGoal) {
		this.idGoal = idGoal;
	}


	@XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
    public String getDate(){
    	if(this.date == null) {
    	      return null;
    	}
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return df.format(this.date);
    }
    
    @XmlTransient
    public Date getDateRegistered(){
    	return this.date;
    }

    public void setDate(String date) throws ParseException{
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.ENGLISH);
        Date d = format.parse(date);
        this.date = d;
    }
	
	// QUERYING TO THE DATABASE
	
	public static Goal getGoalById(int gid) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        Goal p = em.find(Goal.class, gid);
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static List<Goal> getAll() {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        List<Goal> list = em.createNamedQuery("Goal.findAll", Goal.class)
            .getResultList();
        LifeCoachDao.instance.closeConnections(em);
        return list;
    }

    public static Goal saveGoal(int personId, Goal ls) throws ParseException {
    	ls.setPerson(Person.getPersonById(personId));
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    	ls.setDate(sdf.format(new Date()));
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(ls);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        Person.refreshLists(personId);
        return ls;
    } 

    public static Goal updateGoal(Goal ls) {
    	if(ls.getPerson() == null){
    		ls.setPerson(Goal.getGoalById(ls.getIdGoal()).getPerson());
    	}
        EntityManager em = LifeCoachDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        ls=em.merge(ls);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return ls;
    }

    public static void removeGoal(Goal ls) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        ls=em.merge(ls);
        em.remove(ls);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
    }
    
    public static List<Goal> getGoalsByType(int id, String type){
    	System.out.println("Person with id: "+id+ "and goal type:"+type);
    	EntityManager em = LifeCoachDao.instance.createEntityManager();
    	List<Goal> ls = em
    			.createQuery("SELECT g FROM Goal g WHERE g.type = :type and g.person.idPerson = :id", Goal.class)
   				.setParameter("type", type)
   				.setParameter("id", id).getResultList();
   	    LifeCoachDao.instance.closeConnections(em);

   	    if (ls.isEmpty()){
   	    	return null;
    	}
        return ls;
    	
    }

}
