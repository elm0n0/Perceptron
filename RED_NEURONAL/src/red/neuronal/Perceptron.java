package red.neuronal;

import java.util.ArrayList;
import java.util.Random;

import red.neuronal.capa.Capa;

/**
 * En esta clase implementaremos toda las funcionalidades necesarias para que
 * nuestra red sea entrenada.
 * 
 * @author elm0n0
 *
 */
public class Perceptron {

	/**
	 * capas: Objeto de tipo ArrayList<Capa> para almacenar las capas que forman al
	 * perceptrón.
	 */
	private ArrayList<Capa> capas;

	/**
	 * deltas: Objeto de tipo ArrayList<double[][]> para almacenar las derivadas.
	 */
	private ArrayList<double[][]> deltas;

	/**
	 * sigmas: objeto de tipo ArrayList<double[]> que permite ir calculando las
	 * derivadas parciales de forma dinámica capa por capa y así cuando se este
	 * calculando las derivadas de las neuronas de la capa i ya previamente se han
	 * calculado las derivadas de la capa i+1, esto es así porque el proceso de Back
	 * propagation se hace desde la capa de salida hasta la capa de entrada.
	 */
	private ArrayList<double[]> sigmas;

	/**
	 * El constructor de esta clase recibe un arreglo de enteros numNeuronasPorCapa
	 * donde se almacenan en la posición i el número de neuronas de la capa i. A la
	 * hora de crear las capas hay que tener en cuenta que en la capa 0 el número de
	 * entradas es el número de entradas de la red, para las demás capas el número
	 * de entradas es la cantidad de neuronas de la capa anterior.
	 * 
	 * @param numNeuronasPorCapa
	 */
	public Perceptron(int[] numNeuronasPorCapa) {
		capas = new ArrayList<Capa>();
		Random r = new Random();

		for (int i = 0; i < numNeuronasPorCapa.length; i++) {
			if (i == 0) {
				capas.add(new Capa(numNeuronasPorCapa[i], numNeuronasPorCapa[i], r));
			} else {
				capas.add(new Capa(numNeuronasPorCapa[i - 1], numNeuronasPorCapa[i], r));
			}
		}
	}

	/**
	 * retorna la función Sigmoidea evaluada en x.
	 * 
	 * @param x
	 * @return
	 */
	public double Sigmoide(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	/**
	 * retorna la derivada de la Sigmoidea evaluada en x.
	 * 
	 * @param x
	 * @return
	 */
	public double SigmoideDerivada(double x) {
		double y = Sigmoide(x);
		return y * (1 - y);
	}

	/**
	 * El método Activacion retorna la salida de nuestra red.
	 * 
	 * @param entradas
	 * @return
	 */
	public double[] Activacion(double[] entradas) {
		double[] salidas = new double[0];
		for (int i = 0; i < capas.size(); i++) {
			salidas = capas.get(i).Activacion(entradas);
			entradas = salidas;
		}
		return salidas;
	}

	/**
	 * retorna el error para un solo conjunto de entrenamiento
	 * 
	 * @param salidaReal
	 * @param salidaEsperada
	 * @return
	 */
	public double Error(double[] salidaReal, double[] salidaEsperada) {
		double err = 0;
		for (int i = 0; i < salidaReal.length; i++) {
			err += 0.5 * Math.pow(salidaReal[i] - salidaEsperada[i], 2);
		}

		return err;
	}

	/**
	 * retorna el error para todos nuestros datos de entrenamiento.
	 * 
	 * @param entradas
	 * @param salidaEsperada
	 * @return
	 */
	public double ErrorTotal(ArrayList<double[]> entradas, ArrayList<double[]> salidaEsperada) {
		double err = 0;
		for (int i = 0; i < entradas.size(); i++) {
			err += Error(Activacion(entradas.get(i)), salidaEsperada.get(i));
		}

		return err;
	}

	/**
	 * inicializa todos los deltas a 0.
	 */
	public void initDeltas() {
		deltas = new ArrayList<double[][]>();

		for (int i = 0; i < capas.size(); i++) {
			deltas.add(new double[capas.get(i).neuronas.size()][capas.get(i).neuronas.get(0).getPesos().length]);
			for (int j = 0; j < capas.get(i).neuronas.size(); j++) {
				for (int k = 0; k < capas.get(i).neuronas.get(0).getPesos().length; k++) {
					deltas.get(i)[j][k] = 0;
				}
			}
		}
	}

	/**
	 * cálculo de los sigmas
	 * 
	 * @param salidaEsperada
	 */
	public void calcSigmas(double[] salidaEsperada) {
		sigmas = new ArrayList<double[]>();
		for (int i = 0; i < capas.size(); i++) {
			sigmas.add(new double[capas.get(i).neuronas.size()]);
		}

		for (int i = capas.size() - 1; i >= 0; i--) {
			for (int j = 0; j < capas.get(i).neuronas.size(); j++) {
				if (i == capas.size() - 1) {
					double y = capas.get(i).salidas[j];
					sigmas.get(i)[j] = (y - salidaEsperada[j]) * SigmoideDerivada(y);
				} else {
					double sum = 0;
					for (int k = 0; k < capas.get(i + 1).neuronas.size(); k++) {
						sum += capas.get(i + 1).neuronas.get(k).getPesos()[j] * sigmas.get(i + 1)[k];
					}
					sigmas.get(i)[j] = SigmoideDerivada(capas.get(i).neuronas.get(j).getSumaPonderada()) * sum;
				}
			}
		}
	}

	/**
	 * cálculo de los deltas
	 */
	public void calcDeltas() {
		for (int i = 1; i < capas.size(); i++) {
			for (int j = 0; j < capas.get(i).neuronas.size(); j++) {
				for (int k = 0; k < capas.get(i).neuronas.get(j).getPesos().length; k++) {
					deltas.get(i)[j][k] += sigmas.get(i)[j] * capas.get(i - 1).salidas[k];
				}
			}
		}
	}

	/**
	 * actualizar los pesos (fórmula del Descenso del Gradiente)
	 * 
	 * @param alfa
	 */
	public void actPesos(double alfa) {
		for (int i = 0; i < capas.size(); i++) {
			for (int j = 0; j < capas.get(i).neuronas.size(); j++) {
				for (int k = 0; k < capas.get(i).neuronas.get(j).getPesos().length; k++) {
					capas.get(i).neuronas.get(j).getPesos()[k] -= alfa * deltas.get(i)[j][k];
				}
			}
		}
	}

	/**
	 * actualizar los umbrales (fórmula del Descenso del Gradiente)
	 * 
	 * @param alfa
	 */
	public void actUmbrales(double alfa) {
		for (int i = 0; i < capas.size(); i++) {
			for (int j = 0; j < capas.get(i).neuronas.size(); j++) {
				double umbral = capas.get(i).neuronas.get(j).getUmbral();
				umbral -= alfa * sigmas.get(i)[j];
				capas.get(i).neuronas.get(j).setUmbral(umbral);
			}
		}
	}

	/**
	 * algoritmo de Back Propagation
	 * 
	 * Para cada conjunto de entrada posible calculamos la salida de la red y
	 * propagamos el error hacia atrás, o lo que es lo mismo, calculamos las
	 * derivadas de todos los pesos y los umbrales con respecto al error. Luego
	 * actualizamos los pesos y los umbrales según la fórmula del Descenso del
	 * gradiente. Note que para actualizar los pesos hay que esperar haber calculado
	 * todas las derivadas asociadas a una entrada.
	 * 
	 * @param entradas
	 * @param salidaEsperada
	 * @param alfa
	 */
	public void BackPropagation(ArrayList<double[]> entradas, ArrayList<double[]> salidaEsperada, double alfa) {
		initDeltas();
		for (int i = 0; i < entradas.size(); i++) {
			Activacion(entradas.get(i));
			calcSigmas(salidaEsperada.get(i));
			calcDeltas();
			actUmbrales(alfa);
		}
		actPesos(alfa);
	}

	/**
	 * entrena a la red
	 * 
	 * @param entradasPruebas: Lista de arreglos de double donde se almacenan todos los datos de entrada.
	 * @param salidasPruebas: Lista de arreglos de double donde se almacenan las respectivas salidas para cada entrada.
	 * @param alfa: Valor que representa a la razón de aprendizaje.
	 * @param maxError: Máximo error permitido.
	 */
	public void Entrenar(ArrayList<double[]> entradasPruebas, ArrayList<double[]> salidasPruebas, double alfa,
			double maxError) {
		double err = 99999999;

		while (err > maxError) {
			BackPropagation(entradasPruebas, salidasPruebas, alfa);
			err = ErrorTotal(entradasPruebas, salidasPruebas);
			System.out.println(err);
		}
	}

}
