
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Specialty extends Model {

    private int id = 0;
    private String specialtyName;

    public static final String TABLE = "specialty";

    Specialty() {
    }
    public Specialty(String specialtyName) {
        this.specialtyName = specialtyName;
    }        
    @Override
    public int getId() {
        return id;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }
    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }
    

    
    // used for SELECT operations in ORM.load, ORM.findAll, ORM.findOne
    @Override
    void load(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        specialtyName = rs.getString("name");
    }

    // user for INSERT operations in ORM.store (for new record)
    @Override
    void insert() throws SQLException {
        Connection cx = ORM.connection();
        String sql = String.format(
                "insert into %s (name) values (?)", TABLE);
        PreparedStatement st = cx.prepareStatement(sql);
        int i = 0;
        st.setString(++i, specialtyName);
        st.executeUpdate();
        id = ORM.getMaxId(TABLE);
    }

    // used for UPDATE operations in ORM.store (for existing record)
    @Override
    void update() throws SQLException {
        Connection cx = ORM.connection();
        String sql = String.format(
                "update %s set name=? where id=?", TABLE);
        PreparedStatement st = cx.prepareStatement(sql);
        int i = 0;
        st.setString(++i, specialtyName);
        st.setInt(++i, id);
        st.executeUpdate();
    }
}
