import apiClient from './client';

export const giveAdvance = (data) => apiClient.post('/advances', data);
export const getOutstandingAdvances = () => apiClient.get('/advances');
export const getAdvancesForEmployee = (employeeId) => apiClient.get(`/advances/employee/${employeeId}`);
export const getOutstandingTotal = (employeeId) => apiClient.get(`/advances/employee/${employeeId}/total`);
export const recordDeduction = (advanceId, amount) =>
  apiClient.post(`/advances/${advanceId}/deduct`, null, { params: { amount } });
export const deleteAdvance = (advanceId) => apiClient.delete(`/advances/${advanceId}`);
