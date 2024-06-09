package mingeso.proyecto.autofix_reparaciones.models;

public interface Validation {
	public Boolean isValid(String patente);
	public String setNormalized(String patente);
	public String setComplete(String patente);
}
