import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMCustomer, defaultValue } from 'app/shared/model/m-customer.model';

export const ACTION_TYPES = {
  FETCH_MCUSTOMER_LIST: 'mCustomer/FETCH_MCUSTOMER_LIST',
  FETCH_MCUSTOMER: 'mCustomer/FETCH_MCUSTOMER',
  CREATE_MCUSTOMER: 'mCustomer/CREATE_MCUSTOMER',
  UPDATE_MCUSTOMER: 'mCustomer/UPDATE_MCUSTOMER',
  DELETE_MCUSTOMER: 'mCustomer/DELETE_MCUSTOMER',
  SET_BLOB: 'mCustomer/SET_BLOB',
  RESET: 'mCustomer/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMCustomer>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MCustomerState = Readonly<typeof initialState>;

// Reducer

export default (state: MCustomerState = initialState, action): MCustomerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MCUSTOMER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MCUSTOMER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MCUSTOMER):
    case REQUEST(ACTION_TYPES.UPDATE_MCUSTOMER):
    case REQUEST(ACTION_TYPES.DELETE_MCUSTOMER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MCUSTOMER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MCUSTOMER):
    case FAILURE(ACTION_TYPES.CREATE_MCUSTOMER):
    case FAILURE(ACTION_TYPES.UPDATE_MCUSTOMER):
    case FAILURE(ACTION_TYPES.DELETE_MCUSTOMER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MCUSTOMER_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MCUSTOMER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MCUSTOMER):
    case SUCCESS(ACTION_TYPES.UPDATE_MCUSTOMER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MCUSTOMER):
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

const apiUrl = 'api/m-customers';

// Actions

export const getEntities: ICrudGetAllAction<IMCustomer> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MCUSTOMER_LIST,
    payload: axios.get<IMCustomer>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMCustomer> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MCUSTOMER,
    payload: axios.get<IMCustomer>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMCustomer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MCUSTOMER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMCustomer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MCUSTOMER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMCustomer> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MCUSTOMER,
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
