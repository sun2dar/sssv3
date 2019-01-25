import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMPlywoodCategory, defaultValue } from 'app/shared/model/m-plywood-category.model';

export const ACTION_TYPES = {
  FETCH_MPLYWOODCATEGORY_LIST: 'mPlywoodCategory/FETCH_MPLYWOODCATEGORY_LIST',
  FETCH_MPLYWOODCATEGORY: 'mPlywoodCategory/FETCH_MPLYWOODCATEGORY',
  CREATE_MPLYWOODCATEGORY: 'mPlywoodCategory/CREATE_MPLYWOODCATEGORY',
  UPDATE_MPLYWOODCATEGORY: 'mPlywoodCategory/UPDATE_MPLYWOODCATEGORY',
  DELETE_MPLYWOODCATEGORY: 'mPlywoodCategory/DELETE_MPLYWOODCATEGORY',
  SET_BLOB: 'mPlywoodCategory/SET_BLOB',
  RESET: 'mPlywoodCategory/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMPlywoodCategory>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MPlywoodCategoryState = Readonly<typeof initialState>;

// Reducer

export default (state: MPlywoodCategoryState = initialState, action): MPlywoodCategoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MPLYWOODCATEGORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MPLYWOODCATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MPLYWOODCATEGORY):
    case REQUEST(ACTION_TYPES.UPDATE_MPLYWOODCATEGORY):
    case REQUEST(ACTION_TYPES.DELETE_MPLYWOODCATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MPLYWOODCATEGORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MPLYWOODCATEGORY):
    case FAILURE(ACTION_TYPES.CREATE_MPLYWOODCATEGORY):
    case FAILURE(ACTION_TYPES.UPDATE_MPLYWOODCATEGORY):
    case FAILURE(ACTION_TYPES.DELETE_MPLYWOODCATEGORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MPLYWOODCATEGORY_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MPLYWOODCATEGORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MPLYWOODCATEGORY):
    case SUCCESS(ACTION_TYPES.UPDATE_MPLYWOODCATEGORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MPLYWOODCATEGORY):
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

const apiUrl = 'api/m-plywood-categories';

// Actions

export const getEntities: ICrudGetAllAction<IMPlywoodCategory> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MPLYWOODCATEGORY_LIST,
    payload: axios.get<IMPlywoodCategory>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMPlywoodCategory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MPLYWOODCATEGORY,
    payload: axios.get<IMPlywoodCategory>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMPlywoodCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MPLYWOODCATEGORY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMPlywoodCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MPLYWOODCATEGORY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMPlywoodCategory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MPLYWOODCATEGORY,
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
