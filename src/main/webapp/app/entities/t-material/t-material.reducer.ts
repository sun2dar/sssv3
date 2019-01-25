import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITMaterial, defaultValue } from 'app/shared/model/t-material.model';

export const ACTION_TYPES = {
  FETCH_TMATERIAL_LIST: 'tMaterial/FETCH_TMATERIAL_LIST',
  FETCH_TMATERIAL: 'tMaterial/FETCH_TMATERIAL',
  CREATE_TMATERIAL: 'tMaterial/CREATE_TMATERIAL',
  UPDATE_TMATERIAL: 'tMaterial/UPDATE_TMATERIAL',
  DELETE_TMATERIAL: 'tMaterial/DELETE_TMATERIAL',
  RESET: 'tMaterial/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITMaterial>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TMaterialState = Readonly<typeof initialState>;

// Reducer

export default (state: TMaterialState = initialState, action): TMaterialState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TMATERIAL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TMATERIAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TMATERIAL):
    case REQUEST(ACTION_TYPES.UPDATE_TMATERIAL):
    case REQUEST(ACTION_TYPES.DELETE_TMATERIAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TMATERIAL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TMATERIAL):
    case FAILURE(ACTION_TYPES.CREATE_TMATERIAL):
    case FAILURE(ACTION_TYPES.UPDATE_TMATERIAL):
    case FAILURE(ACTION_TYPES.DELETE_TMATERIAL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TMATERIAL_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TMATERIAL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TMATERIAL):
    case SUCCESS(ACTION_TYPES.UPDATE_TMATERIAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TMATERIAL):
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

const apiUrl = 'api/t-materials';

// Actions

export const getEntities: ICrudGetAllAction<ITMaterial> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TMATERIAL_LIST,
    payload: axios.get<ITMaterial>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITMaterial> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TMATERIAL,
    payload: axios.get<ITMaterial>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITMaterial> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TMATERIAL,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITMaterial> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TMATERIAL,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITMaterial> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TMATERIAL,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
