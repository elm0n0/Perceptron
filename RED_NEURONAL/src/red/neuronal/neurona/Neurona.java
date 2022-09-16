package red.neuronal.neurona;

import java.util.Random;

public class Neurona {
	
	/**
	 *	umbral: Variable de tipo double donde se almacena el valor del umbral de la neurona.
	 *	pesos: Arreglo de double donde se almacenan los pesos de la neurona.
	 *	sumaPonderada: Variable de tipo double donde se almacena la suma ponderada. 
	 */

	private double umbral;

	private double[] pesos;

	private double sumaPonderada;

	/**
	 * Constructor: Receive dos parámetros, el primero, un entero que representa el número de
	 * conexiones de entrada de la neurona y el segundo un objeto de tipo Random que se emplea 
	 * para inicializar los pesos y el umbral. Este objeto se le pasa como parámetro ya que a 
	 * la hora de crear todas las neuronas de nuestro Perceptrón se necesita que el Random 
	 * tenga la misma semilla para todas.
	 * 
	 * @param int numEntradas, Random r
	 * @return Neurona
	 */
	public Neurona(int numEntradas, Random r){
		super();
		umbral = r.nextDouble();
        pesos = new double[numEntradas];

        for(int i = 0; i < pesos.length; i++){
            pesos[i] = r.nextDouble();
        }
	}
	
	/**
	 * 
	 * Este metodo retorna que se pasan en double[] entradas.
	 * 
	 * @param entradas
	 * @return Activacion
	 */
	public double Activacion(double[] entradas){
        sumaPonderada = umbral;

        for(int i = 0; i < entradas.length; i++){
            sumaPonderada += entradas[i] * pesos[i]; 
        }

        return Sigmoide(sumaPonderada);
    }

	/**
	 * Sigmoide: Este método recibe un double x y retorna la función Sigmoidea evaluada en ese valor.
	 * @param x
	 * @return
	 */
    public double Sigmoide(double x){
        return 1 / (1 + Math.exp(-x));
    }

	public double getUmbral() {
		return umbral;
	}

	public void setUmbral(double umbral) {
		this.umbral = umbral;
	}

	public double[] getPesos() {
		return pesos;
	}

	public void setPesos(double[] pesos) {
		this.pesos = pesos;
	}

	public double getSumaPonderada() {
		return sumaPonderada;
	}

	public void setSumaPonderada(double sumaPonderada) {
		this.sumaPonderada = sumaPonderada;
	}

}
