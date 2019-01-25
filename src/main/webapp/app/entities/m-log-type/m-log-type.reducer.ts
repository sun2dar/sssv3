import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMLogType, defaultValue } from 'app/shared/model/m-log-type.model';

export const ACTION_TYPES = {
  FETCH_MLOGTYPE_LIST: 'mLogType/FETCH_MLOGTYPE_LIST',
  FETCH_MLOGTYPE: 'mLogType/FETCH_MLOGTYPE',
  CREATE_MLOGTYPE: 'mLogType/CREATE_MLOGTYPE',
  UPDATE_MLOGTYPE: 'mLogType/UPDATE_MLOGTYPE',
  DELETE_MLOGTYPE: 'mLogType/DELETE_MLOGTYPE',
  SET_BLOB: 'mLogType/SET_BLOB',
  RESET: 'mLogType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMLogType>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MLogTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: MLogTypeState = initialState, action): MLogTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MLOGTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MLOGTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MLOGTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_MLOGTYPE):
    case REQUEST(ACTION_TYPES.DELETE_MLOGTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MLOGTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MLOGTYPE):
    case FAILURE(ACTION_TYPES.CREATE_MLOGTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_MLOGTYPE):
    case FAILURE(ACTION_TYPES.DELETE_MLOGTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MLOGTYPE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MLOGTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MLOGTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_MLOGTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MLOGTYPE):
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

const apiUrl = 'api/m-log-types';

// Actions

export const getEntities: ICrudGetAllAction<IMLogType> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MLOGTYPE_LIST,
    payload: axios.get<IMLogType>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMLogType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MLOGTYPE,
    payload: axios.get<IMLogType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMLogType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MLOGTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMLogType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MLOGTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMLogType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MLOGTYPE,
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
