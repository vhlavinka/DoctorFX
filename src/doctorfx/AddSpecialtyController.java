
package doctorfx;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.DBProps;
import models.ORM;
import models.Specialty;

public class AddSpecialtyController implements Initializable {
    // private member variables
    private DoctorFXController mainController;
    
    // fxml variables  
    @FXML
    private TextField specialtyEntry;
    
    @FXML
    private TextArea specialtyList;
    
    void setMainController(DoctorFXController mainController) {
        this.mainController = mainController;
    }
    
    
    // add button method
    @FXML
    private void add(Event event) {
        try {
            Specialty newSpecialty = new Specialty(specialtyEntry.getText());
            
            // validations
            Specialty existingSpecialty
                    = ORM.findOne(Specialty.class, "where name=?", new Object[]{newSpecialty.getSpecialtyName()});
            if (existingSpecialty != null) {
                throw new ExpectedException("existing specialty with same name");
            }

            // store the new specialty
            ORM.store(newSpecialty);
            System.out.println("successfully added new specialty");
            
            // close the window
            ((Button) event.getSource()).getScene().getWindow().hide();

        } 
        catch (ExpectedException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(ex.getMessage());
            alert.show();
        } 
        catch (Exception ex) {
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
                specialtyList.setText(specialtyList.getText() + specialty.getSpecialtyName() + "\n" );
            }
        } catch (Exception ex) {

        }

    }
    
}
