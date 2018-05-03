package imat;

import imat.interfaces.IFXMLController;
import javafx.fxml.Initializable;

public abstract class FXMLController implements Initializable, IFXMLController {
    protected Model model;
    @Override
    public void setModel(Model m) {
        System.out.println("Setting model!");
        this.model = m;
    }
}
