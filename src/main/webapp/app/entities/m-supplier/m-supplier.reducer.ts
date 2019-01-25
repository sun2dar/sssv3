import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMSupplier, defaultValue } from 'app/shared/model/m-supplier.model';

export const ACTION_TYPES = {
  FETCH_MSUPPLIER_LIST: 'mSupplier/FETCH_MSUPPLIER_LIST',
  FETCH_MSUPPLIER: 'mSupplier/FETCH_MSUPPLIER',
  CREATE_MSUPPLIER: 'mSupplier/CREATE_MSUPPLIER',
  UPDATE_MSUPPLIER: 'mSupplier/UPDATE_MSUPPLIER',
  DELETE_MSUPPLIER: 'mSupplier/DELETE_MSUPPLIER',
  SET_BLOB: 'mSupplier/SET_BLOB',
  RESET: 'mSupplier/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMSupplier>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MSupplierState = Readonly<typeof initialState>;

// Reducer

export default (state: MSupplierState = initialState, action): MSupplierState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MSUPPLIER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MSUPPLIER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MSUPPLIER):
    case REQUEST(ACTION_TYPES.UPDATE_MSUPPLIER):
    case REQUEST(ACTION_TYPES.DELETE_MSUPPLIER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MSUPPLIER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MSUPPLIER):
    case FAILURE(ACTION_TYPES.CREATE_MSUPPLIER):
    case FAILURE(ACTION_TYPES.UPDATE_MSUPPLIER):
    case FAILURE(ACTION_TYPES.DELETE_MSUPPLIER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MSUPPLIER_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MSUPPLIER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MSUPPLIER):
    case SUCCESS(ACTION_TYPES.UPDATE_MSUPPLIER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MSUPPLIER):
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

const apiUrl = 'api/m-suppliers';

// Actions

export const getEntities: ICrudGetAllAction<IMSupplier> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MSUPPLIER_LIST,
    payload: axios.get<IMSupplier>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMSupplier> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MSUPPLIER,
    payload: axios.get<IMSupplier>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMSupplier> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MSUPPLIER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMSupplier> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MSUPPLIER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMSupplier> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MSUPPLIER,
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
