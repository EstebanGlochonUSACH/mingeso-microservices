import { useState, type FC, useEffect } from "react";
import type { AxiosError } from "axios";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faWarning } from '@fortawesome/free-solid-svg-icons/faWarning';
import Card from "react-bootstrap/Card";
import Table from "react-bootstrap/Table";
import { numberWithCommas } from "../../utils/utils";
import { type Orden, getOrdenesAll } from "../../services/Ordenes/Ordenes";

const sumaMontos = (...args: Array<number|null|undefined>) => {
	let total = 0;
	for(const arg of args){
		if(typeof(arg) == 'number' && arg > 0) total = total + arg;
	}
	return total;
}

const getDate = (isoDate: string|null) => {
	if(!isoDate) return '-';
	const date = new Date(isoDate);
	const year = date.getFullYear();
	let month = ('00' + (date.getMonth() + 1));
	month = month.substring(month.length - 2, month.length);
	let day = ('00' + date.getDay());
	day = day.substring(day.length - 2, day.length);
	return `${year}-${month}-${day}`;
};

const getTime = (isoDate: string|null) => {
	if(!isoDate) return '-';
	const date = new Date(isoDate);
	const hours = date.getHours();
	let minutes = ('00' + date.getMinutes());
	minutes = minutes.substring(minutes.length - 2, minutes.length);
	return `${hours}:${minutes}`;
};

interface State {
	loading: boolean,
	error: string|null,
	ordenes: Orden[],
};

const ViewReporte3: FC = () => {
	const [state, setState] = useState<State>({
		loading: true,
		error: null,
		ordenes: [],
	});

	useEffect(() => {
		setState(state => ({ ...state, loading: true }));
		getOrdenesAll()
		.then(ordenes => {
			setState(state => ({ ...state, loading: false, error: null, ordenes }));
		})
		.catch((err: AxiosError) => setState(state => ({ ...state, loading: false, ordenes: [], error: err.message })));
	}, []);

	return (
		<Card>
			<Card.Header>
				<b>Reporte</b>: Autos con "todas" las reparaciones
			</Card.Header>
			{state.loading ? (
				<Card.Body className="text-center fst-italic p-4">Cargando Ordenes...</Card.Body>
			):((state.error ? (
				<Card.Body className="text-center fst-italic p-5">
					<FontAwesomeIcon icon={faWarning} /> {state.error}
				</Card.Body>
			):((state.ordenes.length === 0) ? (
				<Card.Body className="text-center fst-italic p-4">No hay datos</Card.Body>
			):(
				<Card.Body>
					<Table responsive>
						<thead>
							<tr>
								<th>Patente Vehículo</th>
								<th>Marca Vehículo</th>
								<th>Modelo Vehículo</th>
								<th>Tipo Vehículo</th>
								<th>Año Fabricación</th>
								<th>Tipo Motor</th>
								<th>Fecha Ingreso Taller</th>
								<th>Hora Ingreso Taller</th>
								<th>Monto Total Reparaciones</th>
								<th>Monto Recargos</th>
								<th>Monto Descuentos</th>
								<th>SUB Total</th>
								<th>Monto IVA</th>
								<th>Costo Total</th>
								<th>Fecha Salida Taller</th>
								<th>Hora Salida Taller</th>
								<th>Fecha Retiro Cliente</th>
								<th>Hora Retiro Cliente</th>
							</tr>
						</thead>
						<tbody>
							{state.ordenes.map(orden => (
								<tr key={orden.id}>
									<td>{orden.auto.patente}</td>
									<td>{orden.auto.marca.nombre}</td>
									<td>{orden.auto.modelo}</td>
									<td>{orden.auto.tipo}</td>
									<td>{orden.auto.anio}</td>
									<td>{orden.auto.motor}</td>
									<td>{getDate(orden.fechaIngreso)}</td>
									<td>{getTime(orden.fechaIngreso)}</td>
									<td>{numberWithCommas(sumaMontos(orden.montoReparaciones))}</td>
									<td>{numberWithCommas(sumaMontos(orden.recargaAntiguedad, orden.recargaAtraso, orden.recargaKilometraje))}</td>
									<td>{numberWithCommas(sumaMontos(orden.descuentoDiaAtencion, orden.descuentoReparaciones, orden.bono?.monto))}</td>
									<td>{numberWithCommas(sumaMontos(orden.montoTotal))}</td>
									<td>{numberWithCommas(sumaMontos(orden.valorIva))}</td>
									<td>{numberWithCommas(sumaMontos(orden.montoTotal, orden.valorIva))}</td>
									<td>{getDate(orden.fechaSalida)}</td>
									<td>{getTime(orden.fechaSalida)}</td>
									<td>{getDate(orden.fechaEntrega)}</td>
									<td>{getTime(orden.fechaEntrega)}</td>
								</tr>
							))}
						</tbody>
					</Table>
				</Card.Body>
			))))}
		</Card>
	);
};

export default ViewReporte3;