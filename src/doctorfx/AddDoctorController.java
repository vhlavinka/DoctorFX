
package doctorfx;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.DBProps;
import models.Doctor;
import models.ORM;
import models.Specialty;

public class AddDoctorController implements Initializable {
    // private member variables
    private DoctorFXController mainController;
    
    // fxml variables
    @FXML 
    private TextField firstName;
    
    @FXML
    private TextField lastName;
    
    @FXML
    private ListView<Specialty> specialtyList;
    
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
            Specialty specialty = specialtyList.getSelectionModel().getSelectedItem();
            
            // validations
            if (specialty == null) {
                throw new ExpectedException("must select a specialty");
            }
            if (first.length() <= 0 || last.length() <= 0) {
                throw new ExpectedException("first and/or last name cannot be empty");
            }
            Doctor existingDoctor
                    = ORM.findOne(Doctor.class, "where name=?", new Object[]{name});
            if (existingDoctor != null) {
                throw new ExpectedException("existing doctor with same name");
            }

            // store the new doctor
            Doctor newDoctor = new Doctor(name, specialty.getId());

            ORM.store(newDoctor);
            System.out.println("successfully added new doctor");
            
            // access the features of DoctorFXController
            ListView<Doctor> doctorList = mainController.getDoctorList();
            TextArea display = mainController.getDisplay();
            
            // reload doctorlist from database
            doctorList.getItems().clear();
            Collection<Doctor> doctors = ORM.findAll(Doctor.class);
            for (Doctor doctor : doctors) {
                doctorList.getItems().add(doctor);
            }
            
            // update text area in main controller
            display.setText(Helper.info(newDoctor));

            // select in list and scroll to added book
            doctorList.getSelectionModel().select(newDoctor);
            doctorList.scrollTo(newDoctor);

            doctorList.requestFocus();
            mainController.setLastFocused(doctorList);
            
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
            ORM.init(DBProps.getProps());
            
            Collection<Specialty> specialties = ORM.findAll(Specialty.class);
            for (Specialty specialty : specialties) {
                specialtyList.getItems().add(specialty);
                System.out.println("added: " + specialty.getSpecialtyName());
            }

            // format specialty list
            SpecialtyCellCallback specialtyCellCallback = new SpecialtyCellCallback();
            specialtyList.setCellFactory(specialtyCellCallback);
        } catch (Exception ex) {

        }

    }
    
}
