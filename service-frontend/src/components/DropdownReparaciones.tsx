import { useReducer, type FC, useEffect } from "react";
import { getReparacionTipos, type Reparacion, type ReparacionTipo } from "../services/Reparaciones/Reparaciones";
import type { ReducerAction } from "../types/Reducer";
import type { Orden } from "../services/Ordenes/Ordenes";
import Dropdown from 'react-bootstrap/Dropdown';

interface ReducerState {
	loading: boolean,
	tipos: ReparacionTipo[],
	filtered: ReparacionTipo[],
};

const DEFAULT_STATE: ReducerState = {
	loading: true,
	tipos: [],
	filtered: [],
};

const actions = {
	FETCHING: Symbol.for('FETCHING'),
	FETCH_DONE: Symbol.for('FETCH_DONE'),
	UPDATE: Symbol.for('UPDATE'),
};

const reducerHandler = (state: ReducerState, action: ReducerAction): ReducerState => {
	if(action.type === actions.FETCHING){
		return { ...state, loading: true };
	}
	else if(action.type === actions.FETCH_DONE){
		const tipos: ReparacionTipo[] = action.tipos;
		const reparaciones: Reparacion[] = action.reparaciones;

		let filtered = tipos;
		if(reparaciones.length > 0 && tipos.length > 0){
			const ids = reparaciones.map(r => r.tipo);
			filtered = tipos.filter(tipo => !ids.includes(tipo.id));
		}

		return {
			...state,
			loading: false,
			tipos,
			filtered,
		};
	}
	else if(action.type === actions.UPDATE){
		const tipos = state.tipos;
		const reparaciones: Reparacion[] = action.reparaciones;

		let filtered = tipos;
		if(reparaciones.length > 0 && tipos.length > 0){
			const ids = reparaciones.map(r => r.tipo);
			filtered = tipos.filter(tipo => !ids.includes(tipo.id));
		}

		return {
			...state,
			loading: false,
			filtered,
		};
	}
	return state;
};

interface DropdownReparacionesProps {
	loading: boolean,
	orden: Orden,
	reparaciones: Reparacion[],
	disabled?: boolean,
	onSelect: (id: number) => void,
};

const DropdownReparaciones: FC<DropdownReparacionesProps> = ({ loading, orden, reparaciones, disabled = false, onSelect }) => {
	const [state, dispatch] = useReducer(reducerHandler, DEFAULT_STATE);

	useEffect(() => {
		if(orden){
			if(state.loading){
				dispatch({ type: actions.FETCHING });
				getReparacionTipos()
				.then(tipos => {
					dispatch({ type: actions.FETCH_DONE, tipos, reparaciones });
				})
				.catch(() => dispatch({ type: actions.FETCH_DONE, tipos: [], reparaciones }));
			}
			else{
				dispatch({ type: actions.UPDATE, reparaciones });
			}
		}
	}, [orden, reparaciones, state.loading]);

	const handleSelect = (eventKey: string|null) => {
		if(eventKey){
			onSelect(parseInt(eventKey));
		}
	};

	return (
		<Dropdown drop="start" onSelect={handleSelect}>
			<Dropdown.Toggle
				id="add-reparacion"
				disabled={state.loading || loading || disabled || state.filtered.length == 0}
			>
				Agregar Reparación
			</Dropdown.Toggle>
			<Dropdown.Menu>
				{state.loading ? (
					<Dropdown.ItemText>Cargando Reparaciones...</Dropdown.ItemText>
				):(
					state.filtered.map(tipo => (
						<Dropdown.Item as="button" key={tipo.id} eventKey={tipo.id}>{tipo.nombre}</Dropdown.Item>
					))
				)}
			</Dropdown.Menu>
		</Dropdown>
	);
};

export default DropdownReparaciones;