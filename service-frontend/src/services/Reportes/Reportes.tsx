import axios from "axios";

export interface ReparacionTipoSummary {
    tipo: {
        id: number,
        nombre: string,
        monto: number,
        descripcion: string,
    },
	totalSedan: number,
	totalMontoSedan: number,
	totalHatchback: number,
	totalMontoHatchback: number,
	totalSuv: number,
	totalMontoSuv: number,
	totalPickup: number,
	totalMontoPickup: number,
	totalFurgoneta: number,
	totalMontoFurgoneta: number,
	totalAutos: number,
	totalMonto: number,
};

export const getReporteReparacionTipoAuto = async (year: number, month: number) => {
	return axios.get('/api/reportes/tipo-auto', { params: { year, month } })
		.then(res => (res.data as ReparacionTipoSummary[]));
};

export interface ReparacionMesTipoSummary {
    tipo: {
        id: number,
        nombre: string,
        monto: number,
        descripcion: string,
    },
    mes0: string,
    totalMes0: number,
    totalMontoMes0: number,
    mes1: string,
    totalMes1: number,
    totalMontoMes1: number,
    mes2: string,
    totalMes2: number,
    totalMontoMes2: number,
    mes3: string,
    totalMes3: number,
    totalMontoMes3: number,
    totalReparaciones: number,
    totalMonto: number,
};

export const getReporteReparacionTipoMes = async (year: number, month: number) => {
	return axios.get('/api/reportes/tipo-mes', { params: { year, month } })
		.then(res => (res.data as ReparacionMesTipoSummary[]));
};
