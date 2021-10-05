package atm.state;

import atm.Controller;
import atm.Operations;
import atm.view.ViewInterface;

import java.util.function.Consumer;

public class StateLogin extends State implements Consumer<String> {

    @Override
    public void doJob() {
        view.show();
    }

    @Override
    public void accept(String uuid) {
        view.hide();
        try {
            atm.getAccountService().login(uuid);
        }
        catch (IllegalAccessException e) {
            atm.setState(States.createState(Operations.Error, atm, e.getMessage()));
            return;
        }
        atm.setUuid(uuid);
        atm.setState(States.createState(Operations.Menu, atm));
    }

    protected StateLogin(Controller atm_) {
        super(atm_);
        view = atm.getViewFactory().createView(Operations.Login, this);
    }

    private final ViewInterface view;
}
