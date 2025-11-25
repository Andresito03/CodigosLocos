import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Arrays;
import java.util.Scanner;

public class HA256W {

    private static final String PASSWORD_CORRECTA = "123456";

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce la contraseña: ");
        String intentoUsuario = sc.nextLine();

        // Generar salt y hash para la contraseña correcta
        String[] credencialesAlmacenadas = generateSaltAndHash(PASSWORD_CORRECTA);
        String saltAlmacenado = credencialesAlmacenadas[0];
        String hashAlmacenado = credencialesAlmacenadas[1];

        System.out.println("Datos almacenados");
        System.out.println("Salt almacenado: " + saltAlmacenado);
        System.out.println("Hash almacenado: " + hashAlmacenado);

        // Generar salt y hash para la contraseña introducida por el usuario
        String[] credencialesUsuario = generateSaltAndHash(intentoUsuario);
        String saltUsuario = credencialesUsuario[0];
        String hashUsuario = credencialesUsuario[1];

        System.out.println("Datos intento del usuario");
        System.out.println("Salt del usuario: " + saltUsuario);
        System.out.println("Hash del usuario: " + hashUsuario);

        // Verificación usando el salt almacenado
        boolean verificacion = verificarPassword(intentoUsuario, saltAlmacenado, hashAlmacenado);

        System.out.println("Verificación ");
        System.out.println("es correcta?: " + verificacion);
    }

    public static String[] generateSaltAndHash(String password) throws Exception {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashBytes = md.digest(password.getBytes());

        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String encodedHash = Base64.getEncoder().encodeToString(hashBytes);

        return new String[]{encodedSalt, encodedHash};
    }

    public static boolean verificarPassword(String passwordAttempt, String storedSalt, String storedHash) throws Exception {
        byte[] salt = Base64.getDecoder().decode(storedSalt);
        byte[] hash = Base64.getDecoder().decode(storedHash);

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] attemptHash = md.digest(passwordAttempt.getBytes());

        return Arrays.equals(hash, attemptHash);
    }
}
