package items;

/**
 * Created by educacion on 22/11/2017.
 */
public class ItemTask {
    private Integer idtask;
    private String title;
    private String description;
    private Integer idPersona;
    private String nombre;

    public Integer getIdtask() {
        return idtask;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIdtask(Integer idtask) {
        this.idtask = idtask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
