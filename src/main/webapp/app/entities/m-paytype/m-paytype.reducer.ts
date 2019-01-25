import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMPaytype, defaultValue } from 'app/shared/model/m-paytype.model';

export const ACTION_TYPES = {
  FETCH_MPAYTYPE_LIST: 'mPaytype/FETCH_MPAYTYPE_LIST',
  FETCH_MPAYTYPE: 'mPaytype/FETCH_MPAYTYPE',
  CREATE_MPAYTYPE: 'mPaytype/CREATE_MPAYTYPE',
  UPDATE_MPAYTYPE: 'mPaytype/UPDATE_MPAYTYPE',
  DELETE_MPAYTYPE: 'mPaytype/DELETE_MPAYTYPE',
  SET_BLOB: 'mPaytype/SET_BLOB',
  RESET: 'mPaytype/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMPaytype>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MPaytypeState = Readonly<typeof initialState>;

// Reducer

export default (state: MPaytypeState = initialState, action): MPaytypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MPAYTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MPAYTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MPAYTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_MPAYTYPE):
    case REQUEST(ACTION_TYPES.DELETE_MPAYTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MPAYTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MPAYTYPE):
    case FAILURE(ACTION_TYPES.CREATE_MPAYTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_MPAYTYPE):
    case FAILURE(ACTION_TYPES.DELETE_MPAYTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MPAYTYPE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MPAYTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MPAYTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_MPAYTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MPAYTYPE):
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

const apiUrl = 'api/m-paytypes';

// Actions

export const getEntities: ICrudGetAllAction<IMPaytype> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MPAYTYPE_LIST,
    payload: axios.get<IMPaytype>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMPaytype> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MPAYTYPE,
    payload: axios.get<IMPaytype>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMPaytype> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MPAYTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMPaytype> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MPAYTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMPaytype> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MPAYTYPE,
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
