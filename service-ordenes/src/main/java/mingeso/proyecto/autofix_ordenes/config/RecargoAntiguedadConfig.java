package mingeso.proyecto.autofix_ordenes.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecargoAntiguedadConfig
{
	private static final Map<String, List<Double>> RECARGOS = new HashMap<>();

	static {
		// Para auto tipo SEDAN:
		List<Double> listSedan = new ArrayList<>();
		listSedan.add(0.00);
		listSedan.add(0.05);
		listSedan.add(0.09);
		listSedan.add(0.15);
		RECARGOS.put("SEDAN", listSedan);

		// Para auto tipo HATCHBACK:
		List<Double> listHatchback = new ArrayList<>();
		listHatchback.add(0.00);
		listHatchback.add(0.05);
		listHatchback.add(0.09);
		listHatchback.add(0.15);
		RECARGOS.put("HATCHBACK", listHatchback);

		// Para auto tipo SUV:
		List<Double> listSuv = new ArrayList<>();
		listSuv.add(0.00);
		listSuv.add(0.07);
		listSuv.add(0.11);
		listSuv.add(0.20);
		RECARGOS.put("SUV", listSuv);

		// Para auto tipo PICKUP:
		List<Double> listPickup = new ArrayList<>();
		listPickup.add(0.00);
		listPickup.add(0.07);
		listPickup.add(0.11);
		listPickup.add(0.20);
		RECARGOS.put("PICKUP", listPickup);

		// Para auto tipo FURGONETA:
		List<Double> listFurgoneta = new ArrayList<>();
		listFurgoneta.add(0.00);
		listFurgoneta.add(0.07);
		listFurgoneta.add(0.11);
		listFurgoneta.add(0.20);
		RECARGOS.put("FURGONETA", listFurgoneta);
	}

	public static Double getRecargo(String tipoAuto, Integer antiguedad) throws Exception {
		List<Double> recargos = RECARGOS.get(tipoAuto);
		if (recargos != null) {
			if(antiguedad < 6){
				return recargos.get(0);
			}
			else if(antiguedad >= 6 && antiguedad < 11){
				return recargos.get(1);
			}
			else if(antiguedad >= 11 && antiguedad < 16){
				return recargos.get(2);
			}
			return recargos.get(3);
		}
		throw new Exception(String.format("No se pudo obtener la recarga (tipoAuto=%s, antiguedad=%s)", tipoAuto, antiguedad.toString()));
	}
}
