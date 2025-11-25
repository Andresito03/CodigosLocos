import java.io.*;
import java.nio.charset.Charset;
import java.util.Scanner;

public class CharsetEncoding {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in); 

        System.out.print("Introduce un texto: ");
        String texto = sc.nextLine();

        // Imprimir 
        System.out.println("ASCII: " + new String(texto.getBytes("US-ASCII"), "US-ASCII"));
        System.out.println("UTF-8: " + new String(texto.getBytes("UTF-8"), "UTF-8"));
        System.out.println("UTF-16: " + new String(texto.getBytes("UTF-16"), "UTF-16"));
        System.out.println("UTF-32: " + new String(texto.getBytes("UTF-32"), "UTF-32")); 

        // Guardar en archivos 
        guardarEnArchivo("texto_ascii.txt", texto, "US-ASCII");
        guardarEnArchivo("texto_utf8.txt", texto, "UTF-8");
        guardarEnArchivo("texto_utf16.txt", texto, "UTF-16");
        guardarEnArchivo("texto_utf32.txt", texto, "UTF-32");

        System.out.println("Archivos guardados con las codificaciones correspondientes.");
    }

    public static void guardarEnArchivo(String nombreArchivo, String texto, String encoding) throws Exception {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(nombreArchivo), Charset.forName(encoding))) {
            writer.write(texto);
        }
    }
}
