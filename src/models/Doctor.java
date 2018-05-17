
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor extends Specialty {

    private int id = 0;
    private String name;
    private int docSpecialty;

    // corresponds to a database table
    public static final String TABLE = "doctor";

    // must have default constructor accessible to the package
    Doctor() {
    }

    public Doctor(String name, int docSpecialty) {
        this.name = name;
        this.docSpecialty = docSpecialty;
    }
  
    public Doctor(int docSpecialty, String name) {
        this.name = name;
        this.docSpecialty = docSpecialty;
    }

    public Doctor(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public int getDocSpecialty() {
        return docSpecialty;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDocSpecialty(int docSpecialty) {
        this.docSpecialty = docSpecialty;
    }
    public String getSpecialty() {
        try {
            Specialty specialty = ORM.findOne(Specialty.class,
                "where id=?",
                new Object[]{docSpecialty});
            return specialty.getSpecialtyName();

        } catch (Exception ex) {

        }
        return " ";
    }
    
    // used for SELECT operations in ORM.load, ORM.findAll, ORM.findOne
    @Override
    void load(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        name = rs.getString("name");
        docSpecialty = rs.getInt("specialty_id");
    }

    // used for INSERT operations in ORM.store (for new record)
    @Override
    void insert() throws SQLException {
        Connection cx = ORM.connection();
        String sql = String.format(
                "insert into %s (name,specialty_id) values (?,?)", TABLE);
        PreparedStatement st = cx.prepareStatement(sql);
        int i = 0;
        st.setString(++i, name);
        st.setInt(++i, docSpecialty);
        st.executeUpdate();
        id = ORM.getMaxId(TABLE);
    }

    // used for UPDATE operations in ORM.store (for existing record)
    @Override
    void update() throws SQLException {
    Connection cx = ORM.connection();
    String sql = String.format(
        "update %s set name=?,specialty=? where id=?", TABLE);
    PreparedStatement st = cx.prepareStatement(sql);
    int i = 0;
    st.setString(++i, name);
    st.setInt(++i, docSpecialty);
    st.setInt(++i, id);
    st.executeUpdate();
    }

}
