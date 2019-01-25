import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITUtang, defaultValue } from 'app/shared/model/t-utang.model';

export const ACTION_TYPES = {
  FETCH_TUTANG_LIST: 'tUtang/FETCH_TUTANG_LIST',
  FETCH_TUTANG: 'tUtang/FETCH_TUTANG',
  CREATE_TUTANG: 'tUtang/CREATE_TUTANG',
  UPDATE_TUTANG: 'tUtang/UPDATE_TUTANG',
  DELETE_TUTANG: 'tUtang/DELETE_TUTANG',
  SET_BLOB: 'tUtang/SET_BLOB',
  RESET: 'tUtang/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITUtang>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TUtangState = Readonly<typeof initialState>;

// Reducer

export default (state: TUtangState = initialState, action): TUtangState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TUTANG_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TUTANG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TUTANG):
    case REQUEST(ACTION_TYPES.UPDATE_TUTANG):
    case REQUEST(ACTION_TYPES.DELETE_TUTANG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TUTANG_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TUTANG):
    case FAILURE(ACTION_TYPES.CREATE_TUTANG):
    case FAILURE(ACTION_TYPES.UPDATE_TUTANG):
    case FAILURE(ACTION_TYPES.DELETE_TUTANG):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TUTANG_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TUTANG):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TUTANG):
    case SUCCESS(ACTION_TYPES.UPDATE_TUTANG):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TUTANG):
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

const apiUrl = 'api/t-utangs';

// Actions

export const getEntities: ICrudGetAllAction<ITUtang> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TUTANG_LIST,
    payload: axios.get<ITUtang>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITUtang> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TUTANG,
    payload: axios.get<ITUtang>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITUtang> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TUTANG,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITUtang> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TUTANG,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITUtang> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TUTANG,
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
