package red.neuronal.capa;

import java.util.ArrayList;
import java.util.Random;

import red.neuronal.neurona.Neurona;

/**
 * Esta clase define a una capa con su conjunto de neuronas y sus salidas.
 * @author elm0n0
 *
 */
public class Capa {
	
	/**
	 * neuronas: Objeto de tipo ArrayList<Neurona> para almacenar las neuronas de la capa.
	 */
	public ArrayList<Neurona> neuronas;
	/**
	 * salidas: lista de doubles que almacena en la posición i la salida correspondiente a la neurona i.
	 */
    public double[] salidas;
    
    /**
     * Constructor: Recibe tres parámetros, número de entradas de la capa, número de neuronas de la capa y un objeto Random.
     * @param numEntradas
     * @param numNeuronas
     * @param r
     */
    public Capa(int numEntradas, int numNeuronas, Random r){
        neuronas = new ArrayList<Neurona>();

        for(int i = 0; i < numNeuronas; i++){
            neuronas.add(new Neurona(numEntradas, r));
        }
    }
    
    /**
     *
     * Activacion: Este método recibe un arreglo de double que contiene los valores 
     * de las entradas a cada una de las neuronas de la capa y retorna un arreglo de 
     * double con las salidas de dichas neuronas.
     * 
     * @param entradas
     * @return
     */
    public double[] Activacion(double[] entradas){
        salidas = new double[neuronas.size()];

        for(int i = 0; i < neuronas.size(); i++){
            salidas[i] = neuronas.get(i).Activacion(entradas);
        }
        return salidas;
    }

}
