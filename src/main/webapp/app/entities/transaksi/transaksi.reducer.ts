import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITransaksi, defaultValue } from 'app/shared/model/transaksi.model';

export const ACTION_TYPES = {
  FETCH_TRANSAKSI_LIST: 'transaksi/FETCH_TRANSAKSI_LIST',
  FETCH_TRANSAKSI: 'transaksi/FETCH_TRANSAKSI',
  CREATE_TRANSAKSI: 'transaksi/CREATE_TRANSAKSI',
  UPDATE_TRANSAKSI: 'transaksi/UPDATE_TRANSAKSI',
  DELETE_TRANSAKSI: 'transaksi/DELETE_TRANSAKSI',
  SET_BLOB: 'transaksi/SET_BLOB',
  RESET: 'transaksi/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITransaksi>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TransaksiState = Readonly<typeof initialState>;

// Reducer

export default (state: TransaksiState = initialState, action): TransaksiState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TRANSAKSI_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TRANSAKSI):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TRANSAKSI):
    case REQUEST(ACTION_TYPES.UPDATE_TRANSAKSI):
    case REQUEST(ACTION_TYPES.DELETE_TRANSAKSI):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TRANSAKSI_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TRANSAKSI):
    case FAILURE(ACTION_TYPES.CREATE_TRANSAKSI):
    case FAILURE(ACTION_TYPES.UPDATE_TRANSAKSI):
    case FAILURE(ACTION_TYPES.DELETE_TRANSAKSI):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TRANSAKSI_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TRANSAKSI):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TRANSAKSI):
    case SUCCESS(ACTION_TYPES.UPDATE_TRANSAKSI):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TRANSAKSI):
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

const apiUrl = 'api/transaksis';

// Actions

export const getEntities: ICrudGetAllAction<ITransaksi> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TRANSAKSI_LIST,
    payload: axios.get<ITransaksi>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITransaksi> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TRANSAKSI,
    payload: axios.get<ITransaksi>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITransaksi> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TRANSAKSI,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITransaksi> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TRANSAKSI,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITransaksi> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TRANSAKSI,
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
