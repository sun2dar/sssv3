import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMVeneer, defaultValue } from 'app/shared/model/m-veneer.model';

export const ACTION_TYPES = {
  FETCH_MVENEER_LIST: 'mVeneer/FETCH_MVENEER_LIST',
  FETCH_MVENEER: 'mVeneer/FETCH_MVENEER',
  CREATE_MVENEER: 'mVeneer/CREATE_MVENEER',
  UPDATE_MVENEER: 'mVeneer/UPDATE_MVENEER',
  DELETE_MVENEER: 'mVeneer/DELETE_MVENEER',
  RESET: 'mVeneer/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMVeneer>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MVeneerState = Readonly<typeof initialState>;

// Reducer

export default (state: MVeneerState = initialState, action): MVeneerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MVENEER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MVENEER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MVENEER):
    case REQUEST(ACTION_TYPES.UPDATE_MVENEER):
    case REQUEST(ACTION_TYPES.DELETE_MVENEER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MVENEER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MVENEER):
    case FAILURE(ACTION_TYPES.CREATE_MVENEER):
    case FAILURE(ACTION_TYPES.UPDATE_MVENEER):
    case FAILURE(ACTION_TYPES.DELETE_MVENEER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MVENEER_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MVENEER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MVENEER):
    case SUCCESS(ACTION_TYPES.UPDATE_MVENEER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MVENEER):
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

const apiUrl = 'api/m-veneers';

// Actions

export const getEntities: ICrudGetAllAction<IMVeneer> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MVENEER_LIST,
    payload: axios.get<IMVeneer>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMVeneer> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MVENEER,
    payload: axios.get<IMVeneer>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMVeneer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MVENEER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMVeneer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MVENEER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMVeneer> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MVENEER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
