import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMOperasionalType, defaultValue } from 'app/shared/model/m-operasional-type.model';

export const ACTION_TYPES = {
  FETCH_MOPERASIONALTYPE_LIST: 'mOperasionalType/FETCH_MOPERASIONALTYPE_LIST',
  FETCH_MOPERASIONALTYPE: 'mOperasionalType/FETCH_MOPERASIONALTYPE',
  CREATE_MOPERASIONALTYPE: 'mOperasionalType/CREATE_MOPERASIONALTYPE',
  UPDATE_MOPERASIONALTYPE: 'mOperasionalType/UPDATE_MOPERASIONALTYPE',
  DELETE_MOPERASIONALTYPE: 'mOperasionalType/DELETE_MOPERASIONALTYPE',
  SET_BLOB: 'mOperasionalType/SET_BLOB',
  RESET: 'mOperasionalType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMOperasionalType>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MOperasionalTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: MOperasionalTypeState = initialState, action): MOperasionalTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MOPERASIONALTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MOPERASIONALTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MOPERASIONALTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_MOPERASIONALTYPE):
    case REQUEST(ACTION_TYPES.DELETE_MOPERASIONALTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MOPERASIONALTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MOPERASIONALTYPE):
    case FAILURE(ACTION_TYPES.CREATE_MOPERASIONALTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_MOPERASIONALTYPE):
    case FAILURE(ACTION_TYPES.DELETE_MOPERASIONALTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MOPERASIONALTYPE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MOPERASIONALTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MOPERASIONALTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_MOPERASIONALTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MOPERASIONALTYPE):
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

const apiUrl = 'api/m-operasional-types';

// Actions

export const getEntities: ICrudGetAllAction<IMOperasionalType> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MOPERASIONALTYPE_LIST,
    payload: axios.get<IMOperasionalType>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMOperasionalType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MOPERASIONALTYPE,
    payload: axios.get<IMOperasionalType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMOperasionalType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MOPERASIONALTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMOperasionalType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MOPERASIONALTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMOperasionalType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MOPERASIONALTYPE,
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
