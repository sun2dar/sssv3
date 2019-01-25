import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMMessage, defaultValue } from 'app/shared/model/m-message.model';

export const ACTION_TYPES = {
  FETCH_MMESSAGE_LIST: 'mMessage/FETCH_MMESSAGE_LIST',
  FETCH_MMESSAGE: 'mMessage/FETCH_MMESSAGE',
  CREATE_MMESSAGE: 'mMessage/CREATE_MMESSAGE',
  UPDATE_MMESSAGE: 'mMessage/UPDATE_MMESSAGE',
  DELETE_MMESSAGE: 'mMessage/DELETE_MMESSAGE',
  RESET: 'mMessage/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMMessage>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MMessageState = Readonly<typeof initialState>;

// Reducer

export default (state: MMessageState = initialState, action): MMessageState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MMESSAGE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MMESSAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MMESSAGE):
    case REQUEST(ACTION_TYPES.UPDATE_MMESSAGE):
    case REQUEST(ACTION_TYPES.DELETE_MMESSAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MMESSAGE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MMESSAGE):
    case FAILURE(ACTION_TYPES.CREATE_MMESSAGE):
    case FAILURE(ACTION_TYPES.UPDATE_MMESSAGE):
    case FAILURE(ACTION_TYPES.DELETE_MMESSAGE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MMESSAGE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MMESSAGE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MMESSAGE):
    case SUCCESS(ACTION_TYPES.UPDATE_MMESSAGE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MMESSAGE):
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

const apiUrl = 'api/m-messages';

// Actions

export const getEntities: ICrudGetAllAction<IMMessage> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MMESSAGE_LIST,
    payload: axios.get<IMMessage>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMMessage> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MMESSAGE,
    payload: axios.get<IMMessage>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMMessage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MMESSAGE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMMessage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MMESSAGE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMMessage> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MMESSAGE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
