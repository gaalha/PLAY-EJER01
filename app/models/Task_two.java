
package models;

import com.avaje.ebean.Model;
import javax.persistence.*;
import java.util.*;

/**
 * Created by educacion on 24/11/2017.
 */
@Entity
@Table(name = "task_two")
public class Task_two extends Model{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtasktwo")
    private Integer idTask;
    @Column(name = "taskname")
    private String title;
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "id_person", referencedColumnName = "idper")
    @ManyToOne
    private Person idPersona;
    @Column(name = "delete_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    public Integer getIdTask() {
        return idTask;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setIdTask(Integer idTask) {
        this.idTask = idTask;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Person idPersona) {
        this.idPersona = idPersona;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public static Finder<Integer, Task_two> find = new Finder<>(Task_two.class);
    public static List<Task_two> list_task(){
        List<Task_two> lista = find.where().isNull("deletedAt").findList();
        return lista;
    }

    public static List<Task_two> getTaskByPerson(Integer idPersona){
        List<Task_two> taskList = find.where().isNull("deletedAt").eq("idPersona", Person.find.byId(idPersona)).findList();
        return taskList;
    }
}
