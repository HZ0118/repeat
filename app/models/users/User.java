package models.users;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity

@Table(name = "user")

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@DiscriminatorColumn(name = "role")

@DiscriminatorValue("customer")

public class User extends Model {

    @Id
    @Constraints.Required
    private String email;

    @Column(insertable = false, updatable = false)
    private String role;

    @Constraints.Required
    private String name;

    @Constraints.Required
    private String password;


    public User(){
    }

    public User(String email, String name, String password) {
        this.email = email;
        this.role = "customer";
        this.name = name;
        this.password = password;
    }

    public static Finder<String,User> find = new Finder<String,User>(User.class);

    public static List<User> findAll(){
        return User.find.all();
    }

    public static User authenticate(String email,String password){
        return find.where().eq("email",email).eq("password",password).findUnique();
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public static User getUserById(String id) {
        if(id == null)
            return null;
        else
            return find.byId(id);
    }

}

