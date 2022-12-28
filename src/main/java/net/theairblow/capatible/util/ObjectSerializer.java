package net.theairblow.capatible.util;

import java.io.*;
import java.util.Base64;

public class ObjectSerializer {
    /** Read the object from Base64 string. */
    public static <T extends Serializable> T fromString(String s)
            throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data ));
        Object o = ois.readObject();
        ois.close();
        return (T)o;
    }

    /** Write the object to a Base64 string. */
    public static String toString(Serializable o) throws IOException {
        ByteArrayOutputStream arr = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(arr);
        out.writeObject(o); out.close();
        return Base64.getEncoder().encodeToString(arr.toByteArray());
    }
}
