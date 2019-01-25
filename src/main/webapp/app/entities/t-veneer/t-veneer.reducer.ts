import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITVeneer, defaultValue } from 'app/shared/model/t-veneer.model';

export const ACTION_TYPES = {
  FETCH_TVENEER_LIST: 'tVeneer/FETCH_TVENEER_LIST',
  FETCH_TVENEER: 'tVeneer/FETCH_TVENEER',
  CREATE_TVENEER: 'tVeneer/CREATE_TVENEER',
  UPDATE_TVENEER: 'tVeneer/UPDATE_TVENEER',
  DELETE_TVENEER: 'tVeneer/DELETE_TVENEER',
  RESET: 'tVeneer/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITVeneer>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TVeneerState = Readonly<typeof initialState>;

// Reducer

export default (state: TVeneerState = initialState, action): TVeneerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TVENEER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TVENEER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TVENEER):
    case REQUEST(ACTION_TYPES.UPDATE_TVENEER):
    case REQUEST(ACTION_TYPES.DELETE_TVENEER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TVENEER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TVENEER):
    case FAILURE(ACTION_TYPES.CREATE_TVENEER):
    case FAILURE(ACTION_TYPES.UPDATE_TVENEER):
    case FAILURE(ACTION_TYPES.DELETE_TVENEER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TVENEER_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TVENEER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TVENEER):
    case SUCCESS(ACTION_TYPES.UPDATE_TVENEER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TVENEER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/t-veneers';

// Actions

export const getEntities: ICrudGetAllAction<ITVeneer> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TVENEER_LIST,
    payload: axios.get<ITVeneer>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITVeneer> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TVENEER,
    payload: axios.get<ITVeneer>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITVeneer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TVENEER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITVeneer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TVENEER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITVeneer> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TVENEER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
