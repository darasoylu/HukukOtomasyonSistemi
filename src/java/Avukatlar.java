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

@ManagedBean(name = "avukatlar")
@SessionScoped
public class Avukatlar {
    private String ID;
    private String ad;
    private String sifre;
    private String email;
    private String bulunacakAvukat;
    private String silinecekAvukat;
    
    CachedRowSet rowSet = null;
    DataSource dataSource;

    public String getBulunacakAvukat() {
        return bulunacakAvukat;
    }

    public void setBulunacakAvukat(String bulunacakAvukat) {
        this.bulunacakAvukat = bulunacakAvukat;
    }

    public String getSilinecekAvukat() {
        return silinecekAvukat;
    }

    public void setSilinecekAvukat(String silinecekAvukat) {
        this.silinecekAvukat = silinecekAvukat;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Avukatlar(){
        try{
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/sample");
        } catch(NamingException e){
            e.printStackTrace();
        }
    }
    public ResultSet bul() throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT a.id, a.ad, a.sifre, a.email FROM avukatlar a");
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
            PreparedStatement ps = connection.prepareStatement("SELECT a.id, a.ad, a.sifre, a.email FROM avukatlar a WHERE a.ad =?");
            ps.setString(1, getBulunacakAvukat());
            rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate(ps.executeQuery());
            return rowSet;
        } finally {
            connection.close();
        }
    }
}

