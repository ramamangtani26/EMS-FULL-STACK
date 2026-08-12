import apiClient from './client';

export const getEmployees = (params) => apiClient.get('/employees', { params });
export const getEmployeeById = (id) => apiClient.get(`/employees/${id}`);
export const addEmployee = (data) => apiClient.post('/employees', data);
export const updateEmployee = (id, data) => apiClient.put(`/employees/${id}`, data);
export const deleteEmployee = (id) => apiClient.delete(`/employees/${id}`);
export const searchByName = (name) => apiClient.get('/employees/search/name', { params: { name } });
export const searchByDepartment = (departmentName) =>
  apiClient.get('/employees/search/department', { params: { departmentName } });
export const searchBySalaryRange = (minSalary, maxSalary) =>
  apiClient.get('/employees/search/salary', { params: { minSalary, maxSalary } });
export const getStatistics = () => apiClient.get('/employees/statistics');
