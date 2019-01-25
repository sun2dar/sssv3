import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITLog, defaultValue } from 'app/shared/model/t-log.model';

export const ACTION_TYPES = {
  FETCH_TLOG_LIST: 'tLog/FETCH_TLOG_LIST',
  FETCH_TLOG: 'tLog/FETCH_TLOG',
  CREATE_TLOG: 'tLog/CREATE_TLOG',
  UPDATE_TLOG: 'tLog/UPDATE_TLOG',
  DELETE_TLOG: 'tLog/DELETE_TLOG',
  RESET: 'tLog/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITLog>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TLogState = Readonly<typeof initialState>;

// Reducer

export default (state: TLogState = initialState, action): TLogState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TLOG_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TLOG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TLOG):
    case REQUEST(ACTION_TYPES.UPDATE_TLOG):
    case REQUEST(ACTION_TYPES.DELETE_TLOG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TLOG_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TLOG):
    case FAILURE(ACTION_TYPES.CREATE_TLOG):
    case FAILURE(ACTION_TYPES.UPDATE_TLOG):
    case FAILURE(ACTION_TYPES.DELETE_TLOG):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TLOG_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TLOG):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TLOG):
    case SUCCESS(ACTION_TYPES.UPDATE_TLOG):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TLOG):
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

const apiUrl = 'api/t-logs';

// Actions

export const getEntities: ICrudGetAllAction<ITLog> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TLOG_LIST,
    payload: axios.get<ITLog>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITLog> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TLOG,
    payload: axios.get<ITLog>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITLog> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TLOG,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITLog> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TLOG,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITLog> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TLOG,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
