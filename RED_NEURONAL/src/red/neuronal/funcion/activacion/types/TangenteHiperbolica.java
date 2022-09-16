package red.neuronal.funcion.activacion.types;

import red.neuronal.funcion.activacion.FuncionActivacion;

public class TangenteHiperbolica implements FuncionActivacion {

	@Override
	public double runFuncionActivacion(double suma) {
		return Math.tanh(suma);
	}

}
