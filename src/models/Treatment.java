/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Treatment extends Model {

    private int id = 0;
    private int doctor_id;
    private int patient_id;
    private String report;

    public static final String TABLE = "treatment";

    Treatment() {
    }
    
    public Treatment getDoctor(){
        try {
            return ORM.load(Doctor.class, doctor_id);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }
    
    public Treatment getPatient() {
        try {
            return ORM.load(Patient.class, patient_id);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }
    
    public Treatment(Doctor doctor, Patient patient) {
        this.doctor_id = doctor.getId();
        this.patient_id = patient.getId();
    }
        
    public Treatment(Patient patient, Doctor doctor ) {
        this.doctor_id = doctor.getId();
        this.patient_id = patient.getId();
    }
    
    public Treatment(Doctor doctor, Patient patient, String report) {
        this.doctor_id = doctor.getId();
        this.patient_id = patient.getId();
        this.report = report;
    }
   
    public Treatment(Patient patient, Doctor doctor, String report) {
        this.doctor_id = doctor.getId();
        this.patient_id = patient.getId();
        this.report = report;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
    

    // used for SELECT operations in ORM.load, ORM.findAll, ORM.findOne
    @Override
    void load(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        doctor_id = rs.getInt("doctor_id");
        patient_id = rs.getInt("patient_id");
        report = rs.getString("report");
    }

    // user for INSERT operations in ORM.store (for new record)
    @Override
    void insert() throws SQLException {
        Connection cx = ORM.connection();
        String sql = String.format(
                "insert into %s (doctor_id,patient_id,report) values (?,?,?)", TABLE);
        PreparedStatement st = cx.prepareStatement(sql);
        int i = 0;
        st.setInt(++i, doctor_id);
        st.setInt(++i, patient_id);
        st.setString(++i, report);
        st.executeUpdate();
        id = ORM.getMaxId(TABLE);
    }

    // used for UPDATE operations in ORM.store (for existing record)
    @Override
    void update() throws SQLException {
        Connection cx = ORM.connection();
        String sql = String.format(
                "update %s set report=? where id=?", TABLE);
        PreparedStatement st = cx.prepareStatement(sql);
        int i = 0;
        st.setString(++i, report);
        st.setInt(++i, id);
        st.executeUpdate();
    }
}
