package me.kfricilone;

import ghidra.app.tablechooser.AddressableRowObject;
import ghidra.program.model.address.Address;
import ghidra.program.model.address.AddressSet;
import ghidra.program.model.listing.Function;

import java.util.Objects;

/**
 * Created by Kyle Fricilone on Nov 16, 2020.
 */
public class CryptMatch implements AddressableRowObject {

    public final CryptEntry entry;
    public final String func;
    public final AddressSet addresses;
    public final double ratio;

    public CryptMatch(CryptEntry entry, String func, AddressSet addresses, double ratio) {
        this.entry = entry;
        this.func = func;
        this.addresses = addresses;
        this.ratio = ratio;
    }

    @Override
    public Address getAddress() {
        return addresses.getMinAddress();
    }

    @Override
    public String toString() {
        return String.format("%s (%s) -> %s", entry.name, func, addresses.getMinAddress().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CryptMatch that = (CryptMatch) o;
        return Double.compare(that.ratio, ratio) == 0 &&
                Objects.equals(entry, that.entry) &&
                Objects.equals(func, that.func) &&
                Objects.equals(addresses, that.addresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entry, func, addresses, ratio);
    }

}
