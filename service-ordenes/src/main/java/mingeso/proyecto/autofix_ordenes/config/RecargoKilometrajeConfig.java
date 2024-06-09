package mingeso.proyecto.autofix_ordenes.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecargoKilometrajeConfig {

	private static final Map<String, List<Double>> RECARGOS = new HashMap<>();

	static {
		// Para auto tipo SEDAN:
		List<Double> listSedan = new ArrayList<>();
		listSedan.add(0.00);
		listSedan.add(0.03);
		listSedan.add(0.07);
		listSedan.add(0.12);
		listSedan.add(0.20);
		RECARGOS.put("SEDAN", listSedan);

		// Para auto tipo HATCHBACK:
		List<Double> listHatchback = new ArrayList<>();
		listHatchback.add(0.00);
		listHatchback.add(0.03);
		listHatchback.add(0.07);
		listHatchback.add(0.12);
		listHatchback.add(0.20);
		RECARGOS.put("HATCHBACK", listHatchback);

		// Para auto tipo SUV:
		List<Double> listSuv = new ArrayList<>();
		listSuv.add(0.00);
		listSuv.add(0.05);
		listSuv.add(0.09);
		listSuv.add(0.12);
		listSuv.add(0.20);
		RECARGOS.put("SUV", listSuv);

		// Para auto tipo PICKUP:
		List<Double> listPickup = new ArrayList<>();
		listPickup.add(0.00);
		listPickup.add(0.05);
		listPickup.add(0.09);
		listPickup.add(0.12);
		listPickup.add(0.20);
		RECARGOS.put("PICKUP", listPickup);

		// Para auto tipo FURGONETA:
		List<Double> listFurgoneta = new ArrayList<>();
		listFurgoneta.add(0.00);
		listFurgoneta.add(0.05);
		listFurgoneta.add(0.09);
		listFurgoneta.add(0.12);
		listFurgoneta.add(0.20);
		RECARGOS.put("FURGONETA", listFurgoneta);
	}

	public static Double getRecargo(String tipoAuto, Integer kilometraje) throws Exception {
		List<Double> recargos = RECARGOS.get(tipoAuto);
		if (recargos != null) {
			if(kilometraje < 5_000){
				return recargos.get(0);
			}
			else if(kilometraje > 5_000 && kilometraje <= 12_000){
				return recargos.get(1);
			}
			else if(kilometraje > 12_000 && kilometraje <= 25_000){
				return recargos.get(2);
			}
			else if(kilometraje > 25_000 && kilometraje <= 40_000){
				return recargos.get(3);
			}
			return recargos.get(4);
		}
		throw new Exception(String.format("No se pudo obtener la recarga (tipoAuto=%s, kilometraje=%s)", tipoAuto, kilometraje.toString()));
	}
}
