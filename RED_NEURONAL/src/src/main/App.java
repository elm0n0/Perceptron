package src.main;

import java.util.ArrayList;
import java.util.Scanner;

import red.neuronal.Perceptron;

public class App {

	public static void main(String[] args) {

		ArrayList<double[]> entradas = new ArrayList<double[]>();
		ArrayList<double[]> salidas = new ArrayList<double[]>();

		for (int i = 0; i < 10; i++) {
			entradas.add(new double[2]);
			salidas.add(new double[1]);
		}

		entradas.get(0)[0] = 0;
		entradas.get(0)[1] = 0;
		salidas.get(0)[0] = 0;
		
		entradas.get(1)[0] = 0;
		entradas.get(1)[1] = 1;
		salidas.get(1)[0] = 0.1;
		
		entradas.get(2)[0] = 1;
		entradas.get(2)[1] = 0;
		salidas.get(2)[0] = 0.1;
		
		entradas.get(3)[0] = 1;
		entradas.get(3)[1] = 1;
		salidas.get(3)[0] = 0.2;
		
		entradas.get(3)[0] = 2;
		entradas.get(3)[1] = 0;
		salidas.get(3)[0] = 0.2;
		
		entradas.get(3)[0] = 2;
		entradas.get(3)[1] = 1;
		salidas.get(3)[0] = 0.3;
		
		entradas.get(3)[0] = 2;
		entradas.get(3)[1] = 2;
		salidas.get(3)[0] = 0.4;
		
		entradas.get(3)[0] = 3;
		entradas.get(3)[1] = 1;
		salidas.get(3)[0] = 0.4;
		
		entradas.get(3)[0] = 3;
		entradas.get(3)[1] = 2;
		salidas.get(3)[0] = 0.5;
		
		entradas.get(3)[0] = 3;
		entradas.get(3)[1] = 3;
		salidas.get(3)[0] = 0.6;
		
		Perceptron p = new Perceptron(new int[] { entradas.get(0).length, 10, salidas.get(0).length });
		p.Entrenar(entradas, salidas, 0.5, 0.000001);
		
		Scanner read = new Scanner(System.in);
		
		boolean parada = false;
		
		while (true && !parada) {
			double a = read.nextDouble();
			double b = read.nextDouble();
			//Long resultado = Math.round(p.Activacion(new double[] { a, b })[0]);
			System.out.println(p.Activacion(new double[] { a, b })[0]);
			System.out.println("escriba exit para salir o cualquier otra cosa para continuar");
			// consumimos la linea, ya que nextDouble no lee el caracter de fin de linea
			read.nextLine();
			// ingresamos la nueva linea a leer para la condicion de parada
			String texto = read.nextLine();
			if (texto.equals("exit")) {
				parada = true;
			}
		}
		read.close();

	}

}
