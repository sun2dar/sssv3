import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITKas, defaultValue } from 'app/shared/model/t-kas.model';

export const ACTION_TYPES = {
  FETCH_TKAS_LIST: 'tKas/FETCH_TKAS_LIST',
  FETCH_TKAS: 'tKas/FETCH_TKAS',
  CREATE_TKAS: 'tKas/CREATE_TKAS',
  UPDATE_TKAS: 'tKas/UPDATE_TKAS',
  DELETE_TKAS: 'tKas/DELETE_TKAS',
  SET_BLOB: 'tKas/SET_BLOB',
  RESET: 'tKas/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITKas>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TKasState = Readonly<typeof initialState>;

// Reducer

export default (state: TKasState = initialState, action): TKasState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TKAS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TKAS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TKAS):
    case REQUEST(ACTION_TYPES.UPDATE_TKAS):
    case REQUEST(ACTION_TYPES.DELETE_TKAS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TKAS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TKAS):
    case FAILURE(ACTION_TYPES.CREATE_TKAS):
    case FAILURE(ACTION_TYPES.UPDATE_TKAS):
    case FAILURE(ACTION_TYPES.DELETE_TKAS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TKAS_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TKAS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TKAS):
    case SUCCESS(ACTION_TYPES.UPDATE_TKAS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TKAS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/t-kas';

// Actions

export const getEntities: ICrudGetAllAction<ITKas> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TKAS_LIST,
    payload: axios.get<ITKas>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITKas> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TKAS,
    payload: axios.get<ITKas>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITKas> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TKAS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITKas> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TKAS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITKas> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TKAS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
