import { useEffect, useState, useCallback } from 'react';
import Navbar from '../components/Navbar';
import EmployeeTable from '../components/EmployeeTable';
import EmployeeFormModal from '../components/EmployeeFormModal';
import StatisticsPanel from '../components/StatisticsPanel';
import {
  getEmployees, addEmployee, updateEmployee, deleteEmployee,
  searchByName, searchByDepartment, searchBySalaryRange, getStatistics,
} from '../api/employeeApi';
import { getDepartments } from '../api/departmentApi';

export default function Dashboard() {
  const [employees, setEmployees] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const [sortBy, setSortBy] = useState('');
  const [order, setOrder] = useState('asc');

  const [nameFilter, setNameFilter] = useState('');
  const [deptFilter, setDeptFilter] = useState('');
  const [minSalary, setMinSalary] = useState('');
  const [maxSalary, setMaxSalary] = useState('');

  const [modalOpen, setModalOpen] = useState(false);
  const [editingEmployee, setEditingEmployee] = useState(null);

  const loadEmployees = useCallback(async () => {
    setLoading(true);
    setError('');
    try {
      let res;
      if (nameFilter.trim()) {
        res = await searchByName(nameFilter.trim());
      } else if (deptFilter) {
        res = await searchByDepartment(deptFilter);
      } else if (minSalary !== '' && maxSalary !== '') {
        res = await searchBySalaryRange(Number(minSalary), Number(maxSalary));
      } else {
        res = await getEmployees({ sortBy, order });
      }
      setEmployees(res.data);
    } catch (err) {
      setError('Failed to load employees. Is the backend running?');
    } finally {
      setLoading(false);
    }
  }, [sortBy, order, nameFilter, deptFilter, minSalary, maxSalary]);

  const loadDepartments = useCallback(async () => {
    try {
      const res = await getDepartments();
      setDepartments(res.data);
    } catch {
      // Non-fatal — the add/edit form will just show an empty department list
    }
  }, []);

  const loadStats = useCallback(async () => {
    try {
      const res = await getStatistics();
      setStats(res.data);
    } catch {
      // Non-fatal
    }
  }, []);

  useEffect(() => { loadDepartments(); }, [loadDepartments]);
  useEffect(() => { loadEmployees(); loadStats(); }, [loadEmployees, loadStats]);

  const handleSortChange = (key, nextOrder) => {
    setSortBy(key);
    setOrder(nextOrder);
  };

  const clearFilters = () => {
    setNameFilter('');
    setDeptFilter('');
    setMinSalary('');
    setMaxSalary('');
  };

  const openAddModal = () => {
    setEditingEmployee(null);
    setModalOpen(true);
  };

  const openEditModal = (emp) => {
    setEditingEmployee(emp);
    setModalOpen(true);
  };

  const handleSave = async (formData) => {
    if (editingEmployee) {
      await updateEmployee(editingEmployee.employeeId, formData);
    } else {
      await addEmployee(formData);
    }
    setModalOpen(false);
    loadEmployees();
    loadStats();
  };

  const handleDelete = async (emp) => {
    if (!window.confirm(`Delete employee "${emp.name}"?`)) return;
    try {
      await deleteEmployee(emp.employeeId);
      loadEmployees();
      loadStats();
    } catch {
      setError('Failed to delete employee.');
    }
  };

  return (
    <div>
      <Navbar />
      <main className="page">
        <div className="page-header">
          <h1>Employees</h1>
          <button className="btn btn-primary" onClick={openAddModal}>+ Add Employee</button>
        </div>

        <StatisticsPanel stats={stats} />

        <div className="filter-bar">
          <input
            placeholder="Search by name"
            value={nameFilter}
            onChange={(e) => { setNameFilter(e.target.value); setDeptFilter(''); setMinSalary(''); setMaxSalary(''); }}
          />
          <select
            value={deptFilter}
            onChange={(e) => { setDeptFilter(e.target.value); setNameFilter(''); setMinSalary(''); setMaxSalary(''); }}
          >
            <option value="">All departments</option>
            {departments.map((d) => (
              <option key={d.departmentId} value={d.departmentName}>{d.departmentName}</option>
            ))}
          </select>
          <input
            type="number" placeholder="Min salary"
            value={minSalary}
            onChange={(e) => { setMinSalary(e.target.value); setNameFilter(''); setDeptFilter(''); }}
          />
          <input
            type="number" placeholder="Max salary"
            value={maxSalary}
            onChange={(e) => { setMaxSalary(e.target.value); setNameFilter(''); setDeptFilter(''); }}
          />
          <button className="btn btn-ghost" onClick={clearFilters}>Clear</button>
        </div>

        {error && <div className="alert alert-error">{error}</div>}

        {loading ? (
          <div className="empty-state">Loading...</div>
        ) : (
          <EmployeeTable
            employees={employees}
            onEdit={openEditModal}
            onDelete={handleDelete}
            sortBy={sortBy}
            order={order}
            onSortChange={handleSortChange}
          />
        )}

        {modalOpen && (
          <EmployeeFormModal
            employee={editingEmployee}
            departments={departments}
            onSave={handleSave}
            onClose={() => setModalOpen(false)}
          />
        )}
      </main>
    </div>
  );
}
