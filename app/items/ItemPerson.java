package items;
import com.avaje.ebean.Model;
import java.util.Date;

/**
 * Created by educacion on 24/11/2017.
 */
public class ItemPerson extends Model{
    private Integer idPersona;
    private String nombre;
    private Integer edad;
    private Date deletedAt;

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
}
