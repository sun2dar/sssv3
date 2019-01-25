import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITBongkar, defaultValue } from 'app/shared/model/t-bongkar.model';

export const ACTION_TYPES = {
  FETCH_TBONGKAR_LIST: 'tBongkar/FETCH_TBONGKAR_LIST',
  FETCH_TBONGKAR: 'tBongkar/FETCH_TBONGKAR',
  CREATE_TBONGKAR: 'tBongkar/CREATE_TBONGKAR',
  UPDATE_TBONGKAR: 'tBongkar/UPDATE_TBONGKAR',
  DELETE_TBONGKAR: 'tBongkar/DELETE_TBONGKAR',
  RESET: 'tBongkar/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITBongkar>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TBongkarState = Readonly<typeof initialState>;

// Reducer

export default (state: TBongkarState = initialState, action): TBongkarState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TBONGKAR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TBONGKAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TBONGKAR):
    case REQUEST(ACTION_TYPES.UPDATE_TBONGKAR):
    case REQUEST(ACTION_TYPES.DELETE_TBONGKAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TBONGKAR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TBONGKAR):
    case FAILURE(ACTION_TYPES.CREATE_TBONGKAR):
    case FAILURE(ACTION_TYPES.UPDATE_TBONGKAR):
    case FAILURE(ACTION_TYPES.DELETE_TBONGKAR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TBONGKAR_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TBONGKAR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TBONGKAR):
    case SUCCESS(ACTION_TYPES.UPDATE_TBONGKAR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TBONGKAR):
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

const apiUrl = 'api/t-bongkars';

// Actions

export const getEntities: ICrudGetAllAction<ITBongkar> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TBONGKAR_LIST,
    payload: axios.get<ITBongkar>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITBongkar> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TBONGKAR,
    payload: axios.get<ITBongkar>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITBongkar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TBONGKAR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITBongkar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TBONGKAR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITBongkar> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TBONGKAR,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
