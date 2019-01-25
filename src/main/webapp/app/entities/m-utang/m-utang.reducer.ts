import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMUtang, defaultValue } from 'app/shared/model/m-utang.model';

export const ACTION_TYPES = {
  FETCH_MUTANG_LIST: 'mUtang/FETCH_MUTANG_LIST',
  FETCH_MUTANG: 'mUtang/FETCH_MUTANG',
  CREATE_MUTANG: 'mUtang/CREATE_MUTANG',
  UPDATE_MUTANG: 'mUtang/UPDATE_MUTANG',
  DELETE_MUTANG: 'mUtang/DELETE_MUTANG',
  RESET: 'mUtang/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMUtang>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MUtangState = Readonly<typeof initialState>;

// Reducer

export default (state: MUtangState = initialState, action): MUtangState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MUTANG_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MUTANG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MUTANG):
    case REQUEST(ACTION_TYPES.UPDATE_MUTANG):
    case REQUEST(ACTION_TYPES.DELETE_MUTANG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MUTANG_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MUTANG):
    case FAILURE(ACTION_TYPES.CREATE_MUTANG):
    case FAILURE(ACTION_TYPES.UPDATE_MUTANG):
    case FAILURE(ACTION_TYPES.DELETE_MUTANG):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MUTANG_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MUTANG):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MUTANG):
    case SUCCESS(ACTION_TYPES.UPDATE_MUTANG):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MUTANG):
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

const apiUrl = 'api/m-utangs';

// Actions

export const getEntities: ICrudGetAllAction<IMUtang> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MUTANG_LIST,
    payload: axios.get<IMUtang>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMUtang> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MUTANG,
    payload: axios.get<IMUtang>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMUtang> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MUTANG,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMUtang> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MUTANG,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMUtang> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MUTANG,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
