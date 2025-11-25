import java.security.*;
import javax.crypto.Cipher;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class RSA {
    public static void main(String[] args) throws Exception {

        // Generar claves 
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();
        PublicKey publicKey = pair.getPublic();
        PrivateKey privateKey = pair.getPrivate();

        // Guardar en archivos
        Files.write(Paths.get("clave.pub"), publicKey.getEncoded());
        Files.write(Paths.get("clave.key"), privateKey.getEncoded());

        // Pedir mensaje 
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce el mensaje a cifrar: ");
        String mensaje = sc.nextLine();
        
        // Cifrar mensaje con la clave pública
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cifrado = cipher.doFinal(mensaje.getBytes());

        // Guardar mensaje cifrado 
        Files.write(Paths.get("mensaje.cifrado"), cifrado);
        System.out.println("Mensaje cifrado y guardado en mensaje.cifrado");

        // Leer mensaje cifrado
        byte[] cifradoLeido = Files.readAllBytes(Paths.get("mensaje.cifrado"));
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] descifrado = cipher.doFinal(cifradoLeido);

        System.out.println("Mensaje descifrado: " + new String(descifrado));
    }
}
