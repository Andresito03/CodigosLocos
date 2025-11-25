import java.security.*;
import java.util.Base64;
import java.io.*;
import java.util.Scanner;
import java.security.spec.X509EncodedKeySpec;

public class FirmaDigital {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        File mensajeFile = new File("mensaje.txt");
        File firmaFile = new File("firma.txt");
        File claveFile = new File("clave_publica.txt");

        // Si los archivos existen, verificar la firma  
        // sino, crear el mensaje, la firma y la clave pública

        if (mensajeFile.exists() && firmaFile.exists() && claveFile.exists()) {
        
            byte[] mensajeLeido = leerArchivo("mensaje.txt");
            byte[] firmaLeida = Base64.getDecoder().decode(leerArchivo("firma.txt"));
            byte[] clavePublicaBytes = Base64.getDecoder().decode(leerArchivo("clave_publica.txt"));

            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey clavePublica = kf.generatePublic(new X509EncodedKeySpec(clavePublicaBytes));

            Signature verificador = Signature.getInstance("SHA256withRSA");
            verificador.initVerify(clavePublica);
            verificador.update(mensajeLeido);

            boolean esValida = verificador.verify(firmaLeida);

            if (esValida) {
                System.out.println("Firma válida. El mensaje es:");
                System.out.println(new String(mensajeLeido));
            } else {
                System.out.println("Firma inválida. No se puede mostrar el mensaje.");
            }

        } else {
        
            System.out.print("Introduce el mensaje a firmar: ");
            String mensaje = sc.nextLine();
            byte[] datos = mensaje.getBytes();

            // Generar par de claves RSA
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();

            // Crear firma digital
            Signature firma = Signature.getInstance("SHA256withRSA");
            firma.initSign(privateKey);
            firma.update(datos);
            byte[] firmaBytes = firma.sign();

            // Guardar mensaje, firma y clave pública en archivos
            try (FileOutputStream fos = new FileOutputStream("mensaje.txt")) {
                fos.write(datos);
            }
            try (FileOutputStream fos = new FileOutputStream("firma.txt")) {
                fos.write(Base64.getEncoder().encode(firmaBytes));
            }
            try (FileOutputStream fos = new FileOutputStream("clave_publica.txt")) {
                fos.write(Base64.getEncoder().encode(publicKey.getEncoded()));
            }

            System.out.println("Se han creado los archivos mensaje.txt, firma.txt y clave_publica.txt");
            System.out.println("Ejecuta de nuevo el programa para verificar la firma y mostrar el mensaje.");
        }
    }

    public static byte[] leerArchivo(String nombreArchivo) throws IOException {
        File file = new File(nombreArchivo);
        byte[] datos = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(datos);
        }
        return datos;
    }
}
