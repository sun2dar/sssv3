import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMPajak, defaultValue } from 'app/shared/model/m-pajak.model';

export const ACTION_TYPES = {
  FETCH_MPAJAK_LIST: 'mPajak/FETCH_MPAJAK_LIST',
  FETCH_MPAJAK: 'mPajak/FETCH_MPAJAK',
  CREATE_MPAJAK: 'mPajak/CREATE_MPAJAK',
  UPDATE_MPAJAK: 'mPajak/UPDATE_MPAJAK',
  DELETE_MPAJAK: 'mPajak/DELETE_MPAJAK',
  SET_BLOB: 'mPajak/SET_BLOB',
  RESET: 'mPajak/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMPajak>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MPajakState = Readonly<typeof initialState>;

// Reducer

export default (state: MPajakState = initialState, action): MPajakState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MPAJAK_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MPAJAK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MPAJAK):
    case REQUEST(ACTION_TYPES.UPDATE_MPAJAK):
    case REQUEST(ACTION_TYPES.DELETE_MPAJAK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MPAJAK_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MPAJAK):
    case FAILURE(ACTION_TYPES.CREATE_MPAJAK):
    case FAILURE(ACTION_TYPES.UPDATE_MPAJAK):
    case FAILURE(ACTION_TYPES.DELETE_MPAJAK):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MPAJAK_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MPAJAK):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MPAJAK):
    case SUCCESS(ACTION_TYPES.UPDATE_MPAJAK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MPAJAK):
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

const apiUrl = 'api/m-pajaks';

// Actions

export const getEntities: ICrudGetAllAction<IMPajak> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MPAJAK_LIST,
    payload: axios.get<IMPajak>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMPajak> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MPAJAK,
    payload: axios.get<IMPajak>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMPajak> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MPAJAK,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMPajak> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MPAJAK,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMPajak> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MPAJAK,
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
