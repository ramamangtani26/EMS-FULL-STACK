export default function EmployeeTable({ employees, onEdit, onDelete, sortBy, order, onSortChange }) {
  const columns = [
    { key: 'employeeId', label: 'ID', sortable: false },
    { key: 'name', label: 'Name', sortable: true },
    { key: 'emailId', label: 'Email', sortable: false },
    { key: 'department', label: 'Department', sortable: false },
    { key: 'salary', label: 'Salary', sortable: true },
    { key: 'joiningDate', label: 'Joining Date', sortable: true },
  ];

  const handleHeaderClick = (col) => {
    if (!col.sortable) return;
    const nextOrder = sortBy === col.key && order === 'asc' ? 'desc' : 'asc';
    onSortChange(col.key, nextOrder);
  };

  if (employees.length === 0) {
    return <div className="empty-state">No employees found.</div>;
  }

  return (
    <div className="table-wrapper">
      <table className="employee-table">
        <thead>
          <tr>
            {columns.map((col) => (
              <th
                key={col.key}
                className={col.sortable ? 'sortable' : ''}
                onClick={() => handleHeaderClick(col)}
              >
                {col.label}
                {sortBy === col.key && (order === 'asc' ? ' ▲' : ' ▼')}
              </th>
            ))}
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {employees.map((emp) => (
            <tr key={emp.employeeId}>
              <td>{emp.employeeId}</td>
              <td>{emp.name}</td>
              <td>{emp.emailId}</td>
              <td>{emp.department?.departmentName}</td>
              <td>₹{emp.salary.toLocaleString()}</td>
              <td>{emp.joiningDate}</td>
              <td className="actions-cell">
                <button className="btn btn-small" onClick={() => onEdit(emp)}>Edit</button>
                <button className="btn btn-small btn-danger" onClick={() => onDelete(emp)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
