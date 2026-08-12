import { useEffect, useState } from 'react';

const emptyForm = {
  name: '',
  emailId: '',
  contactNumber: '',
  address: '',
  departmentId: '',
  salary: '',
  joiningDate: '',
};

export default function EmployeeFormModal({ employee, departments, onSave, onClose }) {
  const [form, setForm] = useState(emptyForm);
  const [errors, setErrors] = useState([]);
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    if (employee) {
      setForm({
        name: employee.name,
        emailId: employee.emailId,
        contactNumber: employee.contactNumber,
        address: employee.address,
        departmentId: employee.department?.departmentId ?? '',
        salary: employee.salary,
        joiningDate: employee.joiningDate,
      });
    } else {
      setForm(emptyForm);
    }
  }, [employee]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrors([]);
    setSaving(true);
    try {
      await onSave({
        ...form,
        departmentId: Number(form.departmentId),
        salary: Number(form.salary),
      });
    } catch (err) {
      const details = err.response?.data?.details;
      const message = err.response?.data?.message;
      setErrors(details && details.length ? details : [message || 'Failed to save employee.']);
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="modal-backdrop" onClick={onClose}>
      <div className="modal-card" onClick={(e) => e.stopPropagation()}>
        <h2>{employee ? 'Edit Employee' : 'Add Employee'}</h2>

        {errors.length > 0 && (
          <div className="alert alert-error">
            <ul>
              {errors.map((err, i) => <li key={i}>{err}</li>)}
            </ul>
          </div>
        )}

        <form onSubmit={handleSubmit} className="employee-form">
          <div className="form-row">
            <label>Name</label>
            <input name="name" value={form.name} onChange={handleChange} required />
          </div>

          <div className="form-row">
            <label>Email</label>
            <input type="email" name="emailId" value={form.emailId} onChange={handleChange} required />
          </div>

          <div className="form-row">
            <label>Contact Number</label>
            <input name="contactNumber" value={form.contactNumber} onChange={handleChange}
                   pattern="\d{10}" title="10 digit phone number" required />
          </div>

          <div className="form-row">
            <label>Address</label>
            <input name="address" value={form.address} onChange={handleChange} required />
          </div>

          <div className="form-row">
            <label>Department</label>
            <select name="departmentId" value={form.departmentId} onChange={handleChange} required>
              <option value="" disabled>Select department</option>
              {departments.map((d) => (
                <option key={d.departmentId} value={d.departmentId}>{d.departmentName}</option>
              ))}
            </select>
          </div>

          <div className="form-row">
            <label>Salary</label>
            <input type="number" min="0" step="0.01" name="salary" value={form.salary} onChange={handleChange} required />
          </div>

          <div className="form-row">
            <label>Joining Date</label>
            <input type="date" name="joiningDate" value={form.joiningDate} onChange={handleChange} required />
          </div>

          <div className="modal-actions">
            <button type="button" className="btn btn-ghost" onClick={onClose}>Cancel</button>
            <button type="submit" className="btn btn-primary" disabled={saving}>
              {saving ? 'Saving...' : 'Save'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
