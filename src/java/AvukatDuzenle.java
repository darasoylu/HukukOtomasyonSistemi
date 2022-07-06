
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

@ManagedBean(name = "avukatduzenle")
@SessionScoped
public class AvukatDuzenle {

    private String Ad;
    private String ID;
    private String Email;
    private String Sifre;

    CachedRowSet rowSet = null;
    DataSource dataSource;

    public String getAd() {
        return Ad;
    }

    public void setAd(String Ad) {
        this.Ad = Ad;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getSifre() {
        return Sifre;
    }

    public void setSifre(String Sifre) {
        this.Sifre = Sifre;
    }

    public AvukatDuzenle() {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/sample");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public ResultSet bul() throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT a.id, a.ad, a.sifre, a.email "
                    + "FROM avukatlar a "
                    + "WHERE a.id=?");
            ps.setInt(1, Login.global_id);
            rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate(ps.executeQuery());
            return rowSet;
        } finally {
            connection.close();
        }
    }
    
    public String degistir() throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE AVUKATLAR SET ad=?, email=?, sifre=? WHERE id=?");
            ps.setString(1, getAd());
            ps.setString(2, getEmail());
            ps.setString(3, getSifre());
            ps.setInt(4, Login.global_id);
            ps.executeUpdate();
            return "duzenle";
        } finally {
            connection.close();
        }
    }
}
