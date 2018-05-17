
package doctorfx;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import models.Doctor;
import models.ORM;
import models.Patient;
import models.Treatment;


public class EditReportController implements Initializable {
    
    private DoctorFXController mainController;
    
    void setMainController(DoctorFXController mainController) {
        this.mainController = mainController;
    }
    
    /* -----------------------------------------    
        IMPORT DATA FROM MAIN CONTROLLER   
    -----------------------------------------  */   
    private Doctor loadDoctor;
    private Patient loadPatient;
    private String loadReport;
    private Treatment reportToModify;
    
    void setReportToModify(Doctor doctor, Patient patient, Treatment treatment){
       this.loadReport = treatment.getReport();       
       this.loadDoctor = doctor;
       this.loadPatient = patient;
       this.reportToModify = treatment;
       
       // initialize fields
       doctorLabel.setText(doctor.getName());
       patientLabel.setText(patient.getPatName());
       reportArea.setText(loadReport);
      
    }
 
    /* -----------------------------------------  
        PRIVATE MEMBER VARIABLES
    ------------------------------------------ */
    @FXML
    private Label doctorLabel;
    
    @FXML
    private Label patientLabel;
    
    @FXML
    private TextArea reportArea;
    
    /* ----------------------------------------- 
        BUTTON METHODS
    ----------------------------------------- */ 
    @FXML
    private void modify(Event event) {
        try {
            TextArea display = mainController.getDisplay();
            reportToModify.setReport(reportArea.getText());
            ORM.store(reportToModify);
            display.setText(Helper.info(reportToModify));
            ((Button) event.getSource()).getScene().getWindow().hide();

            System.out.println("successfully modified");

        }
        catch(Exception ex){ 
            ex.printStackTrace(System.err);
            System.exit(1);         
        }

    } 

    @FXML
    private void cancel(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK) {
            return;
        }
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {    

    }  
    
}
