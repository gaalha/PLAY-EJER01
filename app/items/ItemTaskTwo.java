package items;
import java.util.*;

/**
 * Created by educacion on 24/11/2017.
 */
public class ItemTaskTwo {
    private Integer idTaskTwo;
    private String titleTask;
    private String descriptionTask;
    private Date deleteAt;
    private Integer idPersona;
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdTaskTwo() {
        return idTaskTwo;
    }

    public void setIdTaskTwo(Integer idTaskTwo) {
        this.idTaskTwo = idTaskTwo;
    }

    public String getTitleTask() {
        return titleTask;
    }

    public void setTitleTask(String titleTask) {
        this.titleTask = titleTask;
    }

    public String getDescriptionTask() {
        return descriptionTask;
    }

    public void setDescriptionTask(String descruptionTask) {
        this.descriptionTask = descruptionTask;
    }

    public Date getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Date deleteAt) {
        this.deleteAt = deleteAt;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }


}
