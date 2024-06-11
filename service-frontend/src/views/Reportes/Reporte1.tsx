import { Fragment, useState, type FC, useEffect, ChangeEventHandler } from "react";
import type { AxiosError } from "axios";
import { type ReparacionTipoSummary, getReporteReparacionTipoAuto } from "../../services/Reportes/Reportes";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faWarning } from '@fortawesome/free-solid-svg-icons/faWarning';
import Card from "react-bootstrap/Card";
import Table from "react-bootstrap/Table";
import InputGroup from 'react-bootstrap/InputGroup';
import Form from 'react-bootstrap/Form';
import { numberWithCommas } from "../../utils/utils";

interface State {
	loading: boolean,
	error: string|null,
	reportes: ReparacionTipoSummary[],
	fecha: string|undefined,
};

const ViewReporte1: FC = () => {
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
			getReporteReparacionTipoAuto(year, month)
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
								<th>Lista de Reparaciones</th>
								<th>Sedan</th>
								<th>Hatchback</th>
								<th>SUV</th>
								<th>Pickup</th>
								<th>TOTAL</th>
							</tr>
						</thead>
						<tbody>
							{state.reportes.map(reporte => (
								<Fragment key={reporte.tipo.id}>
									<tr>
										<td>{reporte.tipo.nombre}</td>
										<td align="right">{reporte.totalSedan}</td>
										<td align="right">{reporte.totalHatchback}</td>
										<td align="right">{reporte.totalSuv}</td>
										<td align="right">{reporte.totalPickup}</td>
										<td align="right">{reporte.totalAutos}</td>
									</tr>
									<tr>
										<td></td>
										<td align="right">{numberWithCommas(reporte.totalMontoSedan)}</td>
										<td align="right">{numberWithCommas(reporte.totalMontoHatchback)}</td>
										<td align="right">{numberWithCommas(reporte.totalMontoSuv)}</td>
										<td align="right">{numberWithCommas(reporte.totalMontoPickup)}</td>
										<td align="right">{numberWithCommas(reporte.totalMonto)}</td>
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

export default ViewReporte1;