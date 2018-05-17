/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.PreparedStatement;

/**
 *
 * @author la_le
 */
public class Patient extends Model {

    private int id = 0;
    private String patName;
    private Date admitDate;

    // corresponds to a database table
    public static final String TABLE = "patient";

    // must have default constructor accessible to the package
    Patient() {
    }
    
    public Patient(String patName){
        this.patName = patName;
    }

    public Patient(String patName, Date admitDate){
        this.patName = patName;
        this.admitDate = admitDate;
    }
    
    public Patient(Date admitDate, String patName){
        this.patName = patName;
        this.admitDate = admitDate;
    }
        
    
    @Override
    public int getId() {
        return id;
    }
    
    public String getPatName() {
        return patName;
    }
    
    public Date getAdmitDate() {
        return admitDate;
    }
    
    public void setPatName() {
        this.patName = patName;
    }
    
    public void setAdmitDate() {
        this.admitDate = admitDate;
    }
    
    // used for SELECT operations in ORM.load, ORM.findAll, ORM.findOne
    @Override
    void load(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        patName = rs.getString("name");
        admitDate = rs.getDate("admitted");
    }

    // user for INSERT operations in ORM.store (for new record)
    @Override
    void insert() throws SQLException {
        Connection cx = ORM.connection();
        String sql = String.format(
                "insert into %s (name,admitted) values (?,?)", TABLE);
        PreparedStatement st = cx.prepareStatement(sql);
        int i = 0;
        st.setString(++i, patName);
        st.setDate(++i, admitDate);

        st.executeUpdate();
        id = ORM.getMaxId(TABLE);
    }

    // used for UPDATE operations in ORM.store (for existing record)
    @Override
    void update() throws SQLException {
        Connection cx = ORM.connection();
        String sql = String.format(
                "update %s set name=?,admitted=?where id=?", TABLE);
        PreparedStatement st = cx.prepareStatement(sql);
        int i = 0;
        st.setString(++i, patName);
        st.setDate(++i, admitDate);
        st.setInt(++i, id);
        st.executeUpdate();
    }

}
