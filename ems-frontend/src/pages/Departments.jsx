import { useEffect, useState } from 'react';
import Navbar from '../components/Navbar';
import { getDepartments, addDepartment, updateDepartment, deleteDepartment } from '../api/departmentApi';

const emptyForm = { departmentName: '', location: '' };

export default function Departments() {
  const [departments, setDepartments] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [editingId, setEditingId] = useState(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  const load = async () => {
    setLoading(true);
    try {
      const res = await getDepartments();
      setDepartments(res.data);
    } catch {
      setError('Failed to load departments.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { load(); }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      if (editingId) {
        await updateDepartment(editingId, form);
      } else {
        await addDepartment(form);
      }
      setForm(emptyForm);
      setEditingId(null);
      load();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to save department.');
    }
  };

  const startEdit = (dept) => {
    setEditingId(dept.departmentId);
    setForm({ departmentName: dept.departmentName, location: dept.location });
  };

  const cancelEdit = () => {
    setEditingId(null);
    setForm(emptyForm);
  };

  const handleDelete = async (dept) => {
    if (!window.confirm(`Delete department "${dept.departmentName}"? Employees in this department must be reassigned first.`)) return;
    try {
      await deleteDepartment(dept.departmentId);
      load();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to delete department.');
    }
  };

  return (
    <div>
      <Navbar />
      <main className="page">
        <div className="page-header">
          <h1>Departments</h1>
        </div>

        {error && <div className="alert alert-error">{error}</div>}

        <form className="inline-form" onSubmit={handleSubmit}>
          <input
            placeholder="Department name"
            value={form.departmentName}
            onChange={(e) => setForm({ ...form, departmentName: e.target.value })}
            required
          />
          <input
            placeholder="Location"
            value={form.location}
            onChange={(e) => setForm({ ...form, location: e.target.value })}
            required
          />
          <button type="submit" className="btn btn-primary">{editingId ? 'Update' : 'Add'}</button>
          {editingId && <button type="button" className="btn btn-ghost" onClick={cancelEdit}>Cancel</button>}
        </form>

        {loading ? (
          <div className="empty-state">Loading...</div>
        ) : departments.length === 0 ? (
          <div className="empty-state">No departments yet.</div>
        ) : (
          <div className="table-wrapper">
            <table className="employee-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Location</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {departments.map((d) => (
                  <tr key={d.departmentId}>
                    <td>{d.departmentId}</td>
                    <td>{d.departmentName}</td>
                    <td>{d.location}</td>
                    <td className="actions-cell">
                      <button className="btn btn-small" onClick={() => startEdit(d)}>Edit</button>
                      <button className="btn btn-small btn-danger" onClick={() => handleDelete(d)}>Delete</button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </main>
    </div>
  );
}
