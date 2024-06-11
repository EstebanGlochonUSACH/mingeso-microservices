import { Fragment, useState, type FC, useEffect, ChangeEventHandler } from "react";
import type { AxiosError } from "axios";
import { type ReparacionMesTipoSummary, getReporteReparacionTipoMes } from "../../services/Reportes/Reportes";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faWarning } from '@fortawesome/free-solid-svg-icons/faWarning';
import Card from "react-bootstrap/Card";
import Table from "react-bootstrap/Table";
import InputGroup from 'react-bootstrap/InputGroup';
import Form from 'react-bootstrap/Form';
import { numberWithCommas } from "../../utils/utils";

const meses = [
	'Enero', 'Febrero', 'Marzo',
	'Abril', 'Mayo', 'Junio',
	'Julio', 'Agosto', 'Septiembre',
	'Octube', 'Noviembre', 'Diciembre',
];

const getMonthName = (isoDate: string) => {
	const date = new Date(isoDate);
	const mesIndex = date.getMonth();
	return meses[mesIndex];
};

const getVariacion = (valor1: number, valor2: number) => {
	if(valor2 == 0){
		return '0%';
	}
	else{
		const percent = Math.round(((valor1 / valor2) - 1) * 100);
		console.log('percent:', percent);
		return percent + '%';
	}
};

interface State {
	loading: boolean,
	error: string|null,
	reportes: ReparacionMesTipoSummary[],
	fecha: string|undefined,
};

const ViewReporte2: FC = () => {
	const [state, setState] = useState<State>({
		loading: true,
		error: null,
		reportes: [],
		fecha: undefined,
	});

	useEffect(() => {
		if(!!state.fecha){
			const date = new Date(state.fecha);
			const year = date.getFullYear();
			const month = date.getMonth() + 1;
			setState(state => ({ ...state, loading: true }));
			getReporteReparacionTipoMes(year, month)
			.then(reportes => {
				setState(state => ({ ...state, loading: false, error: null, reportes }));
			})
			.catch((err: AxiosError) => setState(state => ({ ...state, loading: false, reportes: [], error: err.message })));
		}
	}, [state.fecha]);

	const handleChangeFecha: ChangeEventHandler<HTMLInputElement> = (event) => {
		setState(state => ({ ...state, fecha: event.target.value }));
	}

	return (
		<Card>
			<Card.Header>
				<b>Reporte</b>: Tipos de Reparaciones vs el número de Tipos de autos
			</Card.Header>
			<Card.Body>
				<InputGroup>
					<InputGroup.Text>Seleccionar Mes:</InputGroup.Text>
						<Form.Control
							type="datetime-local"
							value={state.fecha || ''}
							onChange={handleChangeFecha}
						/>
				</InputGroup>
			</Card.Body>
			{state.loading ? (
				<Card.Body className="text-center fst-italic p-4">Cargando Reportes...</Card.Body>
			):((state.error ? (
				<Card.Body className="text-center fst-italic p-5">
					<FontAwesomeIcon icon={faWarning} /> {state.error}
				</Card.Body>
			):((state.reportes.length === 0) ? (
				<Card.Body className="text-center fst-italic p-4">No hay datos</Card.Body>
			):(
				<Card.Body>
					<Table>
						<thead>
							<tr>
								<th>MES</th>
								<th>{getMonthName(state.reportes[0].mes3)}</th>
								<th>% Variación</th>
								<th>{getMonthName(state.reportes[0].mes2)}</th>
								<th>% Variación</th>
								<th>{getMonthName(state.reportes[0].mes1)}</th>
								<th>% Variación</th>
								<th>{getMonthName(state.reportes[0].mes0)}</th>
							</tr>
						</thead>
						<tbody>
							{state.reportes.map(reporte => (
								<Fragment key={reporte.tipo.id}>
									<tr>
										<td>{reporte.tipo.nombre}</td>
										<td align="right">{reporte.totalMes3}</td>
										<td align="right">{getVariacion(reporte.totalMes3, reporte.totalMes2)}</td>
										<td align="right">{reporte.totalMes2}</td>
										<td align="right">{getVariacion(reporte.totalMes2, reporte.totalMes1)}</td>
										<td align="right">{reporte.totalMes1}</td>
										<td align="right">{getVariacion(reporte.totalMes1, reporte.totalMes0)}</td>
										<td align="right">{reporte.totalMes0}</td>
									</tr>
									<tr>
										<td></td>
										<td align="right">{numberWithCommas(reporte.totalMontoMes3)}</td>
										<td align="right">{getVariacion(reporte.totalMontoMes3, reporte.totalMontoMes2)}</td>
										<td align="right">{numberWithCommas(reporte.totalMontoMes2)}</td>
										<td align="right">{getVariacion(reporte.totalMontoMes2, reporte.totalMontoMes1)}</td>
										<td align="right">{numberWithCommas(reporte.totalMontoMes1)}</td>
										<td align="right">{getVariacion(reporte.totalMontoMes1, reporte.totalMontoMes0)}</td>
										<td align="right">{numberWithCommas(reporte.totalMontoMes0)}</td>
									</tr>
								</Fragment>
							))}
						</tbody>
					</Table>
				</Card.Body>
			))))}
		</Card>
	);
};

export default ViewReporte2;