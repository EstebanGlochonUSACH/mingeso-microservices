import axios from "axios";
import type { Orden } from "../Ordenes/Ordenes";

export interface ReparacionTipo {
	id: number,
	nombre: string,
	montoGasolina: number,
	montoDiesel: number,
	montoHibrido: number,
	montoElectrico: number,
	descripcion?: string,
};

export const getReparacionTipos = async () => {
	return axios.get('/api/reparaciones').then(res => (res.data as ReparacionTipo[]));
};

export interface Reparacion {
	id?: number,
	tipo: number,
	monto: number,
	orden?: Orden,
};

export const createReparacionTipo = async (reparacion: Omit<ReparacionTipo, 'id'>) => {
	return axios.post('/api/reparaciones', reparacion).then(res => (res.data as ReparacionTipo));
};

export const deleteReparacionTipo = async (reparacion: ReparacionTipo) => {
	if(reparacion.id){
		return axios.delete('/api/reparaciones/'+reparacion.id).then(() => {});
	}
	return Promise.reject(new Error("La reparacion no tiene <id>"));
};