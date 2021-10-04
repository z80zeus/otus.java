package atm.state;

import atm.Controller;
import atm.Operations;
import atm.view.ViewInterface;

import java.util.function.Consumer;

public class StateLogout extends State implements Consumer<Integer> {

    protected StateLogout(Controller atm) {
        super(atm);
        view = atm.getViewFactory().createView(Operations.Logout, this);
    }

    @Override
    public void doJob() {
        view.show();
    }

    @Override
    public void accept(Integer integer) {
        view.hide();
        atm.setUuid(null);
        atm.setState(States.createState(Operations.Login, atm));
    }

    private final ViewInterface view;
}
