import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@ManagedBean(name = "login")
@SessionScoped
public class Login {

    private String id;
    private String sifre;
    private String kaydolId;
    private String kaydolAd;
    private String kaydolSifre;

    public static int global_id;

    CachedRowSet rowSet = null;
    DataSource dataSource;

    public String getKaydolId() {
        return kaydolId;
    }

    public void setKaydolId(String kaydolId) {
        this.kaydolId = kaydolId;
    }

    public String getKaydolAd() {
        return kaydolAd;
    }

    public void setKaydolAd(String kaydolAd) {
        this.kaydolAd = kaydolAd;
    }

    public String getKaydolSifre() {
        return kaydolSifre;
    }

    public void setKaydolSifre(String kaydolSifre) {
        this.kaydolSifre = kaydolSifre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public Login() {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/sample");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public String girisYap() throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM avukatlar WHERE id=? AND sifre=?");
            ps.setInt(1, Integer.parseInt(getId()));
            ps.setString(2, getSifre());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                global_id = Integer.parseInt(getId());
                return "dashboard";
            } else {
                return "index";
            }
        } finally {
            connection.close();
        }
    }

    public String kayıtOl() throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO avukatlar (id, ad, sifre) VALUES (?, ?, ?)");
            ps.setInt(1, Integer.parseInt(getKaydolId()));
            ps.setString(2, getKaydolAd());
            ps.setInt(3, Integer.parseInt(getKaydolSifre()));
            ps.executeUpdate();
        } finally {
            connection.close();
        }
        return "index";
    }
    
}
