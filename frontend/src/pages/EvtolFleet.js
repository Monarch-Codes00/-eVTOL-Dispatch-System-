import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Plus, Plane, Battery, Weight } from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';

const EvtolFleet = () => {
  const [evtols, setEvtols] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [newDrone, setNewDrone] = useState({
    serialNumber: '',
    model: 'LIGHTWEIGHT',
    weightLimit: 300,
    batteryCapacity: 100
  });

  const fetchEvtols = async () => {
    try {
      const res = await axios.get('/api/evtol/all');
      setEvtols(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchEvtols();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('/api/evtol/register', newDrone);
      setShowModal(false);
      fetchEvtols();
      setNewDrone({ serialNumber: '', model: 'LIGHTWEIGHT', weightLimit: 300, batteryCapacity: 100 });
    } catch (err) {
      alert(err.response?.data?.message || "Error registering drone");
    }
  };

  return (
    <div className="container">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
        <div>
          <h2 style={{ fontSize: '2rem', fontWeight: 700 }}>Fleet Management</h2>
          <p style={{ color: '#64748b' }}>Register and monitor eVTOL units</p>
        </div>
        <button className="btn btn-primary" onClick={() => setShowModal(true)}>
          <Plus size={20} /> Register Drone
        </button>
      </div>

      <div className="grid">
        {evtols.map((drone) => (
          <DroneCard key={drone.id} drone={drone} />
        ))}
      </div>

      <AnimatePresence>
        {showModal && (
          <div style={{
            position: 'fixed', top: 0, left: 0, width: '100%', height: '100%',
            background: 'rgba(0,0,0,0.5)', display: 'flex', justifyContent: 'center', 
            alignItems: 'center', zIndex: 1000, backdropFilter: 'blur(4px)'
          }}>
            <motion.div 
              initial={{ scale: 0.9, opacity: 0 }}
              animate={{ scale: 1, opacity: 1 }}
              exit={{ scale: 0.9, opacity: 0 }}
              className="glass card" 
              style={{ width: '100%', maxWidth: '500px', background: 'white' }}
            >
              <h3 style={{ marginBottom: '1.5rem' }}>Register New eVTOL</h3>
              <form onSubmit={handleSubmit}>
                <div className="input-group">
                  <label>Serial Number (Max 100 chars)</label>
                  <input 
                    required maxLength="100"
                    placeholder="e.g. SN-XYZ-001"
                    value={newDrone.serialNumber}
                    onChange={(e) => setNewDrone({...newDrone, serialNumber: e.target.value})}
                  />
                </div>
                <div className="input-group">
                  <label>Model</label>
                  <select 
                    value={newDrone.model}
                    onChange={(e) => {
                      const model = e.target.value;
                      let limit = 300;
                      if(model === 'MIDDLEWEIGHT') limit = 400;
                      if(model === 'CRUISERWEIGHT') limit = 500;
                      if(model === 'HEAVYWEIGHT') limit = 500;
                      setNewDrone({...newDrone, model, weightLimit: limit});
                    }}
                  >
                    <option value="LIGHTWEIGHT">Lightweight (300g)</option>
                    <option value="MIDDLEWEIGHT">Middleweight (400g)</option>
                    <option value="CRUISERWEIGHT">Cruiserweight (500g)</option>
                    <option value="HEAVYWEIGHT">Heavyweight (500g)</option>
                  </select>
                </div>
                <div className="input-group">
                  <label>Battery Capacity (%)</label>
                  <input 
                    type="number" min="0" max="100"
                    value={newDrone.batteryCapacity}
                    onChange={(e) => setNewDrone({...newDrone, batteryCapacity: parseInt(e.target.value)})}
                  />
                </div>
                <div style={{ display: 'flex', gap: '1rem', marginTop: '2rem' }}>
                  <button type="submit" className="btn btn-primary" style={{ flex: 1 }}>Register</button>
                  <button type="button" className="btn" style={{ flex: 1, background: '#f1f5f9' }} onClick={() => setShowModal(false)}>Cancel</button>
                </div>
              </form>
            </motion.div>
          </div>
        )}
      </AnimatePresence>
    </div>
  );
};

const DroneCard = ({ drone }) => {
  const getStatusColor = (state) => {
    switch(state) {
      case 'IDLE': return 'badge-success';
      case 'LOADING': return 'badge-warning';
      case 'LOADED': return 'badge-info';
      case 'DELIVERING': return 'badge-info';
      default: return 'badge-info';
    }
  };

  const batteryColor = (cap) => {
    if (cap > 50) return '#22c55e';
    if (cap > 25) return '#f59e0b';
    return '#ef4444';
  };

  return (
    <motion.div layout className="glass card">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '1rem' }}>
        <div>
          <h4 style={{ fontSize: '1.1rem', fontWeight: 700 }}>{drone.serialNumber}</h4>
          <p style={{ fontSize: '0.8rem', color: '#64748b' }}>{drone.model}</p>
        </div>
        <span className={`badge ${getStatusColor(drone.state)}`}>{drone.state}</span>
      </div>

      <div style={{ margin: '1rem 0' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.85rem' }}>
          <span style={{ display: 'flex', alignItems: 'center', gap: '0.4rem' }}>
            <Battery size={16} color={batteryColor(drone.batteryCapacity)} />
            {drone.batteryCapacity}%
          </span>
          <span style={{ display: 'flex', alignItems: 'center', gap: '0.4rem' }}>
            <Weight size={16} />
            {drone.currentLoad} / {drone.weightLimit}g
          </span>
        </div>
        <div className="battery-container">
          <div className="battery-fill" style={{ 
            width: `${drone.batteryCapacity}%`, 
            background: batteryColor(drone.batteryCapacity) 
          }} />
        </div>
      </div>

      <div style={{ fontSize: '0.85rem', color: '#64748b', marginTop: '0.5rem' }}>
        Items Loaded: {drone.medications?.length || 0}
      </div>
    </motion.div>
  );
};

export default EvtolFleet;
