import React from 'react';
import { Routes, Route, Link, useLocation } from 'react-router-dom';
import { LayoutDashboard, Plane, Pill, Battery, PlusCircle, Activity } from 'lucide-react';
import Dashboard from './pages/Dashboard';
import EvtolFleet from './pages/EvtolFleet';
import Medications from './pages/Medications';
import LoadMedication from './pages/LoadMedication';
import HealthMonitor from './pages/HealthMonitor';

function App() {
  const location = useLocation();

  const isActive = (path) => location.pathname === path;

  return (
    <div className="app-container">
      <header className="header glass container">
        <div className="brand" style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
          <Plane size={32} color="#2563eb" />
          <h1 style={{ fontSize: '1.5rem', fontWeight: 800 }}>SkyMed Dispatch</h1>
        </div>
        <nav className="nav">
          <Link to="/" className={`nav-link ${isActive('/') ? 'active' : ''}`}>
             Dashboard
          </Link>
          <Link to="/fleet" className={`nav-link ${isActive('/fleet') ? 'active' : ''}`}>
             Fleet
          </Link>
          <Link to="/meds" className={`nav-link ${isActive('/meds') ? 'active' : ''}`}>
             Meds
          </Link>
          <Link to="/load" className={`nav-link ${isActive('/load') ? 'active' : ''}`}>
             Dispatch
          </Link>
          <Link to="/health" className={`nav-link ${isActive('/health') ? 'active' : ''}`}>
             Health
          </Link>
        </nav>
      </header>

      <main>
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/fleet" element={<EvtolFleet />} />
          <Route path="/meds" element={<Medications />} />
          <Route path="/load" element={<LoadMedication />} />
          <Route path="/health" element={<HealthMonitor />} />
        </Routes>
      </main>

      <footer className="container" style={{ textAlign: 'center', marginTop: '4rem', color: '#64748b', paddingBottom: '2rem' }}>
        <p>© 2026 SkyMed Transportation Systems. Sustainable. Efficient. Equitable.</p>
      </footer>
    </div>
  );
}

export default App;
