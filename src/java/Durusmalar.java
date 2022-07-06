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

@ManagedBean(name = "durusmalar")
@SessionScoped
public class Durusmalar {
    private String muvekkilID;
    private String yer;
    private String saat;
    private String avukatID;
    private String dava;
    private String dosyaNo;
    private String ad;
    private String silinecekDosya;
    private String bulunacakDosyaNo;
    
    CachedRowSet rowSet = null;
    DataSource dataSource;

    public String getBulunacakDosyaNo() {
        return bulunacakDosyaNo;
    }

    public void setBulunacakDosyaNo(String bulunacakDosyaNo) {
        this.bulunacakDosyaNo = bulunacakDosyaNo;
    }
    
    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }
    public String getSilinecekDosya() {
        return silinecekDosya;
    }

    public void setSilinecekDosya(String silinecekDosya) {
        this.silinecekDosya = silinecekDosya;
    }

    public String getMuvekkilID() {
        return muvekkilID;
    }

    public void setMuvekkilID(String muvekkilID) {
        this.muvekkilID = muvekkilID;
    }

    public String getYer() {
        return yer;
    }

    public void setYer(String yer) {
        this.yer = yer;
    }

    public String getSaat() {
        return saat;
    }

    public void setSaat(String saat) {
        this.saat = saat;
    }

    public String getDava() {
        return dava;
    }

    public void setDava(String dava) {
        this.dava = dava;
    }

    public String getDosyaNo() {
        return dosyaNo;
    }

    public void setDosyaNo(String dosyaNo) {
        this.dosyaNo = dosyaNo;
    }
    
    
    public String getAvukatID() {
        return avukatID;
    }

    public void setAvukatID(String avukatID) {
        this.avukatID = avukatID;
    }   

    public Durusmalar() {
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
            PreparedStatement ps = connection.prepareStatement("SELECT d.dosyano, d.tur, d.yer, d.tarih, d.saat, m.ad  || ' ' || m.soyad as muvekkil "
                    + "FROM durusmalar d, muvekkiller m, avukatlar a "
                    + "WHERE a.id=m.avukat_id AND d.muvekkil_id=m.id AND d.avukat_id=a.id AND a.id=?");
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
            PreparedStatement ps = connection.prepareStatement("SELECT d.dosyano, d.tur, d.yer, d.tarih, d.saat, m.ad  || ' ' || m.soyad as muvekkil "
                    + "FROM durusmalar d, muvekkiller m, avukatlar a "
                    + "WHERE a.id=m.avukat_id AND d.muvekkil_id=m.id AND d.avukat_id=a.id AND d.dosyano=? AND a.id=?");
            ps.setInt(1, Integer.parseInt(getBulunacakDosyaNo()));
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
            PreparedStatement ps = connection.prepareStatement("DELETE FROM durusmalar WHERE dosyano=?");
            ps.setInt(1, Integer.parseInt(getSilinecekDosya()));
            ps.executeUpdate();
            return "durusma";
        } finally {
            connection.close();
        }
    }

}
