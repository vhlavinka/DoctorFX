/*
*
*   VALERIE HLAVINKA
*   PROJECT 1
*
*/
package doctorfx;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.DBProps;
import models.Doctor;
import models.ORM;
import models.Patient;
import models.Treatment;

public class DoctorFXController implements Initializable {
    /* ------------------------
    
    DATA MEMBER VARIABLES
    
    ------------------------ */
    @FXML
    public ListView<Doctor> doctorList;

    @FXML
    public ListView<Patient> patientList;
   
    @FXML
    private TextArea display;
    
    @FXML
    private MenuButton orderMenu;

    private Node lastFocused = null;
    
    private final Collection<Integer> doctorTreatingPatientIds = new HashSet<>();
    private final Collection<Integer> patientTreatedByDoctorIds = new HashSet<>();
    private final Collection<Integer> removePatientLinks = new HashSet<>();
    
    
    void setLastFocused(Node lastFocused) {
        this.lastFocused = lastFocused;
    }
    
    /* ------------------------
    
    GETTER FUCNTIONS FOR MEMBER VARIABLES
    
    ------------------------ */
    public ListView<Doctor> getDoctorList() {
        return doctorList;
    }
 
    public ListView<Patient> getPatientList() {
        return patientList;
    }
 
    public TextArea getDisplay() {
        return display;
    }
   
    /* ------------------------
    
    ACTION METHODS
    
    ------------------------ */
    @FXML
    private void doctorSelect(Event event) {
        try {
            // Clear the lists first thing when selecting a patient
            // so we don't keep irrelevant information highlighted
            doctorList.setStyle(null);
            patientList.setStyle(null);

            Doctor doctor = doctorList.getSelectionModel().getSelectedItem();
            if (doctor == null) {
                if (lastFocused != null) {
                    lastFocused.requestFocus();
                }
                return;
            }
            lastFocused = doctorList;

            //get all patients treated by this doctor
            Collection<Treatment> treats = ORM.findAll(Treatment.class,
                    "where doctor_id=?", new Object[]{doctor.getId()});

            doctorTreatingPatientIds.clear();
            for (Treatment treat : treats) {
                doctorTreatingPatientIds.add(treat.getPatient_id());
            }
            // refresh patient list to show which patients are treated by selected doctor
            patientList.refresh();

            // display info about the doctor in TextArea
            display.setText(Helper.info(doctor));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }

    }

    @FXML
    private void patientSelect(Event event) {
        try {
            // Clear the lists first thing when selecting a patient
            // so we don't keep irrelevant information highlighted
            patientList.setStyle(null);
            doctorList.setStyle(null);
            
            Patient patient = patientList.getSelectionModel().getSelectedItem();
            if (patient == null) {
                if (lastFocused != null) {
                    lastFocused.requestFocus();
                }
                return;
            }
            lastFocused = patientList;

            //get all doctors that the patient is treated by
            Collection<Treatment> treats = ORM.findAll(Treatment.class,
                    "where patient_id=?", new Object[]{patient.getId()});

            patientTreatedByDoctorIds.clear();
            for (Treatment treat : treats) {
                patientTreatedByDoctorIds.add(treat.getDoctor_id());
            }
            // refresh patient list to show which patients are treated by selected doctor
            doctorList.refresh();
            
            // display info about the patient in the TextArea
            display.setText(Helper.info(patient));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }
    /*--------------------------
             MENU ITEMS
    ---------------------------*/    
    @FXML
    private void addDoctor(Event event) {
        try {
            //======================== The absolutely essential part
            // get fxmlLoader
            URL fxml = getClass().getResource("AddDoctor.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxml);
            fxmlLoader.load();

            // get scene from loader
            Scene scene = new Scene(fxmlLoader.getRoot());

            // create a stage for the scene
            Stage dialogStage = new Stage();
            dialogStage.setScene(scene);

            // specify dialog title
            dialogStage.setTitle("Add a Doctor");

            // make it block the application
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            // invoke the dialog
            dialogStage.show();

            // get controller from fxmlLoader
            AddDoctorController dialogController = fxmlLoader.getController();

            // pass the LibraryController to the dialog controller
            dialogController.setMainController(this);
            //===============================================   
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }
    
    @FXML
    private void addPatient(Event event){
        try {
            //======================== The absolutely essential part
            // get fxmlLoader
            URL fxml = getClass().getResource("AddPatient.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxml);
            fxmlLoader.load();

            // get scene from loader
            Scene scene = new Scene(fxmlLoader.getRoot());

            // create a stage for the scene
            Stage dialogStage = new Stage();
            dialogStage.setScene(scene);

            // specify dialog title
            dialogStage.setTitle("Add a Patient");

            // make it block the application
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            // invoke the dialog
            dialogStage.show();

            // get controller from fxmlLoader
            AddPatientController dialogController = fxmlLoader.getController();

            // pass the LibraryController to the dialog controller
            dialogController.setMainController(this);
            //===============================================   
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }        
    }
    
    @FXML 
    private void addSpecialty(Event event) {
        try {
            //======================== The absolutely essential part
            // get fxmlLoader
            URL fxml = getClass().getResource("AddSpecialty.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxml);
            fxmlLoader.load();

            // get scene from loader
            Scene scene = new Scene(fxmlLoader.getRoot());

            // create a stage for the scene
            Stage dialogStage = new Stage();
            dialogStage.setScene(scene);

            // specify dialog title
            dialogStage.setTitle("Add a Specialty");

            // make it block the application
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            // invoke the dialog
            dialogStage.show();

            // get controller from fxmlLoader
            AddSpecialtyController dialogController = fxmlLoader.getController();

            // pass the LibraryController to the dialog controller
            dialogController.setMainController(this);
            //===============================================   
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }        
        
    }
    
    @FXML
    private void linkDoctorPatient(Event event) {
        try{
            Doctor doctor = doctorList.getSelectionModel().getSelectedItem();
            Patient patient = patientList.getSelectionModel().getSelectedItem();
            if (doctor == null || patient == null) {
                throw new ExpectedException("Must select doctor and patient");
            }
            Treatment existingTreatment  = ORM.findOne(Treatment.class, "where doctor_id=? "
                    + "and patient_id=?", new Object[]{doctor.getId(),patient.getId()});
            if (existingTreatment != null) {
                throw new ExpectedException("treatment already exists");
            }
            // see what treatments the patient already has
            Collection<Treatment> treatments = ORM.findAll(Treatment.class, 
                    "where patient_id=?", new Object[]{patient.getId()});
            // get doctors linked from these treatments and see their specialty
            for (Treatment t : treatments) {
                Collection<Doctor> doctors = ORM.findAll(Doctor.class,
                        "where id=?", new Object[]{t.getDoctor_id()});
                // get the specialties of the doctors
                for (Doctor d : doctors) {
                    if(d.getDocSpecialty() == doctor.getDocSpecialty()){
                        throw new ExpectedException("patient already has"
                                + " doctor with same specialty");
                    }                       
                }
            }
      
            Treatment newTreatment = new Treatment(doctor, patient, "");
            ORM.store(newTreatment);
            display.setText(newTreatment.getReport());
        } catch(ExpectedException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(ex.getMessage());
            alert.show();
            if (lastFocused != null) {
                lastFocused.requestFocus();
            }
        } catch (Exception ex){
            ex.printStackTrace(System.err);
        }
            
    }
    
    /*--------------------------
             BUTTONS
    ---------------------------*/
    @FXML
    private void showReport(Event event) {
        try {
            Doctor doctor = doctorList.getSelectionModel().getSelectedItem();
            Patient patient = patientList.getSelectionModel().getSelectedItem();
            if(doctor == null || patient == null) {
                throw new ExpectedException("Must select doctor and patient");
            }
            Treatment treatment = ORM.findOne(Treatment.class, 
                    "where doctor_id=? and patient_id=?", 
                    new Object[]{doctor.getId(), patient.getId()});
            if (treatment == null) {
                throw new ExpectedException("Patient is not treated by this doctor");
            }
            
            display.setText(treatment.getReport());
        } 
        catch (ExpectedException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(ex.getMessage());
            alert.show();
            if(lastFocused != null)
                lastFocused.requestFocus();
        }
        catch (Exception ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
        
    }
    
    @FXML
    private void editReport(Event event) {
        try {
            // ensure that a doctor and patient are selected and linked
            Doctor doctor = doctorList.getSelectionModel().getSelectedItem();
            Patient patient = patientList.getSelectionModel().getSelectedItem();
            if (doctor == null || patient == null) {
                throw new ExpectedException("Must select doctor and patient");
            }
            Treatment treatment = ORM.findOne(Treatment.class,
                    "where doctor_id=? and patient_id=?",
                    new Object[]{doctor.getId(), patient.getId()});
            if (treatment == null) {
                throw new ExpectedException("Patient is not treated by this doctor. "
                        + " Select Create -> New Treatment from the menu to create one.");
            }

        // ------------ INVOKE EDIT REPORT BOX --------------//
            URL fxml = getClass().getResource("EditReport.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxml);
            fxmlLoader.load();

            Scene scene = new Scene(fxmlLoader.getRoot());

            Stage dialogStage = new Stage();
            dialogStage.setScene(scene);

            dialogStage.setTitle("Edit Report");

            dialogStage.initModality(Modality.APPLICATION_MODAL);

            dialogStage.show();

            EditReportController dialogController = fxmlLoader.getController();

            dialogController.setMainController(this);

            dialogController.setReportToModify(doctor, patient, treatment);
        // -------- CODE TO INVOKE EDIT REPORT BOX ENDS HERE ------------//
        
        } catch (ExpectedException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(ex.getMessage());
            alert.show();
            if (lastFocused != null) {
                lastFocused.requestFocus();
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }
    
    
    @FXML
    private void removePatient(Event event) {
        try {
            Patient patient = patientList.getSelectionModel().getSelectedItem();
            Collection<Treatment> treatments = ORM.findAll(Treatment.class,
                    "where patient_id=?",
                    new Object[]{patient.getId()}
            );    
            if (patient == null) {
                throw new ExpectedException("must select patient");
            }
            if (!treatments.isEmpty()) {
                for (Treatment treatment : treatments) {
                    ORM.remove(treatment);
                }
            }
            ORM.remove(patient);     
            
            // confirm deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() != ButtonType.OK) {
                return;
            }
            
            // reset list
            patientList.getItems().remove(patient);
            patientList.getSelectionModel().clearSelection();
            patientTreatedByDoctorIds.clear();
            doctorList.getSelectionModel().clearSelection();            
            doctorList.refresh();
            
            // clear the display
            if(patientList == lastFocused)
                display.clear();
        } catch (ExpectedException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(ex.getMessage());
            alert.show();
            if (lastFocused != null) {
                lastFocused.requestFocus();
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }

    }
    
    @FXML
    private void clearAll(Event event) {
        patientList.getSelectionModel().clearSelection();
        doctorList.getSelectionModel().clearSelection();
        doctorTreatingPatientIds.clear();
        patientTreatedByDoctorIds.clear();
        patientList.refresh();
        doctorList.refresh();
        display.setText("");
    }

    @FXML 
    public void sortByName(Event event) {
        try{ 
            patientList.getItems().clear();
            Collection<Patient> patients = ORM.findAll(Patient.class, "order by name");
            for (Patient patient : patients) {
                patientList.getItems().add(patient);
            }
            orderMenu.setText("Name");
            System.out.println("patients ordered by name");
        } catch (Exception ex){
            
        }
            
    }
    
    @FXML
    public void sortByDate(Event event) {
        try {
            patientList.getItems().clear();
            Collection<Patient> patients = ORM.findAll(Patient.class, "order by admitted");
            for (Patient patient : patients) {
                patientList.getItems().add(patient);
                System.out.println("added pat");
            }
            orderMenu.setText("Admit Date");
            System.out.println("patients ordered by admit date");
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
    
    
    @FXML
    private void refocus(Event event) {
        if (lastFocused != null) {
            lastFocused.requestFocus();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            ORM.init(DBProps.getProps());
            
            // load doctor list ordered by name
            Collection<Doctor> doctors = ORM.findAll(Doctor.class, "order by name");
            for (Doctor doctor : doctors) {
                doctorList.getItems().add(doctor);
                System.out.println("added: " + doctor.getName());
            }

            Collection<Patient> patients = ORM.findAll(Patient.class, "order by name");
            for (Patient patient : patients) {
                patientList.getItems().add(patient);
            }

            DoctorCellCallback doctorCellCallback = new DoctorCellCallback();
            doctorList.setCellFactory(doctorCellCallback);

            PatientCellCallback patientCellCallback = new PatientCellCallback();
            patientList.setCellFactory(patientCellCallback);
            
            // this will trigger the red highlighting effect for both lists
            patientCellCallback.setHighlightedIds( doctorTreatingPatientIds );
            doctorCellCallback.setHighlightedIds( patientTreatedByDoctorIds );
            

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }

    }

}
