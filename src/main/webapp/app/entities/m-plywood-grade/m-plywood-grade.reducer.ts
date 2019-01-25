import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMPlywoodGrade, defaultValue } from 'app/shared/model/m-plywood-grade.model';

export const ACTION_TYPES = {
  FETCH_MPLYWOODGRADE_LIST: 'mPlywoodGrade/FETCH_MPLYWOODGRADE_LIST',
  FETCH_MPLYWOODGRADE: 'mPlywoodGrade/FETCH_MPLYWOODGRADE',
  CREATE_MPLYWOODGRADE: 'mPlywoodGrade/CREATE_MPLYWOODGRADE',
  UPDATE_MPLYWOODGRADE: 'mPlywoodGrade/UPDATE_MPLYWOODGRADE',
  DELETE_MPLYWOODGRADE: 'mPlywoodGrade/DELETE_MPLYWOODGRADE',
  SET_BLOB: 'mPlywoodGrade/SET_BLOB',
  RESET: 'mPlywoodGrade/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMPlywoodGrade>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MPlywoodGradeState = Readonly<typeof initialState>;

// Reducer

export default (state: MPlywoodGradeState = initialState, action): MPlywoodGradeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MPLYWOODGRADE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MPLYWOODGRADE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MPLYWOODGRADE):
    case REQUEST(ACTION_TYPES.UPDATE_MPLYWOODGRADE):
    case REQUEST(ACTION_TYPES.DELETE_MPLYWOODGRADE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MPLYWOODGRADE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MPLYWOODGRADE):
    case FAILURE(ACTION_TYPES.CREATE_MPLYWOODGRADE):
    case FAILURE(ACTION_TYPES.UPDATE_MPLYWOODGRADE):
    case FAILURE(ACTION_TYPES.DELETE_MPLYWOODGRADE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MPLYWOODGRADE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MPLYWOODGRADE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MPLYWOODGRADE):
    case SUCCESS(ACTION_TYPES.UPDATE_MPLYWOODGRADE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MPLYWOODGRADE):
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

const apiUrl = 'api/m-plywood-grades';

// Actions

export const getEntities: ICrudGetAllAction<IMPlywoodGrade> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MPLYWOODGRADE_LIST,
    payload: axios.get<IMPlywoodGrade>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMPlywoodGrade> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MPLYWOODGRADE,
    payload: axios.get<IMPlywoodGrade>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMPlywoodGrade> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MPLYWOODGRADE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMPlywoodGrade> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MPLYWOODGRADE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMPlywoodGrade> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MPLYWOODGRADE,
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
