package com.hotellook.common.reflection;

public final class TypedObject {
    private final Object object;
    private final Class type;

    public TypedObject(Object object, Class type) {
        this.object = object;
        this.type = type;
    }

    Object getObject() {
        return this.object;
    }

    Class getType() {
        return this.type;
    }
}
