import { useState, type FC, type FormEventHandler } from "react";
import {
	type ReparacionTipo,
	getReparacionTipos,
	createReparacionTipo,
	deleteReparacionTipo
} from "../../services/Reparaciones/Reparaciones";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faWarning } from '@fortawesome/free-solid-svg-icons/faWarning';
import { faXmark } from '@fortawesome/free-solid-svg-icons/faXmark';
import Container from "react-bootstrap/Container";
import Card from "react-bootstrap/Card";
import Table from "react-bootstrap/Table";
import Form from "react-bootstrap/Form";
import FloatingLabel from 'react-bootstrap/FloatingLabel';
import InputGroup from 'react-bootstrap/InputGroup';
import Button from 'react-bootstrap/Button';
import Alert from "react-bootstrap/Alert";

const ListReparaciones: FC = () => {
	const [state, setState] = useState<{
		loading: boolean,
		error: string|null,
		signal: Symbol,
		tipos: ReparacionTipo[],
	}>({
		loading: true,
		error: null,
		tipos: [],
		signal: Symbol(),
	});
	const [nombre, setNombre] = useState('');
	const [montoGasolina, setMontoGasolina] = useState(0);
	const [montoDiesel, setMontoDiesel] = useState(0);
	const [montoHibrido, setMontoHibrido] = useState(0);
	const [montoElectrico, setMontoElectrico] = useState(0);
	const [descripcion, setDescripcion] = useState('');

	const handleSubmit: FormEventHandler = (ev) => {
		ev.preventDefault();
		createReparacionTipo({
			nombre,
			montoGasolina,
			montoDiesel,
			montoHibrido,
			montoElectrico,
			descripcion: !!descripcion ? descripcion : undefined,
		})
		.then(() => {
			setState(state => ({ ...state, signal: Symbol(), }));
		})
		.catch(err => {
			setState(state => ({ ...state, error: (err as Error).message, }));
		});
	};

	const handleRemove = (reparacion: ReparacionTipo) => {
		deleteReparacionTipo(reparacion);
	};

	return (
		<Container className="py-2">
			<Card>
				<Card.Header>Reparaciones</Card.Header>
				{state.loading ? (
					<Card.Body className="text-center fst-italic p-4">Cargando Autos...</Card.Body>
				):(
					<Card.Body>
						<Table style={{ tableLayout: 'auto' }}>
							<thead>
								<tr>
									<th>Nombre</th>
									<th>Autos</th>
									<th className="min-width-cell">Eliminar</th>
								</tr>
							</thead>
							<tbody>
								{(state.tipos.length === 0) ? (
									<tr>
										<td colSpan={3} className="text-center fst-italic p-4">No hay datos</td>
									</tr>
								):(
									state.tipos.map(tipo => (
										<tr key={tipo.id}>
											<td width="100%">
												<div>Nombre: {tipo.nombre}</div>
												<div>Monto Gasolina: {tipo.montoGasolina}</div>
												<div>Monto Diesel: {tipo.montoDiesel}</div>
												<div>Monto Hibrido: {tipo.montoHibrido}</div>
												<div>Monto Electrico: {tipo.montoElectrico}</div>
												<div>Descripción: {tipo.descripcion || ''}</div>
											</td>
											<td className="min-width-cell" align="center">
												<Button size="sm" onClick={() => handleRemove(tipo)}>
													<FontAwesomeIcon icon={faXmark} />
												</Button>
											</td>
										</tr>
									))
								)}
							</tbody>
						</Table>
						<Form onSubmit={handleSubmit}>
							<InputGroup className="mb-2">
								<Form.Control
									type="text"
									placeholder="Tipo de Reparación (Nombre)"
									value={nombre}
									onChange={ev => setNombre(ev.target.value)}
								/>
								<Button type="submit">Agregar</Button>
							</InputGroup>
							<FloatingLabel className="mb-2" controlId="floatingTextarea2" label="Descripción">
								<Form.Control
									as="textarea"
									placeholder="Descripción del tipo de reparación"
									value={descripcion}
									onChange={ev => setDescripcion(ev.target.value)}
									style={{ minHeight: '100px' }}
								/>
							</FloatingLabel>
							<InputGroup className="mb-2">
								<Form.Control
									type="number"
									placeholder="Gasolina"
									value={montoGasolina}
									onChange={ev => setMontoGasolina(parseInt(ev.target.value))}
								/>
							</InputGroup>
							<InputGroup className="mb-2">
								<Form.Control
									type="number"
									placeholder="Diesel"
									value={montoDiesel}
									onChange={ev => setMontoDiesel(parseInt(ev.target.value))}
								/>
							</InputGroup>
							<InputGroup className="mb-2">
								<Form.Control
									type="number"
									placeholder="Híbrido"
									value={montoHibrido}
									onChange={ev => setMontoHibrido(parseInt(ev.target.value))}
								/>
							</InputGroup>
							<InputGroup className="mb-2">
								<Form.Control
									type="number"
									placeholder="Eléctrico"
									value={montoElectrico}
									onChange={ev => setMontoElectrico(parseInt(ev.target.value))}
								/>
							</InputGroup>
							{(!!state.error) && (
								<Alert className="mb-0" variant="danger">
									<FontAwesomeIcon icon={faWarning} /> {state.error}
								</Alert>
							)}
						</Form>
					</Card.Body>
				)}
			</Card>
		</Container>
	)
};

export default ListReparaciones;