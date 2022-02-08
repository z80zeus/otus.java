package ru.otus.appcontainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final Logger logger = LoggerFactory.getLogger(AppComponentsContainerImpl.class);
    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final Map<Class<?>, List<Object>> appComponentsByClass = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        try {
            final var config = configClass.getDeclaredConstructor().newInstance();
            final var methods = Arrays.stream(configClass.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(AppComponent.class))
                    .sorted(Comparator.comparingInt(method -> method.getDeclaredAnnotation(AppComponent.class).order())).toList();

            for (var method: methods) {
                var newComponentName = method.getDeclaredAnnotation(AppComponent.class).name();
                if (appComponentsByName.containsKey(newComponentName))
                    throw new InstantiationException("Component " + newComponentName + " is already instantiated");

                // Все типы, которые может возвращать метод: и заявленный интерфейс, и реальный класс и его
                // необъявленные в методе интерфейсы.
                final List<Class<?>> newComponentTypes = new ArrayList<>();

                // Объявленный возвращаемый тип.
                final var methodResultType = method.getReturnType();
                newComponentTypes.add(methodResultType);

                // Добавить все интерфейсы возвращаемого типа.
                newComponentTypes.addAll(Arrays.asList(methodResultType.getInterfaces()));

                // Подготовка параметров вызова метода и получение результата.
                var methodParams = Arrays.stream(method.getParameters())
                        .map(Parameter::getType)
                        .map(clazz -> appComponentsByClass.get(clazz).get(0))
                        .toArray();
                final var newComponent = method.invoke(config, methodParams);

                appComponentsByName.put(newComponentName, newComponent);

                // Добавить в список типов нового компонента РЕАЛЬНЫЙ тип компонента, который вернул метод.
                newComponentTypes.add(newComponent.getClass());

                // Добавить в список все ИНТЕРФЕЙСЫ реального типа, который вернул метод.
                newComponentTypes.addAll(Arrays.asList(newComponent.getClass().getInterfaces()));

                // Записать новый компонент под всеми выявленными интерфейсами.
                List<Object> componentsByType;
                for (final var clazz: newComponentTypes) {
                    if (!appComponentsByClass.containsKey(clazz)) {
                        (componentsByType = new ArrayList<>()).add(newComponent);
                        appComponentsByClass.put(clazz, componentsByType);
                        continue;
                    }

                    componentsByType = appComponentsByClass.get(clazz);
                    if (!componentsByType.contains(newComponent)) {
                        componentsByType.add(newComponent);
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
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
        return (C)appComponentsByClass.get(componentClass).get(0);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        //noinspection unchecked
        return (C)appComponentsByName.get(componentName);
    }
}
