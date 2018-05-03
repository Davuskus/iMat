package imat.utils;

import javafx.util.Callback;

public class ControllerFactoryGate implements Callback<Class<?>, Object> {

    private final Callback<Class<?>, Object> rootFactoryMethod;
    private final Callback<Class<?>, Object> childrenFactoryMethod;
    private boolean isFirst = true;

    public ControllerFactoryGate(Callback<Class<?>, Object> rootFactoryMethod, Callback<Class<?>, Object> childrenFactoryMethod) {
        this.rootFactoryMethod = rootFactoryMethod;
        this.childrenFactoryMethod = childrenFactoryMethod;
    };

    @Override
    public Object call(Class<?> type) {
        if(isFirst) {
            isFirst = false;
            return rootFactoryMethod.call(type);
        }
        else return childrenFactoryMethod.call(type);
    }
}
