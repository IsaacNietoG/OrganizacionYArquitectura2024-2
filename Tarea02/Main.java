/* Tarea02:
 * Implementar las funciones convertToBinary, signBitSum y a2Sum.
 * 
 * Los bits se codifican como arreglos de valores booleanos, en donde true
 * representa 1 y false representa 0; el bit más significativo corresponde a la 
 * posición 0 del arreglo: 10001100
 *                         ^
 * Mientras que la última posición del arreglo corresponde al bit más
 * significativo: 10001100
 *                       ^
 * Por ejemplo:
 * 10001100 representa 140 en su representación de entero sin signo.
 * 10001100 representa -12 en su representación de entero con bit de signo.
 * 10001100 representa -116 en su representaicón de entero con complemento a2.
 */
public class Main
{
    /*
     * Convierte un entero en su representación binaria como número entero sin signo.
     * La salida de esta función debe ser un arreglo de booleanos del mínimo tamaño
     * posible para poder representar el entero, es decir que si la entrada de
     * la función es 3, la salida debe ser el arreglo {true, true}, en cambio si
     * la entrada de la función es 124.
     * La función no acepta entradas negativas.
     */
    public static boolean[] convertToBinary(long n) {
        if(n < 0) {
            throw new IllegalArgumentException("No se aceptan entradas negativas.");
        }
        
        return toBoolean("00000000");
    }
    
    /* 
     * Suma dos arreglos de tamaño 8 de booleanos considerando
     * que representan un número entero en su representación
     * de complemento a2.
     * Si ocurre un desbordamiento se mandará una excepción.
     */
    public static boolean[] a2Sum(boolean[] a, boolean[] b) {
        if (a.length != 8 || b.length != 8) {
            throw new IllegalArgumentException("Los arreglos deben tener tamaño 8.");
        }
        
        boolean overflow = false;

        /* Inserta tu código */
        
        if (overflow) {
            throw new ArithmeticException("Overflow detectado en la suma de enteros con complemento a dos.");
        }
        
        return toBoolean("00000000");
    }
    
    /* 
     * Suma dos arreglos de tamaño 8 de booleanos considerando
     * que representan un número entero en su representación
     * de complemento a2.
     * Si ocurre un desbordamiento se mandará una excepción.
     */
    public static boolean[] signBitSum(boolean[] a, boolean[] b) {
        if (a.length != 8 || b.length != 8) {
            throw new IllegalArgumentException("Los arreglos deben tener tamaño 8.");
        }
        
        boolean overflow = false;

        /* Inserta tu código */
        
        if (overflow) {
            throw new ArithmeticException("Overflow detectado en la suma de enteros con bit de signo.");
        }
        
        return toBoolean("00000000");
    }
    
    /* Convierte un arreglo de booleanos en una cadena de 1'0 y 0's
     * en donde cada true será un '1' y cada false un '0'.*/
    public static String toString(boolean[] bits) {
        String str = "";
        for(int i=0; i<bits.length; i++)
            str = str + (bits[i] ? "1" : "0");
        return str;
    }
    
    /* Convierte una cadena de 1's y 0's en un arreglo de booleanos
     * en donde cada '1' será true y cada '0' será false. Si encuentra
     * algo que no es '1' ni '0' lo considera como false.*/
    public static boolean[] toBoolean(String bits) {
        boolean[] bool = new boolean[8];
        for(int i=0; i<bits.length(); i++)
            bool[i] = (bits.charAt(i) == '1') ? true : false;
        return bool;
    }
    
    /* Algunos ejemplos de las sumas para corroborar que funcionen los algoritmos.*/
    public static void main(String[] args) {
        
        // Ejemplos de prueba para convertToBinary
        System.out.println("\n--- Ejemplos de prueba para convertToBinary ---");
        System.out.println("\nEntrada: 3\nEsperado:  11\nResultado: " + toString(convertToBinary(3)));
        System.out.println("\nEntrada: 124\nEsperado:  1111100\nResultado: " + toString(convertToBinary(124)));
        System.out.println("\nEntrada: 255\nEsperado:  11111111\nResultado: " + toString(convertToBinary(255)));
        System.out.println("\nEntrada: 4096\nEsperado: 1000000000000\nResultado: " + toString(convertToBinary(4096)));
        System.out.println("\nEntrada: 1048576\nEsperado:  100000000000000000000\nResultado: " + toString(convertToBinary(1048576)));
        
        boolean[] num1 = new boolean[8];
        boolean[] num2 = new boolean[8];
        String r = "";
        
        System.out.println("\n--- Ejemplos de prueba para a2Sum ---");
        
        num1 = toBoolean("01111111");
        num2 = toBoolean("00000001");
        try {
            r = toString(a2Sum(num1, num2));
        } catch(ArithmeticException e) {
            r = "Excepción por desbordamiento";
        }
        System.out.println("\nEntrada: 01111111 + 00000001");
        System.out.println("Esperado: Excepción por desbordamiento");
        System.out.println("Obtenido: " + r);
        
        num1 = toBoolean("10000001");
        num2 = toBoolean("00000001");
        r = toString(a2Sum(num1, num2));
        System.out.println("\nEntrada: 10000001 + 00000001");
        System.out.println("Esperado: 10000010");
        System.out.println("Obtenido: " + r);
        
        num1 = toBoolean("00001111");
        num2 = toBoolean("00000001");
        r = toString(a2Sum(num1, num2));
        System.out.println("\nEntrada: 00001111 + 00000001");
        System.out.println("Esperado: 00010000");
        System.out.println("Obtenido: " + r);
        
        num1 = toBoolean("11110000");
        num2 = toBoolean("00010000");
        r = toString(a2Sum(num1, num2));
        System.out.println("\nEntrada: 11110000 + 00010000");
        System.out.println("Esperado: 00000000");
        System.out.println("Obtenido: " + r);
        
        
        System.out.println("\n--- Ejemplos de prueba para signBitSum ---");
        
        num1 = toBoolean("11111000");
        num2 = toBoolean("11101001");
        try {
            r = toString(signBitSum(num1, num2));
        } catch(ArithmeticException e) {
            r = "Excepción por desbordamiento";
        }
        System.out.println("\nEntrada: 11111000 + 11101001");
        System.out.println("Esperado: Excepción por desbordamiento");
        System.out.println("Obtenido: " + r);
        
        num1 = toBoolean("10000011");
        num2 = toBoolean("00000001");
        r = toString(signBitSum(num1, num2));
        System.out.println("\nEntrada: 10000011 + 00000001");
        System.out.println("Esperado: 10000010");
        System.out.println("Obtenido: " + r);
        
        num1 = toBoolean("00001111");
        num2 = toBoolean("10001111");
        r = toString(signBitSum(num1, num2));
        System.out.println("\nEntrada: 00001111 + 10001111");
        System.out.println("Esperado: 00000000");
        System.out.println("Obtenido: " + r);
        
        num1 = toBoolean("01111111");
        num2 = toBoolean("01111111");
        try {
            r = toString(signBitSum(num1, num2));
        } catch(ArithmeticException e) {
            r = "Excepción por desbordamiento";
        }
        System.out.println("\nEntrada: 01111111 + 01111111");
        System.out.println("Esperado: Excepción por desbordamiento");
        System.out.println("Obtenido: " + r);

    }
}