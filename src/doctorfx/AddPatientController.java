
package doctorfx;

import static doctorfx.Helper.currentDate;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.DBProps;
import models.Doctor;
import models.ORM;
import models.Patient;
import models.Specialty;

public class AddPatientController implements Initializable {
    // private member variables
    private DoctorFXController mainController;
    
    // fxml variables
    @FXML 
    private TextField firstName;
    
    @FXML
    private TextField lastName;
    
    void setMainController(DoctorFXController mainController) {
        this.mainController = mainController;
    }
    
    
    // add button method
    @FXML
    private void add(Event event) {
        try {
            String first = firstName.getText().trim();
            String last = lastName.getText().trim();
            String name = last + "," + first;
            
            // validations
            if (first.length() <= 0 || last.length() <= 0) {
                throw new ExpectedException("first and/or last name cannot be empty");
            }
            Patient existingPatient
                    = ORM.findOne(Patient.class, "where name=?", new Object[]{name});
            if (existingPatient != null) {
                throw new ExpectedException("existing patient with same name");
            }

            // store the new patient
            Patient newPatient = new Patient(name, Helper.currentDate());

            ORM.store(newPatient);
            System.out.println("successfully added new patient");
            
            // access the features of DoctorFXController
            ListView<Patient> patientList = mainController.getPatientList();
            TextArea display = mainController.getDisplay();
            
            // reload patientlist from database
            patientList.getItems().clear();
            Collection<Patient> patients = ORM.findAll(Patient.class);
            for (Patient patient : patients) {
                patientList.getItems().add(patient);
            }
            
            // update text area in main controller
            display.setText(Helper.info(newPatient));

            // select in list and scroll to added book
            patientList.getSelectionModel().select(newPatient);
            patientList.scrollTo(newPatient);

            patientList.requestFocus();
            mainController.setLastFocused(patientList);
            
            // close the window
            ((Button) event.getSource()).getScene().getWindow().hide();

        } catch (ExpectedException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(ex.getMessage());
            alert.show();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
    
    @FXML
    private void cancel(Event event) {
        ((Button)event.getSource()).getScene().getWindow().hide();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

        } catch (Exception ex) {

        }

    }
    
}
