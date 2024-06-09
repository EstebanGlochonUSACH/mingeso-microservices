package mingeso.proyecto.autofix_autos.models;

import mingeso.proyecto.autofix_autos.config.ValidadorPatenteConfig;;

public enum TipoVehiculo implements Validation
{
	AUTO {
		public Boolean isValid(String patente) {
			return ValidadorPatenteConfig.isPatenteAuto(patente);
		}

		public String setNormalized(String patente) {
			return patente;
		}

		public String setComplete(String patente) {
			return patente;
		}
	},
	MOTO {
		public Boolean isValid(String patente) {
			return ValidadorPatenteConfig.isPatenteMoto(patente);
		}

		public String setNormalized(String patente) {
			if (ValidadorPatenteConfig.isBeNormalized(patente)) {
				return ValidadorPatenteConfig.getNormalized(patente);
			}
			return patente;
		}

		public String setComplete(String patente) {
			if (ValidadorPatenteConfig.isPatenteMotoNotComplete(patente)) {
				return ValidadorPatenteConfig.getComplete(patente);
			}
			return patente;
		}
	}
}
