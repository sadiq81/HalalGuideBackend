package dk.eazyit.halalguide.util;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Privat
 * Date: 11/02/16
 * Time: 15.39
 * To change this template use File | Settings | File Templates.
 */
public class IdGenerator {

    public static String createId() {
        UUID uuid = java.util.UUID.randomUUID();
        return uuid.toString();
    }
}
