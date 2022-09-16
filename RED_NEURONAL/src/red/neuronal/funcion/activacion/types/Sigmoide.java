package red.neuronal.funcion.activacion.types;

import red.neuronal.funcion.activacion.FuncionActivacion;

public class Sigmoide implements FuncionActivacion {

	@Override
	public double runFuncionActivacion(double suma) {
		return (1/( 1 + Math.pow(Math.E,(-1*suma))));
	}
}
