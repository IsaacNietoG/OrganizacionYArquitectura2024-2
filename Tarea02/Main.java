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
     * METODO AUXILIAR
     *
     * Obtiene el numero necesario de bits para almacenar el número especificado
     * Utiliza Math.log para obtener el logaritmo base 2 del número, este es el indicador
     * más rápido para obtener los bits necesarios para representar n
     *
     * @param n el número a trabajar
     *
     * @return cantidad de bits necesaria para guardar n
     */
    private static int getArraySize(long n){

        Long N = new Long(n);
        return (int)Math.ceil(Math.log(N.doubleValue())/Math.log(2.0));
    }

    /*
     *  METODO AUXILIAR
     *
     *  Determina si el número dado es par.
     *  Esto lo hace comprobando su bit menos significativo, si está activado
     *  es impar.
     *  Por lo mismo, en ciertas partes de este código se usará también para extraer
     *  directamente el bit menos significativo del número.
     *
     *  @param n el número a trabajar
     *  @return es el número par?
     *
     *  */
    private static boolean isEven(long n){
        return (n & 1) == 0;
    }

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
        if(n == 0){
            return toBoolean("00000000");
        }

        boolean[] result = new boolean[getArraySize(n)];

        int i = result.length-1;
        while(n != 0){
            result[i] = !isEven(n);
            n/=2;
        }

        return result;
    }

    /*
     * METODO AUXILIAR
     *
     * Azucar sintáctica para hacer un XOR exclusivo más legible en otras secciones
     * del código.
     *
     * @param a
     * @param b
     * @return a XOR b
     */
    private static boolean XOR(boolean a, boolean b){
        return (a || b) && (!a || !b);
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

        //Usaré el boolean overflow también como espacio para carrys.
        boolean overflow = false;
        boolean[] result = new boolean[8];

        for(int i = a.length-1; i>=0; i--){

            //Aqui se suman los tres bits considerados, incluyendo el carry
            //aunque este último no será consumido realmente hasta el siguiente paso
            //Se usa la estructura a XOR b XOR overflow/carry
            result[i] = XOR(XOR(a[i], b[i]), overflow);

            //Si ambos bits están activados entonces habilitamos el carry
            //de la misma manera en que si no lo están, esto emula el consumir
            //un posible carry previo
            //También si el overflow está cargado pero alguno de los dos bits
            //no lo está, se considera que no se vuelve a consumir
            overflow = a[i] && b[i] || overflow && (a[i] || b[i]);

        }

        //Redefiniendo overflow como lo q es originalmente
        //
        //De esta manera detecta si hubo cambio de signo donde no debió de haber habido.
        //
        //Casos de overflow:
        //Negativo + Negativo = Positivo
        //Positivo + Positivo = Negativo
        overflow = a[0] && b[0] && !result[0];

        if (overflow) {
            throw new ArithmeticException("Overflow detectado en la suma de enteros con complemento a dos.");
        }
        
        return result;
    }

    /*
     * METODO AUXILIAR
     *
     * Realiza el algoritmo de suma sin más ni menos, tira la excepción overflow si
     * se conserva el carry al finalizar el algoritmo
     *
     * @param a
     * @param b
     * @return a + b en operación unsigned
     */
    private static boolean[] unsignedSum(boolean[] a, boolean[] b){
        if(a.length != b.length){
            throw new IllegalArgumentException("Suma de numeros de longitud distinta no implementada");
        }

        boolean carry = false;
        int longitud = a.length - 1;
        boolean[] resultado = new boolean[longitud+1];

        for(int i = longitud; i >= 0; i--){
            //Sumador clásico

            resultado[i] = XOR(XOR(a[i], b[i]), carry);

            carry = (a[i] && b[i]) || carry && (a[i] || b[i]);
        }

        if(carry){
            throw new ArithmeticException("Overflow detectado");
        }

        return resultado;
    }

    private static boolean[] getBiggerNumber(boolean[] a, boolean[] b){
        int tamanioMasCorto = a.length >= b.length ? a.length : b.length;
        boolean[] result = a;

        for(int i = 0; i < tamanioMasCorto; i++){
            if(a[i] != b[i]){
                result = a[i] && !b[i] ? a : b;
            }
        }

        return result;
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
        boolean signoA = a[0];
        boolean signoB = b[0];
        boolean[] result = new boolean[8];



        if(signoA == signoB){
            //Signos iguales entonces realizamos la suma sin bronca
            boolean[] subA = new boolean[7];
            boolean[] subB = new boolean[7];
            System.arraycopy(a, 1, subA, 0, 7);
            System.arraycopy(b, 1, subB, 0, 7);
            System.arraycopy(unsignedSum(subA, subB),
                             0, result, 1, 7);

            //Solo hay que considerar cambiar el signo para que sea acorde
            result[0] = signoA;

        }else{
            //Signos distintos, primero hay que determinar cuál es el número más grande y cuál el más chico, para
            //obtener la diferencia.
            boolean[] magnitudA = new boolean[7];
            boolean[] magnitudB = new boolean[7];
            System.arraycopy(a, 1, magnitudA, 0, 7);
            System.arraycopy(b, 1, magnitudB, 0, 7);

            boolean[] numeroGrande = getBiggerNumber(magnitudA, magnitudB);
            boolean[] numeroChico = numeroGrande == magnitudA? magnitudB : magnitudA;



        }

        if (overflow) {
            throw new ArithmeticException("Overflow detectado en la suma de enteros con bit de signo.");
        }
        
        return result;
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
