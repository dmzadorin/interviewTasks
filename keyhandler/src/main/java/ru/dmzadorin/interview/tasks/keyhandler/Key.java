package ru.dmzadorin.interview.tasks.keyhandler;

import java.util.Objects;

/**
 * Simple value class with final fields: id and name
 * and correct equals() and hashCode() defined.
 * Created by @dmzadorin on 04.08.16.
 */
public class Key {
    private final int id;

    private final String name;

    public Key(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key key = (Key) o;
        return id == key.id && Objects.equals(name, key.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Key{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
