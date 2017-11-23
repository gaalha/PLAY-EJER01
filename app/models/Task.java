package models;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.*;

/**
 * Created by educacion on 22/11/2017.
 */

@Entity
@Table(name = "task")
public class Task  extends Model{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtask")
    private Integer idTask;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "id_persona", referencedColumnName = "idpersona")
    @ManyToOne
    private Persona idPersona;

    public Integer getIdTask() {
        return idTask;
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

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    public static Finder<Integer, Task> find = new Finder<>(Task.class);

    public static List<Task> listtask()
    {
        List<Task> lista;
        lista = find.where().findList();
        return lista;
    }

}

