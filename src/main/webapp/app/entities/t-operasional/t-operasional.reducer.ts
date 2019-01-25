import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITOperasional, defaultValue } from 'app/shared/model/t-operasional.model';

export const ACTION_TYPES = {
  FETCH_TOPERASIONAL_LIST: 'tOperasional/FETCH_TOPERASIONAL_LIST',
  FETCH_TOPERASIONAL: 'tOperasional/FETCH_TOPERASIONAL',
  CREATE_TOPERASIONAL: 'tOperasional/CREATE_TOPERASIONAL',
  UPDATE_TOPERASIONAL: 'tOperasional/UPDATE_TOPERASIONAL',
  DELETE_TOPERASIONAL: 'tOperasional/DELETE_TOPERASIONAL',
  RESET: 'tOperasional/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITOperasional>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TOperasionalState = Readonly<typeof initialState>;

// Reducer

export default (state: TOperasionalState = initialState, action): TOperasionalState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TOPERASIONAL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TOPERASIONAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TOPERASIONAL):
    case REQUEST(ACTION_TYPES.UPDATE_TOPERASIONAL):
    case REQUEST(ACTION_TYPES.DELETE_TOPERASIONAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TOPERASIONAL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TOPERASIONAL):
    case FAILURE(ACTION_TYPES.CREATE_TOPERASIONAL):
    case FAILURE(ACTION_TYPES.UPDATE_TOPERASIONAL):
    case FAILURE(ACTION_TYPES.DELETE_TOPERASIONAL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TOPERASIONAL_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TOPERASIONAL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TOPERASIONAL):
    case SUCCESS(ACTION_TYPES.UPDATE_TOPERASIONAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TOPERASIONAL):
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

const apiUrl = 'api/t-operasionals';

// Actions

export const getEntities: ICrudGetAllAction<ITOperasional> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TOPERASIONAL_LIST,
    payload: axios.get<ITOperasional>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITOperasional> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TOPERASIONAL,
    payload: axios.get<ITOperasional>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITOperasional> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TOPERASIONAL,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITOperasional> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TOPERASIONAL,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITOperasional> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TOPERASIONAL,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
