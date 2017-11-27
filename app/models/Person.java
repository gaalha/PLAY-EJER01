package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.*;

/**
 * Created by educacion on 24/11/2017.
 */
@Entity
@Table(name = "person")
public class Person extends Model{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idper")
    private Integer idPersona;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "edad")
    private Integer edad;
    @Column(name = "delete_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public static Finder<Integer, Person> find = new Finder<>(Person.class);

    public static List<Person> listaperson()    {
        List<Person> lista = find.where().isNull("deletedAt").findList();
        return lista;
    }

    public static Person getById(int idPersona){
        Person person = find.where().isNull("deletedAt").eq("idPersona", idPersona).findUnique();
        if(person == null) return null;
        return person;
    }
}
