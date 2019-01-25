import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMMaterialType, defaultValue } from 'app/shared/model/m-material-type.model';

export const ACTION_TYPES = {
  FETCH_MMATERIALTYPE_LIST: 'mMaterialType/FETCH_MMATERIALTYPE_LIST',
  FETCH_MMATERIALTYPE: 'mMaterialType/FETCH_MMATERIALTYPE',
  CREATE_MMATERIALTYPE: 'mMaterialType/CREATE_MMATERIALTYPE',
  UPDATE_MMATERIALTYPE: 'mMaterialType/UPDATE_MMATERIALTYPE',
  DELETE_MMATERIALTYPE: 'mMaterialType/DELETE_MMATERIALTYPE',
  SET_BLOB: 'mMaterialType/SET_BLOB',
  RESET: 'mMaterialType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMMaterialType>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MMaterialTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: MMaterialTypeState = initialState, action): MMaterialTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MMATERIALTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MMATERIALTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MMATERIALTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_MMATERIALTYPE):
    case REQUEST(ACTION_TYPES.DELETE_MMATERIALTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MMATERIALTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MMATERIALTYPE):
    case FAILURE(ACTION_TYPES.CREATE_MMATERIALTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_MMATERIALTYPE):
    case FAILURE(ACTION_TYPES.DELETE_MMATERIALTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MMATERIALTYPE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MMATERIALTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MMATERIALTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_MMATERIALTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MMATERIALTYPE):
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

const apiUrl = 'api/m-material-types';

// Actions

export const getEntities: ICrudGetAllAction<IMMaterialType> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MMATERIALTYPE_LIST,
    payload: axios.get<IMMaterialType>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMMaterialType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MMATERIALTYPE,
    payload: axios.get<IMMaterialType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMMaterialType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MMATERIALTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMMaterialType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MMATERIALTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMMaterialType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MMATERIALTYPE,
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
