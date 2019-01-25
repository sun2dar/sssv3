import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMLogCategory, defaultValue } from 'app/shared/model/m-log-category.model';

export const ACTION_TYPES = {
  FETCH_MLOGCATEGORY_LIST: 'mLogCategory/FETCH_MLOGCATEGORY_LIST',
  FETCH_MLOGCATEGORY: 'mLogCategory/FETCH_MLOGCATEGORY',
  CREATE_MLOGCATEGORY: 'mLogCategory/CREATE_MLOGCATEGORY',
  UPDATE_MLOGCATEGORY: 'mLogCategory/UPDATE_MLOGCATEGORY',
  DELETE_MLOGCATEGORY: 'mLogCategory/DELETE_MLOGCATEGORY',
  RESET: 'mLogCategory/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMLogCategory>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MLogCategoryState = Readonly<typeof initialState>;

// Reducer

export default (state: MLogCategoryState = initialState, action): MLogCategoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MLOGCATEGORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MLOGCATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MLOGCATEGORY):
    case REQUEST(ACTION_TYPES.UPDATE_MLOGCATEGORY):
    case REQUEST(ACTION_TYPES.DELETE_MLOGCATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MLOGCATEGORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MLOGCATEGORY):
    case FAILURE(ACTION_TYPES.CREATE_MLOGCATEGORY):
    case FAILURE(ACTION_TYPES.UPDATE_MLOGCATEGORY):
    case FAILURE(ACTION_TYPES.DELETE_MLOGCATEGORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MLOGCATEGORY_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MLOGCATEGORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MLOGCATEGORY):
    case SUCCESS(ACTION_TYPES.UPDATE_MLOGCATEGORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MLOGCATEGORY):
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

const apiUrl = 'api/m-log-categories';

// Actions

export const getEntities: ICrudGetAllAction<IMLogCategory> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MLOGCATEGORY_LIST,
    payload: axios.get<IMLogCategory>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMLogCategory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MLOGCATEGORY,
    payload: axios.get<IMLogCategory>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMLogCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MLOGCATEGORY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMLogCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MLOGCATEGORY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMLogCategory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MLOGCATEGORY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
