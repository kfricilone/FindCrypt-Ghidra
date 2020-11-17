package me.kfricilone;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Kyle Fricilone on Nov 17, 2020.
 */
public class CryptEntry {

    public final String name;
    public final int size;
    public final byte[] buffer;

    public CryptEntry(String name, int size, byte[] buffer) {
        this.name = name;
        this.size = size;
        this.buffer = buffer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CryptEntry that = (CryptEntry) o;
        return size == that.size &&
                Objects.equals(name, that.name) &&
                Arrays.equals(buffer, that.buffer);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, size);
        result = 31 * result + Arrays.hashCode(buffer);
        return result;
    }

    @Override
    public String toString() {
        return "CryptEntry(name=" + name +", size=" + size + ')';
    }
}
