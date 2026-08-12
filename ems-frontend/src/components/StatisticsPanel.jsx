export default function StatisticsPanel({ stats }) {
  if (!stats) return null;

  return (
    <div className="stats-grid">
      <div className="stat-card">
        <span className="stat-label">Total Employees</span>
        <span className="stat-value">{stats.totalEmployees}</span>
      </div>
      <div className="stat-card">
        <span className="stat-label">Highest Salary</span>
        <span className="stat-value">₹{stats.highestSalary.toLocaleString()}</span>
      </div>
      <div className="stat-card">
        <span className="stat-label">Lowest Salary</span>
        <span className="stat-value">₹{stats.lowestSalary.toLocaleString()}</span>
      </div>
      <div className="stat-card">
        <span className="stat-label">Average Salary</span>
        <span className="stat-value">₹{Math.round(stats.averageSalary).toLocaleString()}</span>
      </div>
      <div className="stat-card stat-card-wide">
        <span className="stat-label">Department-wise Count</span>
        <div className="dept-count-list">
          {Object.entries(stats.departmentWiseCount).map(([dept, count]) => (
            <span key={dept} className="dept-count-chip">
              {dept}: {count}
            </span>
          ))}
        </div>
      </div>
    </div>
  );
}
