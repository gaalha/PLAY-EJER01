package models;
import com.avaje.ebean.Model;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by educacion on 29/11/2017.
 */
@Entity
@Table(name = "usuario")
public class User extends Model{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUser")
    private Integer idUser;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "delete_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    public Integer getIdUser() {
        return idUser;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public static Finder<Integer, User> finder = new Finder<>(User.class);

    public static List<User> listauser()    {
        List<User> lista = finder.where().isNull("deletedAt").findList();
        return lista;
    }

    public boolean getUserLogin(String name, String pass){
        User user = finder.where().isNull("deletedAt").eq("name", name).eq("password", pass).findUnique();
        if(user == null) return false;
        return true;
    }
}
