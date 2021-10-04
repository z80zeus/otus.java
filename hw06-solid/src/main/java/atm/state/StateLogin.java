package atm.state;

import atm.Controller;
import atm.Operations;
import atm.view.ViewInterface;

import java.util.function.Consumer;

public class StateLogin extends State implements Consumer<String> {

    public StateLogin(Controller atm_) {
        super(atm_);
        view = atm.getViewFactory().createView(Operations.Login, this);
    }

    @Override
    public void doJob() {
        view.show();
    }

    @Override
    public void accept(String uuid) {
        view.hide();
        atm.setUuid(uuid);
        atm.setState(States.createState(Operations.Menu, atm));
    }

    private final ViewInterface view;
}
