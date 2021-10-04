package atm.view;

import atm.config.ConfigInterface;

public class ViewAbstractFactory {
    public ViewFactoryInterface createFactory(ConfigInterface cfg) {
        return new ViewFactoryConsole();
    }
}
