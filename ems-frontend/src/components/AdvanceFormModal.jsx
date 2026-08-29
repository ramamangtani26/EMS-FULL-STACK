import { useState } from 'react';

const emptyForm = {
  amount: '',
  dateGiven: new Date().toISOString().slice(0, 10),
  reason: '',
};

export default function AdvanceFormModal({ employee, onSave, onClose }) {
  const [form, setForm] = useState(emptyForm);
  const [errors, setErrors] = useState([]);
  const [saving, setSaving] = useState(false);

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
        amount: Number(form.amount),
      });
    } catch (err) {
      const details = err.response?.data?.details;
      const message = err.response?.data?.message;
      setErrors(details && details.length ? details : [message || 'Failed to give advance.']);
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="modal-backdrop" onClick={onClose}>
      <div className="modal-card" onClick={(e) => e.stopPropagation()}>
        <h2>Give Advance to {employee?.name}</h2>

        {errors.length > 0 && (
          <div className="alert alert-error">
            <ul>
              {errors.map((err, i) => <li key={i}>{err}</li>)}
            </ul>
          </div>
        )}

        <form onSubmit={handleSubmit} className="employee-form">
          <div className="form-row">
            <label>Amount (₹)</label>
            <input type="number" min="1" step="0.01" name="amount" value={form.amount} onChange={handleChange} required />
          </div>

          <div className="form-row">
            <label>Date Given</label>
            <input type="date" name="dateGiven" value={form.dateGiven} onChange={handleChange} required />
          </div>

          <div className="form-row">
            <label>Reason (optional)</label>
            <input name="reason" value={form.reason} onChange={handleChange} />
          </div>

          <div className="modal-actions">
            <button type="button" className="btn btn-ghost" onClick={onClose}>Cancel</button>
            <button type="submit" className="btn btn-primary" disabled={saving}>
              {saving ? 'Saving...' : 'Give Advance'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
