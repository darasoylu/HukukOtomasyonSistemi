
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

@ManagedBean(name = "muvekkiller")
@SessionScoped
public class Muvekkiller {

    private String TC;
    private String Ad;
    private String ID;
    private String Email;
    private String avukatID;
    private String bulunacak_muvekkil;
    private String silinecekMuvekkilId;

    CachedRowSet rowSet = null;
    DataSource dataSource;

    public String getSilinecekMuvekkilId() {
        return silinecekMuvekkilId;
    }

    public void setSilinecekMuvekkilId(String silinecekMuvekkilId) {
        this.silinecekMuvekkilId = silinecekMuvekkilId;
    }

    public String getBulunacak_muvekkil() {
        return bulunacak_muvekkil;
    }

    public void setBulunacak_muvekkil(String bulunacak_muvekkil) {
        this.bulunacak_muvekkil = bulunacak_muvekkil;
    }

    public String getTC() {
        return TC;
    }

    public void setTC(String TC) {
        this.TC = TC;
    }

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

    public String getAvukatID() {
        return avukatID;
    }

    public void setAvukatID(String avukatID) {
        this.avukatID = avukatID;
    }

    public Muvekkiller() {
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
            PreparedStatement ps = connection.prepareStatement("SELECT m.id, m.tc, m.ad, m.soyad, m.email "
                    + "FROM avukatlar a, muvekkiller m "
                    + "WHERE a.id=m.avukat_id AND a.id=?");
            ps.setInt(1, Login.global_id);
            rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate(ps.executeQuery());
            return rowSet;
        } finally {
            connection.close();
        }
    }

    public ResultSet arama() throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT m.id, m.tc, m.ad, m.soyad, m.email "
                    + "FROM avukatlar a, muvekkiller m "
                    + "WHERE a.id=m.avukat_id AND m.ad=? AND a.id=?");
            ps.setString(1, getBulunacak_muvekkil());
            ps.setInt(2, Login.global_id);
            rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate(ps.executeQuery());
            return rowSet;
        } finally {
            connection.close();
        }
    }

    public String sil() throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM muvekkiller WHERE id=?");
            ps.setInt(1, Integer.parseInt(getSilinecekMuvekkilId()));
            ps.executeUpdate();
            return "muvekkil";
        } finally {
            connection.close();
        }
    }
}


