import { ChangeEventHandler, useEffect, useState, type FC, type FormEventHandler } from "react";
import {
	type ReparacionTipo,
	getReparacionTipos,
	createReparacionTipo,
	deleteReparacionTipo,
	updateReparacionTipo
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

const ReparacionItem: FC<{
	tipo: ReparacionTipo,
	onUpdate: (tipo: ReparacionTipo) => void,
}> = ({ tipo, onUpdate }) => {
	const [nombre, setNombre] = useState(tipo.nombre);
	const [montoGasolina, setMontoGasolina] = useState(tipo.montoGasolina);
	const [montoDiesel, setMontoDiesel] = useState(tipo.montoDiesel);
	const [montoHibrido, setMontoHibrido] = useState(tipo.montoHibrido);
	const [montoElectrico, setMontoElectrico] = useState(tipo.montoElectrico);
	const [descripcion, setDescripcion] = useState(tipo.descripcion || '');

	const handleUpdateNombre: ChangeEventHandler<HTMLInputElement> = (event) => {
		const nombre = event.target.value;
		setNombre(nombre);
		updateReparacionTipo({ ...tipo, nombre })
		.then(tipo => onUpdate(tipo))
		.catch(err => console.error(err));
	};

	const handleUpdateMontoGasolina: ChangeEventHandler<HTMLInputElement> = (event) => {
		const montoGasolina = parseInt(event.target.value) || 0;
		setMontoGasolina(montoGasolina);
		updateReparacionTipo({ ...tipo, montoGasolina })
		.then(tipo => onUpdate(tipo))
		.catch(err => console.error(err));
	};

	const handleUpdateMontoDiesel: ChangeEventHandler<HTMLInputElement> = (event) => {
		const montoDiesel = parseInt(event.target.value) || 0;
		setMontoDiesel(montoDiesel);
		updateReparacionTipo({ ...tipo, montoDiesel })
		.then(tipo => onUpdate(tipo))
		.catch(err => console.error(err));
	};

	const handleUpdateMontoHibrido: ChangeEventHandler<HTMLInputElement> = (event) => {
		const montoHibrido = parseInt(event.target.value) || 0;
		setMontoHibrido(montoHibrido);
		updateReparacionTipo({ ...tipo, montoHibrido })
		.then(tipo => onUpdate(tipo))
		.catch(err => console.error(err));
	};

	const handleUpdateMontoElectrico: ChangeEventHandler<HTMLInputElement> = (event) => {
		const montoElectrico = parseInt(event.target.value) || 0;
		setMontoElectrico(montoElectrico);
		updateReparacionTipo({ ...tipo, montoElectrico })
		.then(tipo => onUpdate(tipo))
		.catch(err => console.error(err));
	};

	const handleUpdateDescripcion: ChangeEventHandler<HTMLInputElement> = (event) => {
		const descripcion = event.target.value;
		setDescripcion(descripcion);
		updateReparacionTipo({
			...tipo,
			descripcion: (!!descripcion) ? descripcion : null,
		})
		.then(tipo => onUpdate(tipo))
		.catch(err => console.error(err));
	};

	return (
		<>
			<InputGroup className="mb-2">
				<InputGroup.Text>Nombre:</InputGroup.Text>
				<Form.Control
					type="text"
					placeholder="Tipo de Reparación (Nombre)"
					value={nombre}
					onChange={handleUpdateNombre}
				/>
			</InputGroup>
			<InputGroup className="mb-2">
				<InputGroup.Text>Gasolina:</InputGroup.Text>
				<Form.Control
					type="number"
					min={0}
					step={1}
					placeholder="Gasolina"
					value={montoGasolina}
					onChange={handleUpdateMontoGasolina}
				/>
			</InputGroup>
			<InputGroup className="mb-2">
				<InputGroup.Text>Diesel:</InputGroup.Text>
				<Form.Control
					type="number"
					min={0}
					step={1}
					placeholder="Diesel"
					value={montoDiesel}
					onChange={handleUpdateMontoDiesel}
				/>
			</InputGroup>
			<InputGroup className="mb-2">
				<InputGroup.Text>Hibrido:</InputGroup.Text>
				<Form.Control
					type="number"
					min={0}
					step={1}
					placeholder="Hibrido"
					value={montoHibrido}
					onChange={handleUpdateMontoHibrido}
				/>
			</InputGroup>
			<InputGroup className="mb-2">
				<InputGroup.Text>Electrico:</InputGroup.Text>
				<Form.Control
					type="number"
					min={0}
					step={1}
					placeholder="Electrico"
					value={montoElectrico}
					onChange={handleUpdateMontoElectrico}
				/>
			</InputGroup>
			<FloatingLabel className="mb-2" controlId="floatingTextarea2" label="Descripción:">
				<Form.Control
					as="textarea"
					placeholder="Descripción del tipo de reparación"
					value={descripcion}
					onChange={handleUpdateDescripcion}
					style={{ minHeight: '100px' }}
				/>
			</FloatingLabel>
		</>
	);
}

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

	useEffect(() => {
		setState(state => ({ ...state, loading: true, error: null }));
		getReparacionTipos()
		.then(tipos => {
			setState(state => ({ ...state, loading: false, error: null, tipos }));
		})
		.catch(err => {
			setState(state => ({ ...state, loading: false, error: (err as Error).message }));
		});
	}, [state.signal]);

	const handleSubmit: FormEventHandler = (ev) => {
		ev.preventDefault();
		createReparacionTipo({
			nombre,
			montoGasolina,
			montoDiesel,
			montoHibrido,
			montoElectrico,
			descripcion: !!descripcion ? descripcion : null,
		})
		.then(() => {
			setState(state => ({ ...state, signal: Symbol(), }));
		})
		.catch(err => {
			setState(state => ({ ...state, error: (err as Error).message, }));
		});
	};

	const handleRemove = (reparacion: ReparacionTipo) => {
		deleteReparacionTipo(reparacion)
		.then(() => {
			setState(state => ({ ...state, signal: Symbol(), }));
		})
		.catch(err => {
			setState(state => ({ ...state, error: (err as Error).message, }));
		});
	};

	const handleItemUpdate = (reparacion: ReparacionTipo) => {
		setState(state => {
			const tipos = state.tipos.map(tipo => {
				if(tipo.id == reparacion.id){
					return reparacion;
				}
				return tipo;
			});
			return { ...state, tipos };
		});
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
									<th>Id</th>
									<th>Detalle</th>
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
											<td className="min-width-cell" align="center">
												{tipo.id}
											</td>
											<td width="100%">
												<ReparacionItem
													tipo={tipo}
													onUpdate={handleItemUpdate}
												/>
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
							<div className="my-2">Montos:</div>
							<InputGroup className="mb-2">
								<InputGroup.Text>Gasolina:</InputGroup.Text>
								<Form.Control
									type="number"
									min={0}
									step={1}
									placeholder="Gasolina"
									value={montoGasolina}
									onChange={ev => setMontoGasolina(parseInt(ev.target.value))}
								/>
								<InputGroup.Text>Diesel:</InputGroup.Text>
								<Form.Control
									type="number"
									min={0}
									step={1}
									placeholder="Diesel"
									value={montoDiesel}
									onChange={ev => setMontoDiesel(parseInt(ev.target.value))}
								/>
								<InputGroup.Text>Híbrido:</InputGroup.Text>
								<Form.Control
									type="number"
									min={0}
									step={1}
									placeholder="Híbrido"
									value={montoHibrido}
									onChange={ev => setMontoHibrido(parseInt(ev.target.value))}
								/>
								<InputGroup.Text>Eléctrico:</InputGroup.Text>
								<Form.Control
									type="number"
									min={0}
									step={1}
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