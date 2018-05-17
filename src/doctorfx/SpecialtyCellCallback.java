package doctorfx;
 
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import models.Specialty;

class SpecialtyCellCallback implements Callback<ListView<Specialty>, ListCell<Specialty>> {  
  @Override
  public ListCell<Specialty> call(ListView<Specialty> p) {
    ListCell<Specialty> cell = new ListCell<Specialty>() {
      @Override
      protected void updateItem(Specialty specialty, boolean empty) {
        super.updateItem(specialty, empty);
        if (empty) {
          this.setText(null);
          return;
        }
        this.setText(specialty.getSpecialtyName() );

      }
    };
    return cell;
  }
}