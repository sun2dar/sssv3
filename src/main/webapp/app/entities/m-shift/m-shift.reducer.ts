import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMShift, defaultValue } from 'app/shared/model/m-shift.model';

export const ACTION_TYPES = {
  FETCH_MSHIFT_LIST: 'mShift/FETCH_MSHIFT_LIST',
  FETCH_MSHIFT: 'mShift/FETCH_MSHIFT',
  CREATE_MSHIFT: 'mShift/CREATE_MSHIFT',
  UPDATE_MSHIFT: 'mShift/UPDATE_MSHIFT',
  DELETE_MSHIFT: 'mShift/DELETE_MSHIFT',
  SET_BLOB: 'mShift/SET_BLOB',
  RESET: 'mShift/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMShift>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MShiftState = Readonly<typeof initialState>;

// Reducer

export default (state: MShiftState = initialState, action): MShiftState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MSHIFT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MSHIFT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MSHIFT):
    case REQUEST(ACTION_TYPES.UPDATE_MSHIFT):
    case REQUEST(ACTION_TYPES.DELETE_MSHIFT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MSHIFT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MSHIFT):
    case FAILURE(ACTION_TYPES.CREATE_MSHIFT):
    case FAILURE(ACTION_TYPES.UPDATE_MSHIFT):
    case FAILURE(ACTION_TYPES.DELETE_MSHIFT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MSHIFT_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MSHIFT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MSHIFT):
    case SUCCESS(ACTION_TYPES.UPDATE_MSHIFT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MSHIFT):
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

const apiUrl = 'api/m-shifts';

// Actions

export const getEntities: ICrudGetAllAction<IMShift> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MSHIFT_LIST,
    payload: axios.get<IMShift>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMShift> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MSHIFT,
    payload: axios.get<IMShift>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMShift> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MSHIFT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMShift> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MSHIFT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMShift> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MSHIFT,
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
