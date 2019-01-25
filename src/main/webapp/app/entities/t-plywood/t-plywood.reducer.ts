import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITPlywood, defaultValue } from 'app/shared/model/t-plywood.model';

export const ACTION_TYPES = {
  FETCH_TPLYWOOD_LIST: 'tPlywood/FETCH_TPLYWOOD_LIST',
  FETCH_TPLYWOOD: 'tPlywood/FETCH_TPLYWOOD',
  CREATE_TPLYWOOD: 'tPlywood/CREATE_TPLYWOOD',
  UPDATE_TPLYWOOD: 'tPlywood/UPDATE_TPLYWOOD',
  DELETE_TPLYWOOD: 'tPlywood/DELETE_TPLYWOOD',
  RESET: 'tPlywood/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITPlywood>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TPlywoodState = Readonly<typeof initialState>;

// Reducer

export default (state: TPlywoodState = initialState, action): TPlywoodState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TPLYWOOD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TPLYWOOD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TPLYWOOD):
    case REQUEST(ACTION_TYPES.UPDATE_TPLYWOOD):
    case REQUEST(ACTION_TYPES.DELETE_TPLYWOOD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TPLYWOOD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TPLYWOOD):
    case FAILURE(ACTION_TYPES.CREATE_TPLYWOOD):
    case FAILURE(ACTION_TYPES.UPDATE_TPLYWOOD):
    case FAILURE(ACTION_TYPES.DELETE_TPLYWOOD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TPLYWOOD_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TPLYWOOD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TPLYWOOD):
    case SUCCESS(ACTION_TYPES.UPDATE_TPLYWOOD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TPLYWOOD):
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

const apiUrl = 'api/t-plywoods';

// Actions

export const getEntities: ICrudGetAllAction<ITPlywood> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TPLYWOOD_LIST,
    payload: axios.get<ITPlywood>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITPlywood> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TPLYWOOD,
    payload: axios.get<ITPlywood>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITPlywood> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TPLYWOOD,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITPlywood> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TPLYWOOD,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITPlywood> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TPLYWOOD,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
