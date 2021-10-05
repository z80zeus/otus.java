package atm.view;

import atm.Operations;
import atm.state.State;

public interface ViewFactoryInterface {
    ViewInterface createView(Operations operation, Object controller, Object... args);
}
