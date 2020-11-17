package me.kfricilone;

import docking.widgets.dialogs.MultiLineMessageDialog;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Created by Kyle Fricilone on Nov 16, 2020.
 */
public class DatabaseParser {

    private static final int MAGIC = 0xD3010401;

    public static List<CryptEntry> parse(File file) {
        var entries = new ArrayList<CryptEntry>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {

            var magic = dis.readInt();
            if (magic != MAGIC) throw new IOException("Specified database file has unexpected magic.");

            var count = dis.readShort();
            if (count < 1) throw new IOException("Specified database contains no entries.");

            for (var i = 0; i < count; i++) {
                var name = read(dis);
                var compressed = dis.readBoolean();
                var size = dis.readInt();
                var data = read(dis);

                byte[] buffer;
                if (compressed) {
                    try (GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(data));
                         ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                        gis.transferTo(baos);
                        buffer = baos.toByteArray();
                    }
                } else {
                    buffer = data;
                }

                entries.add(new CryptEntry(new String(name, StandardCharsets.UTF_8), size, buffer));
            }
        } catch (IOException e) {
            MultiLineMessageDialog.showMessageDialog(null, "FindCrypt - Error" , "An error happened while loading the database.", e.getMessage(), 0);
        }

        return entries;
    }

    private static byte[] read(DataInputStream dis) throws IOException {
        var size = dis.readInt();
        var data = new byte[size];
        dis.readFully(data);

        return data;
    }

}
