import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMVeneerCategory, defaultValue } from 'app/shared/model/m-veneer-category.model';

export const ACTION_TYPES = {
  FETCH_MVENEERCATEGORY_LIST: 'mVeneerCategory/FETCH_MVENEERCATEGORY_LIST',
  FETCH_MVENEERCATEGORY: 'mVeneerCategory/FETCH_MVENEERCATEGORY',
  CREATE_MVENEERCATEGORY: 'mVeneerCategory/CREATE_MVENEERCATEGORY',
  UPDATE_MVENEERCATEGORY: 'mVeneerCategory/UPDATE_MVENEERCATEGORY',
  DELETE_MVENEERCATEGORY: 'mVeneerCategory/DELETE_MVENEERCATEGORY',
  SET_BLOB: 'mVeneerCategory/SET_BLOB',
  RESET: 'mVeneerCategory/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMVeneerCategory>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MVeneerCategoryState = Readonly<typeof initialState>;

// Reducer

export default (state: MVeneerCategoryState = initialState, action): MVeneerCategoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MVENEERCATEGORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MVENEERCATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MVENEERCATEGORY):
    case REQUEST(ACTION_TYPES.UPDATE_MVENEERCATEGORY):
    case REQUEST(ACTION_TYPES.DELETE_MVENEERCATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MVENEERCATEGORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MVENEERCATEGORY):
    case FAILURE(ACTION_TYPES.CREATE_MVENEERCATEGORY):
    case FAILURE(ACTION_TYPES.UPDATE_MVENEERCATEGORY):
    case FAILURE(ACTION_TYPES.DELETE_MVENEERCATEGORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MVENEERCATEGORY_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MVENEERCATEGORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MVENEERCATEGORY):
    case SUCCESS(ACTION_TYPES.UPDATE_MVENEERCATEGORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MVENEERCATEGORY):
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

const apiUrl = 'api/m-veneer-categories';

// Actions

export const getEntities: ICrudGetAllAction<IMVeneerCategory> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MVENEERCATEGORY_LIST,
    payload: axios.get<IMVeneerCategory>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMVeneerCategory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MVENEERCATEGORY,
    payload: axios.get<IMVeneerCategory>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMVeneerCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MVENEERCATEGORY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMVeneerCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MVENEERCATEGORY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMVeneerCategory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MVENEERCATEGORY,
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
