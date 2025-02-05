import java.util.Arrays;
import java.util.Scanner;

/**
 * Un objeto de esta clase guarda en un array las diferentes
 * palabras que hay en un texto
 *
 * Cada palabra es un objeto Palabra que guarda la palabra (como String)
 * y su frecuencia de aparici�n en el texto
 *
 * El array guarda como m�ximo n palabras distintas
 *
 *
 */
public class Texto {

    private Palabra[] palabras;
    private int total;

    /**
     * Constructor
     * Crea el array al tama�o n
     * e inicializa adecuadamente el resto de atributos
     */
    public Texto(int n) {
        palabras = new Palabra[n];
    }

    /**
     *
     * @return true si el texto est� completo
     */
    public boolean textoCompleto() {
        return total == palabras.length;
    }

    /**
     *
     * @return el n� de palabras distintas aparecidas en
     * el texto y guardadas en el array
     */
    public int totalPalabras() {
        return total;
    }

    /**
     * Dada una l�nea de texto conteniendo diferentes palabras
     * el m�todo extre las palabras y las inserta en el array
     *
     * Las palabras en la l�nea est�n separadas por uno o varios espacios
     * seguidos, o por el punto o por la coma
     * Puede haber espacios al comienzo y final de la l�nea
     *
     * Ej - "   a single      type.  " contiene tres palabras: a single type
     *      "y un mozo de campo y plaza  "  contiene 7 palabras
     *
     * Antes de insertar una palabra habr� que comprobar que no se
     * ha a�adido previamente. 
     * Si ya se ha a�adido solo hay que incrementar su frecuencia de
     * aparici�n 
     * Si no est� la palabra y hay sitio en el array para una nueva
     * se inserta de forma que quede ordenada (!!no se ordena, se
     * inserta en orden!!)
     *  
     * Hay que usar como m�todos de ayuda estaPalabra() e
     * insertarPalabraEnOrden()
     */
    public void addPalabras(String linea) {
        String[] i = linea.trim().split("[.\\,\\s]+");
        for(int j = 0; j < i.length; j++)   {
            if(estaPalabra(i[j]) >= 0)    {
                palabras[estaPalabra(i[j])].incrementar();
            }
            else if (!textoCompleto())  {
                insertarPalabraEnOrden(i[j]);
            }
        }
    }

    /**
     *  dada una palabra devuelve la posici�n en la que se
     *  encuentra en el array o -1 si no est�
     *
     *  Indiferente may�sculas y min�sculas
     */
    public int estaPalabra(String palabra) {    
        // Bucle para recorrer el Array
        for(int i = 0; i < total; i++){
            // Comapara la palabra de la posicion sin importar mayusculas o minusculas
            if(palabras[i].getPalabra().equalsIgnoreCase(palabra)){
                // Si esta la palabra devuelve su posicio
                return i;
            }
        }
        // Si no esta la palabra develve el -1
        return -1;
    }

    /**
     *
     * @param palabra inserta la palabra en el lugar adecuado
     *                de forma que el array palabras quede ordenado
     *                alfab�ticamente
     *  Solo hay que insertar en este m�todo, se asume que la palabra
     *                no est� y que es posible a�adirla
     *
     */
    private void insertarPalabraEnOrden(String palabra) {
        if (estaPalabra(palabra) == -1 && !textoCompleto()) {
            int aux = total - 1;
            while (aux >= 0 && palabras[aux].getPalabra().compareToIgnoreCase(palabra) > 0) {
                palabras[aux + 1] = palabras[aux];
                aux--;
            }
            palabras[aux + 1] = new Palabra(palabra);
            total++;
        }
    }

    /**
     * Representaci�n textual del array de palabras
     * Cada palabra y su frecuencia de aparici�n
     * Se muestran en l�neas de 5 en 5 palabras
     * (ver enunciado)
     *
     * De forma eficiente ya que habr� muchas concatenaciones
     *
     *
     */
    public String toString() {
        // Creamos el StringBuilder
        StringBuilder sb = new StringBuilder();
        // Recorremos el Array
        for(int i = 0; i < total; i++)   {
            // Si i resto 5 es igual a 0 hacer salto de linea
            if(i % 5 == 0)  {
                sb.append("\n");
            }

            sb.append(String.format("%5s", palabras[i].toString()));
        }
        // Devuelve el String
        return sb.toString();
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y
     */
    public void print()
    {
        System.out.println(toString());
    }

    /**
     *  Devuelve la palabra de la posici�n p
     *  Si p es incorrecto se devuelve null
     *      
     */
    public Palabra getPalabra(int p) {
        if(p < 0 || p >= total){
            return null;
        }
        return palabras[p];
    }

    /**
     *
     * @return un array de cadenas con las palabras del texto
     * capitalizadas de forma alterna
     */
    public String[] capitalizarAlterna() {
        String[] i = new String[total];

        for(int j = 0; j < total; j++) {
            String str = "";
            int k = 0;
            for(int l = 0;  l< palabras[j].getPalabra().length(); l++) {

                if(k >= 3 && k < 6 && k < palabras.length) {
                    str += palabras[j].getPalabra().toLowerCase().charAt(k);
                    k++;   
                }
                else{
                    str += palabras[j].getPalabra().toUpperCase().charAt(k);
                    k++;
                }
            }
            i[j] = str;
        }

        return i;
    }

    /**
     *
     * @return un array de cadenas con las palabras que tienen letras
     * repetidas
     */
    public String[] palabrasConLetrasRepetidas() {
        int contador = 0;
        int i = 0;
        for(int j = 0; j < total; j++){
            if(Utilidades.tieneLetrasRepetidas(palabras[i].getPalabra())){
                contador++;
            }
        }
        String[] letrasRepetidas = new String[contador];
        for(int k = 0; k < total; k++){
            if(Utilidades.tieneLetrasRepetidas(palabras[k].getPalabra())){
                letrasRepetidas[i] = palabras[k].getPalabra();
                i++;
            }
        }
        return letrasRepetidas;
    }

    /**
     *
     * @return un array con la frecuencia de palabras de cada longitud
     * La palabra m�s larga consideraremos de longitud 15
     *
     */
    public int[] calcularFrecuenciaLongitud() {
        int[] frecuenciaDeLongitud = new int[15];
        int i = 0; 
        int palabrasTotales;
        while(i < total){
            palabrasTotales = palabras[i].getPalabra().length();
            frecuenciaDeLongitud[palabrasTotales]++;
            i++;
        }

        return frecuenciaDeLongitud;    
    }

    /**
     *
     * @param frecuencia se borra del array palabras aquellas de frecuencia
     *                   menor a la proporcionada
     * @return el n de palabras borradas
     */
    public int borrarDeFrecuenciaMenor(int frecuencia) {
        int b = 0;  
        for(int i = total -1; i >= 0; i--) {
            if(palabras[i].getFrecuencia() < frecuencia) {
                System.arraycopy(palabras, i+1, palabras, i, total - i -1);
                b++;
                total--;
            }
        }
        return b;
    }

    /**
     *  Lee de un fichero un texto formado por una
     *  serie de l�neas.
     *  Cada l�nea contiene varias palabras separadas
     *  por espacios o el . o la ,
     *
     */
    public void leerDeFichero(String nombre) {
        Scanner sc = new Scanner(
                this.getClass().getResourceAsStream(nombre));
        while (sc.hasNextLine()) {
            String linea = sc.nextLine();
            this.addPalabras(linea);
        }
        sc.close();
    
    }
}
