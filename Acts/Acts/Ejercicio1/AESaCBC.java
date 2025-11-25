import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class AESaCBC {
    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);
        System.out.print("Introduce un texto a cifrar: ");
        String texto = in.nextLine();

        String clave = "1234567890123456"; 
        String iv = "abcdef9876543210";  
        System.out.println("Clave de cifrado: "+clave);


        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(clave.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());

        // Cifrar
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] cifrado = cipher.doFinal(texto.getBytes());
        String cifradoBase64 = Base64.getEncoder().encodeToString(cifrado);
        System.out.println("Cifrado (Base64): " + cifradoBase64);

        // Descifrar
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] descifrado = cipher.doFinal(cifrado);
        System.out.println("Descifrado: " + new String(descifrado));

    }
}
