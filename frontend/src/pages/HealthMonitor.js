import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { ShieldCheck, History, Clock } from 'lucide-react';
import { motion } from 'framer-motion';

const HealthMonitor = () => {
  const [logs, setLogs] = useState([]);

  const fetchLogs = async () => {
    try {
      const res = await axios.get('/api/health/logs');
      setLogs(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchLogs();
    const interval = setInterval(fetchLogs, 30000); // Poll every 30s
    return () => clearInterval(interval);
  }, []);

  return (
    <div className="container">
      <div style={{ marginBottom: '2rem' }}>
        <h2 style={{ fontSize: '2rem', fontWeight: 700 }}>Health & Audit</h2>
        <p style={{ color: '#64748b' }}>Periodic battery checks and operational logs</p>
      </div>

      <div className="glass card" style={{ padding: 0 }}>
        <div style={{ padding: '1.5rem', borderBottom: '1px solid #e2e8f0', display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
          <History size={20} color="#2563eb" />
          <h3 style={{ fontSize: '1.1rem' }}>Battery Status Audit Logs</h3>
        </div>
        
        <div style={{ overflowX: 'auto' }}>
          <table style={{ width: '100%', borderCollapse: 'collapse' }}>
            <thead>
              <tr style={{ textAlign: 'left', background: '#f8fafc' }}>
                <th style={{ padding: '1rem', fontSize: '0.85rem', color: '#64748b' }}>TIME</th>
                <th style={{ padding: '1rem', fontSize: '0.85rem', color: '#64748b' }}>eVTOL SN</th>
                <th style={{ padding: '1rem', fontSize: '0.85rem', color: '#64748b' }}>BATTERY %</th>
                <th style={{ padding: '1rem', fontSize: '0.85rem', color: '#64748b' }}>STATE</th>
                <th style={{ padding: '1rem', fontSize: '0.85rem', color: '#64748b' }}>STATUS</th>
              </tr>
            </thead>
            <tbody>
              {logs.length === 0 ? (
                <tr><td colSpan="5" style={{ padding: '2rem', textAlign: 'center', color: '#94a3b8' }}>No logs captured yet... (Wait for scheduler task)</td></tr>
              ) : (
                logs.map((log) => (
                  <tr key={log.id} style={{ borderTop: '1px solid #f1f5f9' }}>
                    <td style={{ padding: '1rem', fontSize: '0.9rem' }}>
                      <span style={{ display: 'flex', alignItems: 'center', gap: '0.4rem' }}>
                        <Clock size={14} color="#94a3b8" />
                        {new Date(log.timestamp).toLocaleTimeString()}
                      </span>
                    </td>
                    <td style={{ padding: '1rem', fontWeight: 600 }}>{log.evtolSerialNumber}</td>
                    <td style={{ padding: '1rem' }}>
                      <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                        <span>{log.batteryLevel}%</span>
                        <div style={{ width: '40px', height: '6px', background: '#e2e8f0', borderRadius: '3px' }}>
                          <div style={{ 
                            width: `${log.batteryLevel}%`, 
                            height: '100%', 
                            background: log.batteryLevel < 25 ? '#ef4444' : '#22c55e',
                            borderRadius: '3px'
                          }} />
                        </div>
                      </div>
                    </td>
                    <td style={{ padding: '1rem', fontSize: '0.85rem' }}>{log.evtolState}</td>
                    <td style={{ padding: '1rem' }}>
                      <span style={{ 
                        color: log.batteryLevel < 25 ? '#ef4444' : '#22c55e',
                        display: 'flex',
                        alignItems: 'center',
                        gap: '0.4rem',
                        fontSize: '0.85rem'
                      }}>
                        <ShieldCheck size={16} /> 
                        {log.batteryLevel < 25 ? 'Critical' : 'Healthy'}
                      </span>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default HealthMonitor;
