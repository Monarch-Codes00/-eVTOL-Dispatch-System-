import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Plane, Pill, Battery, Activity } from 'lucide-react';
import { motion } from 'framer-motion';

const Dashboard = () => {
  const [stats, setStats] = useState({
    totalEvtols: 0,
    availableEvtols: 0,
    totalMeds: 0,
    lowBattery: 0,
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [evtolsRes, availableRes, medsRes] = await Promise.all([
          axios.get('/api/evtol/all'),
          axios.get('/api/evtol/available'),
          axios.get('/api/medications')
        ]);

        const evtols = evtolsRes.data;
        setStats({
          totalEvtols: evtols.length,
          availableEvtols: availableRes.data.length,
          totalMeds: medsRes.data.length,
          lowBattery: evtols.filter(e => e.batteryCapacity < 25).length,
        });
      } catch (err) {
        console.error("Error fetching dashboard stats", err);
      }
    };
    fetchData();
  }, []);

  return (
    <div className="container">
      <div style={{ marginBottom: '2rem' }}>
        <h2 style={{ fontSize: '2rem', fontWeight: 700 }}>Fleet Overview</h2>
        <p style={{ color: '#64748b' }}>Real-time status of your medical delivery network</p>
      </div>

      <div className="grid">
        <StatCard 
          icon={<Plane color="#2563eb" />} 
          title="Total Fleet" 
          value={stats.totalEvtols} 
          subtitle="Active eVTOLs"
        />
        <StatCard 
          icon={<Activity color="#22c55e" />} 
          title="Ready for Load" 
          value={stats.availableEvtols} 
          subtitle="Battery > 25% & Idle"
        />
        <StatCard 
          icon={<Pill color="#0ea5e9" />} 
          title="Medications" 
          value={stats.totalMeds} 
          subtitle="Items in inventory"
        />
        <StatCard 
          icon={<Battery color="#ef4444" />} 
          title="Maintenance" 
          value={stats.lowBattery} 
          subtitle="Low battery units"
        />
      </div>

      <div style={{ marginTop: '3rem' }} className="glass card">
        <h3>Operational Insights</h3>
        <p style={{ marginTop: '1rem', color: '#64748b' }}>
          Welcome to the SkyMed eVTOL Dispatch Controller. From here you can manage your fleet, 
          register new units, and handle medication shipments to remote locations. 
          Use the navigation above to access specific modules.
        </p>
      </div>
    </div>
  );
};

const StatCard = ({ icon, title, value, subtitle }) => (
  <motion.div 
    whileHover={{ scale: 1.02 }}
    className="glass card"
  >
    <div style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '1rem' }}>
      <div style={{ padding: '0.75rem', borderRadius: '12px', background: '#f1f5f9' }}>
        {icon}
      </div>
      <div>
        <h4 style={{ fontSize: '0.9rem', color: '#64748b' }}>{title}</h4>
        <p style={{ fontSize: '1.75rem', fontWeight: 800 }}>{value}</p>
      </div>
    </div>
    <p style={{ fontSize: '0.8rem', color: '#94a3b8' }}>{subtitle}</p>
  </motion.div>
);

export default Dashboard;
