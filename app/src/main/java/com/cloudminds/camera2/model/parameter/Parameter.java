package com.cloudminds.camera2.model.parameter;

public class Parameter<T> {
    public T getParameter() {
        return parameter;
    }

    public void setParameter(T parameter) {
        this.parameter = parameter;
    }

    private T parameter;
}
