package org.asyou.sequoia.model;

import org.asyou.sequoia.common.Assertions;
import org.bson.BSONObject;

public final class BSONField {
    private final String name;
    private final BSONObject value;

    public BSONField(String name, BSONObject value) {
        this.name = (String) Assertions.notNull("name", name);
        this.value = (BSONObject)Assertions.notNull("value", value);
    }

    public String getName() {
        return this.name;
    }

    public BSONObject getValue() {
        return this.value;
    }

    public String toString() {
        return "Field{name='" + this.name + '\'' + ", value=" + this.value + '}';
    }
}
