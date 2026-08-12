import apiClient from './client';

export const getDepartments = () => apiClient.get('/departments');
export const addDepartment = (data) => apiClient.post('/departments', data);
export const updateDepartment = (id, data) => apiClient.put(`/departments/${id}`, data);
export const deleteDepartment = (id) => apiClient.delete(`/departments/${id}`);
