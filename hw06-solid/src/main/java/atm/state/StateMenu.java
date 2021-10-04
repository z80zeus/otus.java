package atm.state;

import atm.Controller;
import atm.Operations;
import atm.view.ViewInterface;
import atm.view.ViewMenu;

import java.util.function.Consumer;

public class StateMenu extends State implements Consumer<Operations> {

    public StateMenu(Controller atm_) {
        super(atm_);
        view = new ViewMenu(this);
    }

    @Override
    public void doJob() {
        view.show();
    }

    @Override
    public void accept(Operations operation) {
        view.hide();
        atm.setState(States.createState(operation, atm));
    }

    private final ViewInterface view;
}
