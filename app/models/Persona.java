package models;

import com.avaje.ebean.Model;
import javax.persistence.*;
import java.util.*;

/**
 * Created by educacion on 16/11/2017.
 */

@Entity
@Table(name = "persona")
public class Persona extends Model{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpersona")
    private Integer idPersona;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "edad")
    private Integer edad;


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

    public static Finder<Integer, Persona> find = new Finder<>(Persona.class);

    public static List<Persona> listapersona()
    {
        List<Persona> lista = new ArrayList<>();
        lista = find.where().findList();
        return lista;
    }
}
