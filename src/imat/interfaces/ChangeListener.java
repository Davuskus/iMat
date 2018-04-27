package imat.interfaces;

public interface ChangeListener<T> {

    void onChange(T oldValue, T newValue);

}
