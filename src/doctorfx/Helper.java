package doctorfx;
 
import models.Doctor;
import models.Patient;
import models.Treatment;
 
public class Helper {
  public static String info(Doctor doctor) {
      
    return String.format(
        "id: %s\n"
        + "name: %s\n"
        + "specialty: %s\n",
        doctor.getId(),
        doctor.getName(),
        doctor.getSpecialty()
    );
  }
 
  public static String info(Patient patient) {
    return String.format(
        "id: %s\n"
        + "name: %s\n"
        + "admitted: %s\n",
        patient.getId(),
        patient.getPatName(),
        patient.getAdmitDate()
    );
  }
  
    public static String info(Treatment treatment) {
    return String.format(
        "%s\n",
        treatment.getReport()
    );
  }
 
  public static java.sql.Date currentDate() {
    long now = new java.util.Date().getTime();
    java.sql.Date date = new java.sql.Date(now);
    return date;
  }
}
