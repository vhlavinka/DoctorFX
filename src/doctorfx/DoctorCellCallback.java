package doctorfx;
 
import java.util.Collection;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import models.Doctor;

class DoctorCellCallback implements Callback<ListView<Doctor>, ListCell<Doctor>> {  
    
  private Collection<Integer> highlightedIds = null;
 
  void setHighlightedIds(Collection<Integer> highlightedIds) {
    this.highlightedIds = highlightedIds;
  }    
    
  @Override
  public ListCell<Doctor> call(ListView<Doctor> p) {
    ListCell<Doctor> cell = new ListCell<Doctor>() {
      @Override
      protected void updateItem(Doctor doctor, boolean empty) {
        super.updateItem(doctor, empty);
        if (empty) {
          this.setText(null);
          return;
        }
        this.setText(doctor.getName());
 
        if (highlightedIds == null) {
          return;
        }
 
        String css = ""
            + "-fx-text-fill: #c00;"
            + "-fx-font-weight: bold;"
            + "-fx-font-style: italic;"
            ;
 
        if (highlightedIds.contains(doctor.getId())) {
          //System.out.println(doctor.getId());
          this.setStyle(css);
        }
        else {
          this.setStyle(null);
        }
      }
    };
    return cell;
  }
}
