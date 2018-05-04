package imat;

import imat.interfaces.IFXMLController;
import javafx.fxml.Initializable;

public abstract class FXMLController implements Initializable, IFXMLController {

    protected Model model;

    @Override
    public void setModel(Model m) {
        this.model = m;
    }

}
