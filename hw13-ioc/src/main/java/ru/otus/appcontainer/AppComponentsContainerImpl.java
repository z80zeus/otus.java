package ru.otus.appcontainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final Logger logger = LoggerFactory.getLogger(AppComponentsContainerImpl.class);
    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final Map<Class<?>, Object> appComponentsByClass = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        try {
            var config = configClass.getDeclaredConstructor().newInstance();
            var methods = Arrays.stream(configClass.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(AppComponent.class))
                    .sorted(Comparator.comparingInt(method -> method.getDeclaredAnnotation(AppComponent.class).order())).toList();

            for (var method: methods) {
                var methodResultType = method.getReturnType();
                var methodResultName = method.getDeclaredAnnotation(AppComponent.class).name();
                var methodParams = Arrays.stream(method.getParameters())
                        .map(Parameter::getType)
                        .map(appComponentsByClass::get)
                        .toArray();
                var result = method.invoke(config, methodParams);
                appComponentsByName.put(methodResultName, result);
                appComponentsByClass.put(methodResultType, result);
                appComponentsByClass.put(result.getClass(), result);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error(e.getMessage());
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        //noinspection unchecked
        return (C)appComponentsByClass.get(componentClass);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        //noinspection unchecked
        return (C)appComponentsByName.get(componentName);
    }
}
