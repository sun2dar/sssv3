import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMLog, defaultValue } from 'app/shared/model/m-log.model';

export const ACTION_TYPES = {
  FETCH_MLOG_LIST: 'mLog/FETCH_MLOG_LIST',
  FETCH_MLOG: 'mLog/FETCH_MLOG',
  CREATE_MLOG: 'mLog/CREATE_MLOG',
  UPDATE_MLOG: 'mLog/UPDATE_MLOG',
  DELETE_MLOG: 'mLog/DELETE_MLOG',
  RESET: 'mLog/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMLog>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MLogState = Readonly<typeof initialState>;

// Reducer

export default (state: MLogState = initialState, action): MLogState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MLOG_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MLOG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MLOG):
    case REQUEST(ACTION_TYPES.UPDATE_MLOG):
    case REQUEST(ACTION_TYPES.DELETE_MLOG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MLOG_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MLOG):
    case FAILURE(ACTION_TYPES.CREATE_MLOG):
    case FAILURE(ACTION_TYPES.UPDATE_MLOG):
    case FAILURE(ACTION_TYPES.DELETE_MLOG):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MLOG_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MLOG):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MLOG):
    case SUCCESS(ACTION_TYPES.UPDATE_MLOG):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MLOG):
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

const apiUrl = 'api/m-logs';

// Actions

export const getEntities: ICrudGetAllAction<IMLog> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MLOG_LIST,
    payload: axios.get<IMLog>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMLog> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MLOG,
    payload: axios.get<IMLog>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMLog> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MLOG,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMLog> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MLOG,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMLog> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MLOG,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
