import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMSatuan, defaultValue } from 'app/shared/model/m-satuan.model';

export const ACTION_TYPES = {
  FETCH_MSATUAN_LIST: 'mSatuan/FETCH_MSATUAN_LIST',
  FETCH_MSATUAN: 'mSatuan/FETCH_MSATUAN',
  CREATE_MSATUAN: 'mSatuan/CREATE_MSATUAN',
  UPDATE_MSATUAN: 'mSatuan/UPDATE_MSATUAN',
  DELETE_MSATUAN: 'mSatuan/DELETE_MSATUAN',
  RESET: 'mSatuan/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMSatuan>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MSatuanState = Readonly<typeof initialState>;

// Reducer

export default (state: MSatuanState = initialState, action): MSatuanState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MSATUAN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MSATUAN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MSATUAN):
    case REQUEST(ACTION_TYPES.UPDATE_MSATUAN):
    case REQUEST(ACTION_TYPES.DELETE_MSATUAN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MSATUAN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MSATUAN):
    case FAILURE(ACTION_TYPES.CREATE_MSATUAN):
    case FAILURE(ACTION_TYPES.UPDATE_MSATUAN):
    case FAILURE(ACTION_TYPES.DELETE_MSATUAN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MSATUAN_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MSATUAN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MSATUAN):
    case SUCCESS(ACTION_TYPES.UPDATE_MSATUAN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MSATUAN):
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

const apiUrl = 'api/m-satuans';

// Actions

export const getEntities: ICrudGetAllAction<IMSatuan> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MSATUAN_LIST,
    payload: axios.get<IMSatuan>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMSatuan> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MSATUAN,
    payload: axios.get<IMSatuan>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMSatuan> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MSATUAN,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMSatuan> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MSATUAN,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMSatuan> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MSATUAN,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
